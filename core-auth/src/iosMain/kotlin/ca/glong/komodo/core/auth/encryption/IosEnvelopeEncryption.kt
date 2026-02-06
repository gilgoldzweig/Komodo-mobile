package ca.glong.komodo.core.auth.encryption

import ca.glong.komodo.core.auth.error.AuthError
import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.keys.KeyAlgorithm
import ca.glong.komodo.core.auth.keys.KeyMetadata
import kotlinx.cinterop.*
import org.koin.core.annotation.Single
import platform.CoreCrypto.*
import platform.CoreFoundation.*
import platform.Foundation.*
import platform.Security.*
import platform.darwin.OSStatus
import platform.posix.memcpy

@Single
@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
class IosEnvelopeEncryption : EnvelopeEncryption {

    private companion object {
        const val SERVICE_NAME = "ca.glong.komodo.envelope"
        const val KEY_SIZE = 32
        const val IV_SIZE = 16
        const val TAG_SIZE = 16
        const val VERSION_V1: Byte = 0x01
        const val ALGORITHM_AES_GCM: Byte = 0x01
    }

    override suspend fun encrypt(data: ByteArray, keyAlias: String): Result<ByteArray> = runCatching {
        val masterKey = getOrCreateMasterKey(keyAlias)
        
        val nonce = ByteArray(IV_SIZE)
        nonce.usePinned { pinned ->
            SecRandomCopyBytes(kSecRandomDefault, nonce.size.toULong(), pinned.addressOf(0))
        }
        
        val ciphertext = aesCbcEncrypt(data, masterKey, nonce)
        val tag = computeHmac(nonce + ciphertext, masterKey)
        
        val wrapped = ByteArray(2 + IV_SIZE + TAG_SIZE + ciphertext.size)
        wrapped[0] = VERSION_V1
        wrapped[1] = ALGORITHM_AES_GCM
        nonce.copyInto(wrapped, destinationOffset = 2)
        tag.copyInto(wrapped, destinationOffset = 2 + IV_SIZE, endIndex = TAG_SIZE)
        ciphertext.copyInto(wrapped, destinationOffset = 2 + IV_SIZE + TAG_SIZE)
        
        wrapped
    }.recoverCatching { throwable ->
        throw AuthError.EnvelopeError.EncryptionFailed(
            reason = throwable.message ?: "Unknown encryption failure"
        )
    }

    override suspend fun decrypt(encryptedData: ByteArray, keyAlias: String): Result<ByteArray> = runCatching {
        require(encryptedData.size >= 2 + IV_SIZE + TAG_SIZE) {
            "Wrapped data too short"
        }
        
        val version = encryptedData[0]
        val algorithm = encryptedData[1]
        
        if (version != VERSION_V1) {
            throw AuthError.EnvelopeError.InvalidEnvelope("Unsupported version: $version")
        }
        
        if (algorithm != ALGORITHM_AES_GCM) {
            throw AuthError.EnvelopeError.InvalidEnvelope("Unsupported algorithm: $algorithm")
        }
        
        val masterKey = getOrCreateMasterKey(keyAlias)
        
        val nonce = encryptedData.sliceArray(2 until 2 + IV_SIZE)
        val providedTag = encryptedData.sliceArray(2 + IV_SIZE until 2 + IV_SIZE + TAG_SIZE)
        val ciphertext = encryptedData.sliceArray(2 + IV_SIZE + TAG_SIZE until encryptedData.size)
        
        val computedTag = computeHmac(nonce + ciphertext, masterKey)
        val computedTagTruncated = computedTag.sliceArray(0 until TAG_SIZE)
        
        if (!providedTag.contentEquals(computedTagTruncated)) {
            throw AuthError.EnvelopeError.DecryptionFailed("Authentication tag mismatch")
        }
        
        aesCbcDecrypt(ciphertext, masterKey, nonce)
    }.recoverCatching { throwable ->
        throw AuthError.EnvelopeError.DecryptionFailed(
            reason = throwable.message ?: "Unknown decryption failure"
        )
    }

