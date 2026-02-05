package ca.glong.komodo.core.auth.keys

import ca.glong.komodo.core.auth.error.AuthError
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.value
import org.koin.core.annotation.Single
import platform.CoreFoundation.*
import platform.Foundation.*
import platform.Security.*

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosKeyManager : KeyManager {

    private fun createKeyAttributes(alias: String): CFDictionaryRef? {
        return memScoped {
            val nsAlias = NSString.create(string = alias)
            val tag = nsAlias.dataUsingEncoding(NSUTF8StringEncoding)
            
            val aliasRef = CFBridgingRetain(nsAlias)
            val tagRef = CFBridgingRetain(tag)
            
            val privateKeys = allocArray<CFTypeRefVar>(2)
            val privateValues = allocArray<CFTypeRefVar>(2)
            
            privateKeys[0] = kSecAttrLabel?.reinterpret<CPointed>()
            privateValues[0] = aliasRef?.reinterpret<CPointed>()
            
            privateKeys[1] = kSecAttrApplicationTag?.reinterpret<CPointed>()
            privateValues[1] = tagRef?.reinterpret<CPointed>()
            
            val privateKeyAttrs = CFDictionaryCreate(
                kCFAllocatorDefault,
                privateKeys,
                privateValues,
                2,
                kCFTypeDictionaryKeyCallBacks.ptr,
                kCFTypeDictionaryValueCallBacks.ptr
            )
            
            if (privateKeyAttrs == null) {
                if (aliasRef != null) CFRelease(aliasRef)
                if (tagRef != null) CFRelease(tagRef)
                return@memScoped null
            }
            
            val topKeys = allocArray<CFTypeRefVar>(4)
            val topValues = allocArray<CFTypeRefVar>(4)
            
            topKeys[0] = kSecAttrKeyType?.reinterpret<CPointed>()
            topValues[0] = kSecAttrKeyTypeECSECPrimeRandom?.reinterpret<CPointed>()
            
            topKeys[1] = kSecAttrKeySizeInBits?.reinterpret<CPointed>()
            val keySizeNum = NSNumber(int = 256)
            val keySizeRef = CFBridgingRetain(keySizeNum)
            topValues[1] = keySizeRef?.reinterpret<CPointed>()
            
            topKeys[2] = kSecAttrTokenID?.reinterpret<CPointed>()
            topValues[2] = kSecAttrTokenIDSecureEnclave?.reinterpret<CPointed>()
            
            topKeys[3] = kSecPrivateKeyAttrs?.reinterpret<CPointed>()
            topValues[3] = privateKeyAttrs.reinterpret<CPointed>()
            
            val attributes = CFDictionaryCreate(
                kCFAllocatorDefault,
                topKeys,
                topValues,
                4,
                kCFTypeDictionaryKeyCallBacks.ptr,
                kCFTypeDictionaryValueCallBacks.ptr
            )
            
            if (aliasRef != null) CFRelease(aliasRef)
            if (tagRef != null) CFRelease(tagRef)
            if (keySizeRef != null) CFRelease(keySizeRef)
            CFRelease(privateKeyAttrs) 
            
            attributes
        }
    }

    override suspend fun generateKeyPair(alias: String): Result<Unit> = runCatching {
        memScoped {
            val attributes = createKeyAttributes(alias) 
                ?: throw AuthError.KeyStoreError("Failed to create key attributes")
            
            try {
                val error = alloc<CFErrorRefVar>()
                val privateKey = SecKeyCreateRandomKey(attributes, error.ptr)
                
                if (privateKey == null) {
                    val errorRef = error.value
                    val errorDesc = if (errorRef != null) {
                        val desc = CFBridgingRelease(CFErrorCopyDescription(errorRef)) as? String
                         desc ?: "Unknown error"
                    } else {
                        "Unknown error"
                    }
                     throw AuthError.KeyStoreError("Failed to generate key pair: $errorDesc")
                }
                
                CFRelease(privateKey)
            } finally {
                CFRelease(attributes)
            }
        }
        Unit
    }

    override suspend fun getPublicKey(alias: String): Result<ByteArray?> = runCatching {
         memScoped {
            val nsAlias = NSString.create(string = alias)
            val tag = nsAlias.dataUsingEncoding(NSUTF8StringEncoding)
            
            val tagRef = CFBridgingRetain(tag)
            
            try {
                val keys = allocArray<CFTypeRefVar>(4)
                val values = allocArray<CFTypeRefVar>(4)

                keys[0] = kSecClass?.reinterpret<CPointed>()
                values[0] = kSecClassKey?.reinterpret<CPointed>()

                keys[1] = kSecAttrKeyType?.reinterpret<CPointed>()
                values[1] = kSecAttrKeyTypeECSECPrimeRandom?.reinterpret<CPointed>()

                keys[2] = kSecAttrApplicationTag?.reinterpret<CPointed>()
                values[2] = tagRef?.reinterpret<CPointed>()

                keys[3] = kSecReturnRef?.reinterpret<CPointed>()
                values[3] = kCFBooleanTrue?.reinterpret<CPointed>()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    4,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )
                
                try {
                    val result = alloc<CFTypeRefVar>()
                    val status = SecItemCopyMatching(query, result.ptr)
                    
                    if (status == errSecSuccess) {
                        val resultValue = result.value
                        val privateKeyRef = resultValue?.reinterpret<CPointed>()
                        // Note: We need a SecKeyRef for the API. In K/N SecKeyRef is CPointer<__SecKey>
                        // We can cast the void pointer to it.
                        // However, SecKeyCopyPublicKey expects SecKeyRef
                        // Let's rely on type inference or explicit cast if needed.
                        
                        if (privateKeyRef != null) {
                            try {
                                // reinterpret<__SecKey> to match expected type of SecKeyCopyPublicKey
                                // But __SecKey is not easily accessible. 
                                // Actually SecKeyRef is CPointer<__SecKey>.
                                // Let's try reinterpret<CPointed>() and let compiler check, or cast.
                                // The issue might be that privateKeyRef is treated as CPointed, but the function expects CPointer<__SecKey>
                                // We can use reinterpret<__SecKey>() if we import it or just pass it as is if it matches
                                
                                // FIX: platform.Security.SecKeyRef is a typealias.
                                // We need to cast result.value (COpaquePointer?) to SecKeyRef.
                                val secKey = resultValue as? SecKeyRef
                                
                                if (secKey != null) {
                                     val publicKeyRef = SecKeyCopyPublicKey(secKey)
                                     if (publicKeyRef != null) {
                                        try {
                                            val error = alloc<CFErrorRefVar>()
                                            val externalRep = SecKeyCopyExternalRepresentation(publicKeyRef, error.ptr)
                                            if (externalRep != null) {
                                                val nsData = CFBridgingRelease(externalRep) as? NSData
                                                if (nsData != null) {
                                                    val length = nsData.length.toInt()
                                                    val bytes = ByteArray(length)
                                                    if (bytes.isNotEmpty()) {
                                                         val ptr = nsData.bytes
                                                         if (ptr != null) {
                                                             val bytePtr = ptr.reinterpret<ByteVar>()
                                                             for (i in 0 until length) {
                                                                 bytes[i] = bytePtr[i]
                                                             }
                                                         }
                                                    }
                                                    return@runCatching bytes
                                                }
                                            }
                                        } finally {
                                            CFRelease(publicKeyRef)
                                        }
                                     }
                                }
                            } finally {
                                // privateKeyRef is borrowed from SecItemCopyMatching result?
                                // SecItemCopyMatching with kSecReturnRef returns a retained object.
                                // So we MUST release result.value
                                CFRelease(result.value)
                            }
                        }
                    }
                    null
                } finally {
                    if (query != null) CFRelease(query)
                }
            } finally {
                 if (tagRef != null) CFRelease(tagRef)
            }
         }
    }

    override suspend fun signData(alias: String, data: ByteArray): Result<ByteArray> = runCatching {
        memScoped {
            val nsAlias = NSString.create(string = alias)
            val tag = nsAlias.dataUsingEncoding(NSUTF8StringEncoding)
            val tagRef = CFBridgingRetain(tag)
            
            val dataPtr = allocArray<ByteVar>(data.size)
            for (i in data.indices) {
                dataPtr[i] = data[i]
            }
            val nsDataToSign = NSData.create(bytes = dataPtr, length = data.size.toULong())
            val dataToSignRef = CFBridgingRetain(nsDataToSign)

            try {
                val keys = allocArray<CFTypeRefVar>(4)
                val values = allocArray<CFTypeRefVar>(4)

                keys[0] = kSecClass?.reinterpret<CPointed>()
                values[0] = kSecClassKey?.reinterpret<CPointed>()

                keys[1] = kSecAttrKeyType?.reinterpret<CPointed>()
                values[1] = kSecAttrKeyTypeECSECPrimeRandom?.reinterpret<CPointed>()

                keys[2] = kSecAttrApplicationTag?.reinterpret<CPointed>()
                values[2] = tagRef?.reinterpret<CPointed>()

                keys[3] = kSecReturnRef?.reinterpret<CPointed>()
                values[3] = kCFBooleanTrue?.reinterpret<CPointed>()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    4,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )
                
                try {
                    val result = alloc<CFTypeRefVar>()
                    val status = SecItemCopyMatching(query, result.ptr)
                    
                    if (status != errSecSuccess) {
                        throw AuthError.KeyStoreError("Key not found for signing")
                    }
                    
                    val resultValue = result.value
                    val secKey = resultValue as? SecKeyRef
                        ?: throw AuthError.KeyStoreError("Failed to retrieve private key ref")
                        
                    try {
                        val error = alloc<CFErrorRefVar>()
                        val signature = SecKeyCreateSignature(
                            secKey,
                            kSecKeyAlgorithmECDSASignatureMessageX962SHA256,
                            dataToSignRef as? CFDataRef,
                            error.ptr
                        )
                        
                        if (signature == null) {
                             val errorRef = error.value
                             val errorDesc = if (errorRef != null) {
                                val desc = CFBridgingRelease(CFErrorCopyDescription(errorRef)) as? String
                                desc ?: "Unknown error"
                             } else {
                                 "Unknown error"
                             }
                             throw AuthError.KeyStoreError("Signing failed: $errorDesc")
                        }
                        
                        val signatureData = CFBridgingRelease(signature) as? NSData
                            ?: throw AuthError.KeyStoreError("Failed to bridge signature data")
                            
                        val length = signatureData.length.toInt()
                        val bytes = ByteArray(length)
                        val ptr = signatureData.bytes
                        if (ptr != null) {
                             val bytePtr = ptr.reinterpret<ByteVar>()
                             for (i in 0 until length) {
                                 bytes[i] = bytePtr[i]
                             }
                        }
                        bytes
                    } finally {
                        CFRelease(result.value)
                    }
                } finally {
                     if (query != null) CFRelease(query)
                }
            } finally {
                if (tagRef != null) CFRelease(tagRef)
                if (dataToSignRef != null) CFRelease(dataToSignRef)
            }
        }
    }

    override suspend fun deleteKey(alias: String): Result<Unit> = runCatching {
        memScoped {
            val nsAlias = NSString.create(string = alias)
            val tag = nsAlias.dataUsingEncoding(NSUTF8StringEncoding)
            val tagRef = CFBridgingRetain(tag)

            try {
                val keys = allocArray<CFTypeRefVar>(3)
                val values = allocArray<CFTypeRefVar>(3)

                keys[0] = kSecClass?.reinterpret<CPointed>()
                values[0] = kSecClassKey?.reinterpret<CPointed>()
                
                keys[1] = kSecAttrKeyType?.reinterpret<CPointed>()
                values[1] = kSecAttrKeyTypeECSECPrimeRandom?.reinterpret<CPointed>()

                keys[2] = kSecAttrApplicationTag?.reinterpret<CPointed>()
                values[2] = tagRef?.reinterpret<CPointed>()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    3,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )
                
                try {
                    val status = SecItemDelete(query)
                    if (status != errSecSuccess && status != errSecItemNotFound) {
                        throw AuthError.KeyStoreError("Failed to delete key: $status")
                    }
                } finally {
                    if (query != null) CFRelease(query)
                }
            } finally {
                if (tagRef != null) CFRelease(tagRef)
            }
        }
        Unit
    }

    override suspend fun hasKey(alias: String): Result<Boolean> = runCatching {
        memScoped {
            val nsAlias = NSString.create(string = alias)
            val tag = nsAlias.dataUsingEncoding(NSUTF8StringEncoding)
            val tagRef = CFBridgingRetain(tag)

            try {
                val keys = allocArray<CFTypeRefVar>(4)
                val values = allocArray<CFTypeRefVar>(4)

                keys[0] = kSecClass?.reinterpret<CPointed>()
                values[0] = kSecClassKey?.reinterpret<CPointed>()
                
                keys[1] = kSecAttrKeyType?.reinterpret<CPointed>()
                values[1] = kSecAttrKeyTypeECSECPrimeRandom?.reinterpret<CPointed>()

                keys[2] = kSecAttrApplicationTag?.reinterpret<CPointed>()
                values[2] = tagRef?.reinterpret<CPointed>()
                
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
                
                try {
                    val status = SecItemCopyMatching(query, null)
                    status == errSecSuccess
                } finally {
                    if (query != null) CFRelease(query)
                }
            } finally {
                if (tagRef != null) CFRelease(tagRef)
            }
        }
    }
}

@Single
actual fun providePlatformKeyManager(): KeyManager {
    return IosKeyManager()
}