    override suspend fun getKeyMetadata(keyAlias: String): Result<KeyMetadata?> = runCatching {
        if (keyExistsInKeychain(keyAlias)) {
            KeyMetadata(
                alias = keyAlias,
                algorithm = KeyAlgorithm.ED25519,
                createdAt = NSDate().timeIntervalSince1970.toLong() * 1000,
                isPrivate = true,
                keySize = 256,
                metadata = mapOf("type" to "envelope_encryption")
            )
        } else {
            null
        }
    }

    private fun getOrCreateMasterKey(alias: String): ByteArray {
        return loadKeyFromKeychain(alias) ?: generateAndStoreKey(alias)
    }

    private fun generateAndStoreKey(alias: String): ByteArray {
        val key = ByteArray(KEY_SIZE)
        key.usePinned { pinned ->
            SecRandomCopyBytes(kSecRandomDefault, key.size.toULong(), pinned.addressOf(0))
        }
        storeKeyInKeychain(alias, key)
        return key
    }

    private fun storeKeyInKeychain(alias: String, key: ByteArray) = memScoped {
        val keyData = key.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = key.size.toULong())
        }
        
        val keys = allocArray<CFTypeRefVar>(5)
        val values = allocArray<CFTypeRefVar>(5)
        
        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()
        
        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()
        
        keys[2] = kSecAttrAccount?.reinterpret<CPointed>()
        val aliasRef = CFBridgingRetain(NSString.create(string = alias))
        values[2] = aliasRef?.reinterpret<CPointed>()
        
        keys[3] = kSecValueData?.reinterpret<CPointed>()
        val keyDataRef = CFBridgingRetain(keyData)
        values[3] = keyDataRef?.reinterpret<CPointed>()
        
        keys[4] = kSecAttrAccessible?.reinterpret<CPointed>()
        values[4] = kSecAttrAccessibleWhenUnlockedThisDeviceOnly?.reinterpret<CPointed>()
        
        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            5,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )
        
        if (query == null) {
            if (serviceRef != null) CFRelease(serviceRef)
            if (aliasRef != null) CFRelease(aliasRef)
            if (keyDataRef != null) CFRelease(keyDataRef)
            throw AuthError.KeyError.StorageFailed("Failed to create query dictionary")
        }
        
        SecItemDelete(query)
        
        val status: OSStatus = SecItemAdd(query, null)
        
        CFRelease(query)
        if (serviceRef != null) CFRelease(serviceRef)
        if (aliasRef != null) CFRelease(aliasRef)
        if (keyDataRef != null) CFRelease(keyDataRef)
        
        if (status != errSecSuccess) {
            throw AuthError.KeyError.StorageFailed("Failed to store key in Keychain: $status")
        }
    }

    private fun loadKeyFromKeychain(alias: String): ByteArray? = memScoped {
        val keys = allocArray<CFTypeRefVar>(5)
        val values = allocArray<CFTypeRefVar>(5)
        
        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()
        
        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()
        
        keys[2] = kSecAttrAccount?.reinterpret<CPointed>()
        val aliasRef = CFBridgingRetain(NSString.create(string = alias))
        values[2] = aliasRef?.reinterpret<CPointed>()
        
        keys[3] = kSecReturnData?.reinterpret<CPointed>()
        values[3] = kCFBooleanTrue?.reinterpret<CPointed>()
        
        keys[4] = kSecMatchLimit?.reinterpret<CPointed>()
        values[4] = kSecMatchLimitOne?.reinterpret<CPointed>()
        
        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            5,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )
        
        if (query == null) {
            if (serviceRef != null) CFRelease(serviceRef)
            if (aliasRef != null) CFRelease(aliasRef)
            throw AuthError.KeyError.LoadFailed("Failed to create query dictionary")
        }
        
        val result = alloc<ObjCObjectVar<NSData?>>()
        val status: OSStatus = SecItemCopyMatching(query, result.ptr.reinterpret())
        
        CFRelease(query)
        if (serviceRef != null) CFRelease(serviceRef)
        if (aliasRef != null) CFRelease(aliasRef)
        
        when (status) {
            errSecSuccess -> {
                val data = result.value ?: return@memScoped null
                val bytes = ByteArray(data.length.toInt())
                bytes.usePinned { pinned ->
                    memcpy(pinned.addressOf(0), data.bytes, data.length)
                }
                bytes
            }
            errSecItemNotFound -> null
            else -> throw AuthError.KeyError.LoadFailed("Failed to load key from Keychain: $status")
        }
    }

    private fun keyExistsInKeychain(alias: String): Boolean = memScoped {
        val keys = allocArray<CFTypeRefVar>(4)
        val values = allocArray<CFTypeRefVar>(4)
        
        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()
        
        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()
        
        keys[2] = kSecAttrAccount?.reinterpret<CPointed>()
        val aliasRef = CFBridgingRetain(NSString.create(string = alias))
        values[2] = aliasRef?.reinterpret<CPointed>()
        
        keys[3] = kSecMatchLimit?.reinterpret<CPointed>()
        values[3] = kSecMatchLimitOne?.reinterpret<CPointed>()
        
        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            4,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )
        
        if (query == null) {
            if (serviceRef != null) CFRelease(serviceRef)
            if (aliasRef != null) CFRelease(aliasRef)
            return@memScoped false
        }
        
        val status = SecItemCopyMatching(query, null)
        
        CFRelease(query)
        if (serviceRef != null) CFRelease(serviceRef)
        if (aliasRef != null) CFRelease(aliasRef)
        
        status == errSecSuccess
    }

    private fun aesCbcEncrypt(plaintext: ByteArray, key: ByteArray, iv: ByteArray): ByteArray = memScoped {
        val outputSize = plaintext.size + kCCBlockSizeAES128.toInt()
        val output = ByteArray(outputSize)
        val dataOutMoved = alloc<ULongVar>()
        
        plaintext.usePinned { plaintextPin ->
            key.usePinned { keyPin ->
                iv.usePinned { ivPin ->
                    output.usePinned { outputPin ->
                        val status = CCCrypt(
                            kCCEncrypt.toUInt(),
                            kCCAlgorithmAES.toUInt(),
                            kCCOptionPKCS7Padding.toUInt(),
                            keyPin.addressOf(0),
                            key.size.toULong(),
                            ivPin.addressOf(0),
                            plaintextPin.addressOf(0),
                            plaintext.size.toULong(),
                            outputPin.addressOf(0),
                            outputSize.toULong(),
                            dataOutMoved.ptr
                        )
                        
                        if (status != kCCSuccess) {
                            throw AuthError.EnvelopeError.EncryptionFailed("CCCrypt failed: $status")
                        }
                    }
                }
            }
        }
        
        output.sliceArray(0 until dataOutMoved.value.toInt())
    }

    private fun aesCbcDecrypt(ciphertext: ByteArray, key: ByteArray, iv: ByteArray): ByteArray = memScoped {
        val outputSize = ciphertext.size
        val output = ByteArray(outputSize)
        val dataOutMoved = alloc<ULongVar>()
        
        ciphertext.usePinned { ciphertextPin ->
            key.usePinned { keyPin ->
                iv.usePinned { ivPin ->
                    output.usePinned { outputPin ->
                        val status = CCCrypt(
                            kCCDecrypt.toUInt(),
                            kCCAlgorithmAES.toUInt(),
                            kCCOptionPKCS7Padding.toUInt(),
                            keyPin.addressOf(0),
                            key.size.toULong(),
                            ivPin.addressOf(0),
                            ciphertextPin.addressOf(0),
                            ciphertext.size.toULong(),
                            outputPin.addressOf(0),
                            outputSize.toULong(),
                            dataOutMoved.ptr
                        )
                        
                        if (status != kCCSuccess) {
                            throw AuthError.EnvelopeError.DecryptionFailed("CCCrypt failed: $status")
                        }
                    }
                }
            }
        }
        
        output.sliceArray(0 until dataOutMoved.value.toInt())
    }

    private fun computeHmac(data: ByteArray, key: ByteArray): ByteArray = memScoped {
        val macLength = CC_SHA256_DIGEST_LENGTH
        val mac = ByteArray(macLength)
        
        data.usePinned { dataPin ->
            key.usePinned { keyPin ->
                mac.usePinned { macPin ->
                    CCHmac(
                        kCCHmacAlgSHA256.toUInt(),
                        keyPin.addressOf(0),
                        key.size.toULong(),
                        dataPin.addressOf(0),
                        data.size.toULong(),
                        macPin.addressOf(0)
                    )
                }
            }
        }
        
        mac
    }
}
