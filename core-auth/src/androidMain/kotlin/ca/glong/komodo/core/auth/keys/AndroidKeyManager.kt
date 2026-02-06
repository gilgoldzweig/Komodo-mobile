package ca.glong.komodo.core.auth.keys

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import ca.glong.komodo.core.auth.encryption.AndroidEnvelopeEncryption
import ca.glong.komodo.core.auth.error.AuthError
import ca.glong.komodo.core.auth.storage.AndroidSecureStorage
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.PublicKeySign
import com.google.crypto.tink.PublicKeyVerify
import com.google.crypto.tink.signature.SignatureConfig
import com.google.crypto.tink.signature.SignatureKeyTemplates
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature
import java.security.spec.ECGenParameterSpec

class AndroidKeyManager(
    private val envelopeEncryption: EnvelopeEncryption,
    private val secureStorage: AndroidSecureStorage,
    @Suppress("unused") private val context: Context
) : KeyManager {

    private companion object {
        const val KEYSTORE_TYPE = "AndroidKeyStore"
        const val KEY_PREFIX = "key:"
        const val METADATA_PREFIX = "metadata:"
        const val PUBLIC_KEY_PREFIX = "public:"
    }

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(KEYSTORE_TYPE).apply { load(null) }
    }

    private val json = Json { ignoreUnknownKeys = true }

    init {
        try {
            SignatureConfig.register()
        } catch (e: Exception) {
            // Ignore: already registered
        }
    }

    override suspend fun generateKeyPair(alias: String, type: KeyType): Result<Unit> = runCatching {
        when (type) {
            KeyType.ED25519 -> generateEd25519KeyPair(alias)
            KeyType.EC_P256 -> generateP256KeyPair(alias)
            KeyType.RSA_4096 -> throw AuthError.KeyError.GenerationFailed("RSA_4096 not yet supported")
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is AuthError -> throw throwable
            else -> throw AuthError.KeyError.GenerationFailed(throwable.message ?: "Unknown error")
        }
    }

    private suspend fun generateEd25519KeyPair(alias: String) {
        try {
            val keysetHandle = KeysetHandle.generateNew(SignatureKeyTemplates.ED25519)
            
            val privateKeyBytes = extractPrivateKeyBytes(keysetHandle)
            val publicKeyBytes = extractPublicKeyBytes(keysetHandle)
            
            val wrappedKey = envelopeEncryption.encrypt(privateKeyBytes, alias)
                .getOrElse { throw AuthError.KeyError.GenerationFailed("Failed to wrap private key: ${it.message}") }
            
            val wrappedKeyBase64 = Base64.encodeToString(wrappedKey, Base64.NO_WRAP)
            secureStorage.save("$KEY_PREFIX$alias", wrappedKeyBase64)
                .getOrElse { throw AuthError.KeyError.StorageFailed("Failed to store private key") }
            
            val publicKeyBase64 = Base64.encodeToString(publicKeyBytes, Base64.NO_WRAP)
            secureStorage.save("$PUBLIC_KEY_PREFIX$alias", publicKeyBase64)
                .getOrElse { throw AuthError.KeyError.StorageFailed("Failed to store public key") }
            
            val metadata = KeyMetadata(
                alias = alias,
                algorithm = KeyAlgorithm.ED25519,
                createdAt = System.currentTimeMillis(),
                isPrivate = true,
                keySize = 256,
                metadata = mapOf("type" to "ed25519")
            )
            val metadataJson = json.encodeToString(metadata)
            secureStorage.save("$METADATA_PREFIX$alias", metadataJson)
                .getOrElse { throw AuthError.KeyError.StorageFailed("Failed to store metadata") }
        } catch (e: AuthError) {
            throw e
        } catch (e: Exception) {
            throw AuthError.KeyError.GenerationFailed("Ed25519 generation failed: ${e.message}")
        }
    }

    private suspend fun generateP256KeyPair(alias: String) {
        try {
            if (keyStore.containsAlias(alias)) {
                throw AuthError.KeyError.GenerationFailed("Key with alias $alias already exists")
            }
            
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                KEYSTORE_TYPE
            )
            
            val parameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            )
                .setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setUserAuthenticationRequired(false)
                .build()
            
            keyPairGenerator.initialize(parameterSpec)
            val keyPair = keyPairGenerator.generateKeyPair()
            
            val publicKeyBytes = keyPair.public.encoded
            val publicKeyBase64 = Base64.encodeToString(publicKeyBytes, Base64.NO_WRAP)
            secureStorage.save("$PUBLIC_KEY_PREFIX$alias", publicKeyBase64)
                .getOrElse { throw AuthError.KeyError.StorageFailed("Failed to store public key") }
            
            val metadata = KeyMetadata(
                alias = alias,
                algorithm = KeyAlgorithm.P256,
                createdAt = System.currentTimeMillis(),
                isPrivate = true,
                keySize = 256,
                metadata = mapOf("type" to "p256", "hardware_backed" to "true")
            )
            val metadataJson = json.encodeToString(metadata)
            secureStorage.save("$METADATA_PREFIX$alias", metadataJson)
                .getOrElse { throw AuthError.KeyError.StorageFailed("Failed to store metadata") }
        } catch (e: AuthError) {
            throw e
        } catch (e: Exception) {
            throw AuthError.KeyError.GenerationFailed("P-256 generation failed: ${e.message}")
        }
    }

    override suspend fun signData(alias: String, data: ByteArray): Result<ByteArray> = runCatching {
        val metadataJson = secureStorage.read("$METADATA_PREFIX$alias")
            .getOrElse { throw AuthError.KeyError.LoadFailed("Key not found: $alias") }
            ?: throw AuthError.KeyError.LoadFailed("Key not found: $alias")
        
        val metadata = json.decodeFromString<KeyMetadata>(metadataJson)
        
        when (metadata.algorithm) {
            KeyAlgorithm.ED25519 -> signWithEd25519(alias, data)
            KeyAlgorithm.P256 -> signWithP256(alias, data)
            else -> throw AuthError.KeyError.OperationFailed("Unsupported algorithm: ${metadata.algorithm}")
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is AuthError -> throw throwable
            else -> throw AuthError.KeyError.OperationFailed(throwable.message ?: "Sign failed")
        }
    }

    private suspend fun signWithEd25519(alias: String, data: ByteArray): ByteArray {
        try {
            val wrappedKeyBase64 = secureStorage.read("$KEY_PREFIX$alias")
                .getOrElse { throw AuthError.KeyError.LoadFailed("Private key not found") }
                ?: throw AuthError.KeyError.LoadFailed("Private key not found")
            
            val wrappedKey = Base64.decode(wrappedKeyBase64, Base64.NO_WRAP)
            
            val privateKeyBytes = envelopeEncryption.decrypt(wrappedKey, alias)
                .getOrElse { throw AuthError.KeyError.LoadFailed("Failed to unwrap private key") }
            
            val keysetHandle = reconstructEd25519KeysetHandle(privateKeyBytes)
            
            val signer = keysetHandle.getPrimitive(PublicKeySign::class.java)
            return signer.sign(data)
        } catch (e: AuthError) {
            throw e
        } catch (e: Exception) {
            throw AuthError.KeyError.OperationFailed("Ed25519 signing failed: ${e.message}")
        }
    }

    private suspend fun signWithP256(alias: String, data: ByteArray): ByteArray {
        try {
            val entry = keyStore.getEntry(alias, null) as? KeyStore.PrivateKeyEntry
                ?: throw AuthError.KeyError.LoadFailed("Key not found in Keystore: $alias")
            
            val signature = Signature.getInstance("SHA256withECDSA")
            signature.initSign(entry.privateKey)
            signature.update(data)
            return signature.sign()
        } catch (e: AuthError) {
            throw e
        } catch (e: Exception) {
            throw AuthError.KeyError.OperationFailed("P-256 signing failed: ${e.message}")
        }
    }

    override suspend fun getPublicKey(alias: String): Result<ByteArray?> = runCatching {
        val publicKeyBase64 = secureStorage.read("$PUBLIC_KEY_PREFIX$alias")
            .getOrNull()
        
        publicKeyBase64?.let { Base64.decode(it, Base64.NO_WRAP) }
    }

    override suspend fun deleteKey(alias: String): Result<Unit> = runCatching {
        secureStorage.delete("$KEY_PREFIX$alias").getOrNull()
        secureStorage.delete("$PUBLIC_KEY_PREFIX$alias").getOrNull()
        secureStorage.delete("$METADATA_PREFIX$alias").getOrNull()
        
        if (keyStore.containsAlias(alias)) {
            keyStore.deleteEntry(alias)
        }
    }.recoverCatching { throwable ->
        throw AuthError.KeyError.OperationFailed("Delete failed: ${throwable.message}")
    }

    override suspend fun hasKey(alias: String): Result<Boolean> = runCatching {
        secureStorage.contains("$METADATA_PREFIX$alias")
            .getOrElse { false }
    }

    override suspend fun exportSshKey(alias: String): Result<String> = runCatching {
        val metadataJson = secureStorage.read("$METADATA_PREFIX$alias")
            .getOrElse { throw AuthError.KeyError.LoadFailed("Key not found: $alias") }
            ?: throw AuthError.KeyError.LoadFailed("Key not found: $alias")
        
        val metadata = json.decodeFromString<KeyMetadata>(metadataJson)
        
        val publicKeyBase64Stored = secureStorage.read("$PUBLIC_KEY_PREFIX$alias")
            .getOrElse { throw AuthError.KeyError.LoadFailed("Public key not found") }
            ?: throw AuthError.KeyError.LoadFailed("Public key not found")
        
        val publicKeyBytes = Base64.decode(publicKeyBase64Stored, Base64.NO_WRAP)
        
        when (metadata.algorithm) {
            KeyAlgorithm.ED25519 -> {
                val sshKeyBytes = encodeSshEd25519PublicKey(publicKeyBytes)
                val sshKeyBase64 = Base64.encodeToString(sshKeyBytes, Base64.NO_WRAP)
                "ssh-ed25519 $sshKeyBase64 $alias@android"
            }
            KeyAlgorithm.P256 -> {
                val sshKeyBytes = encodeSshP256PublicKey(publicKeyBytes)
                val sshKeyBase64 = Base64.encodeToString(sshKeyBytes, Base64.NO_WRAP)
                "ecdsa-sha2-nistp256 $sshKeyBase64 $alias@android"
            }
            else -> throw AuthError.KeyError.OperationFailed("Unsupported algorithm for SSH export: ${metadata.algorithm}")
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is AuthError -> throw throwable
            else -> throw AuthError.KeyError.OperationFailed("SSH export failed: ${throwable.message}")
        }
    }

    // Helper functions for key extraction and reconstruction

    private fun extractPrivateKeyBytes(keysetHandle: KeysetHandle): ByteArray {
        // Tink doesn't expose raw key bytes directly, so we serialize the keyset
        val outputStream = ByteArrayOutputStream()
        com.google.crypto.tink.CleartextKeysetHandle.write(keysetHandle, com.google.crypto.tink.JsonKeysetWriter.withOutputStream(outputStream))
        return outputStream.toByteArray()
    }

    private fun extractPublicKeyBytes(keysetHandle: KeysetHandle): ByteArray {
        val publicKeysetHandle = keysetHandle.publicKeysetHandle
        val outputStream = ByteArrayOutputStream()
        com.google.crypto.tink.CleartextKeysetHandle.write(publicKeysetHandle, com.google.crypto.tink.JsonKeysetWriter.withOutputStream(outputStream))
        return outputStream.toByteArray()
    }

    private fun reconstructEd25519KeysetHandle(privateKeyBytes: ByteArray): KeysetHandle {
        val inputStream = privateKeyBytes.inputStream()
        return com.google.crypto.tink.CleartextKeysetHandle.read(com.google.crypto.tink.JsonKeysetReader.withInputStream(inputStream))
    }

    private fun encodeSshEd25519PublicKey(rawPublicKeyBytes: ByteArray): ByteArray {
        // For Ed25519, the rawPublicKeyBytes from Tink is a JSON keyset
        // We need to extract the actual 32-byte public key
        // SSH Ed25519 format: 
        // - 4 bytes: length of "ssh-ed25519" (11)
        // - 11 bytes: "ssh-ed25519"
        // - 4 bytes: length of public key (32)
        // - 32 bytes: public key
        
        val keyType = "ssh-ed25519".toByteArray()
        
        // Parse the Tink keyset JSON to extract the raw 32-byte public key
        val publicKey = extractEd25519RawPublicKey(rawPublicKeyBytes)
        
        val output = ByteArrayOutputStream()
        output.writeInt32(keyType.size)
        output.write(keyType)
        output.writeInt32(publicKey.size)
        output.write(publicKey)
        
        return output.toByteArray()
    }

    private fun encodeSshP256PublicKey(x509PublicKeyBytes: ByteArray): ByteArray {
        // P-256 public key is X.509 encoded (65 bytes for uncompressed point)
        // SSH format:
        // - 4 bytes: length of "ecdsa-sha2-nistp256" (19)
        // - 19 bytes: "ecdsa-sha2-nistp256"
        // - 4 bytes: length of "nistp256" (8)
        // - 8 bytes: "nistp256"
        // - 4 bytes: length of public key point (65)
        // - 65 bytes: 0x04 + X (32 bytes) + Y (32 bytes)
        
        val keyType = "ecdsa-sha2-nistp256".toByteArray()
        val curveName = "nistp256".toByteArray()
        
        // Extract the 65-byte uncompressed point from X.509 encoding
        val publicKeyPoint = extractP256UncompressedPoint(x509PublicKeyBytes)
        
        val output = ByteArrayOutputStream()
        output.writeInt32(keyType.size)
        output.write(keyType)
        output.writeInt32(curveName.size)
        output.write(curveName)
        output.writeInt32(publicKeyPoint.size)
        output.write(publicKeyPoint)
        
        return output.toByteArray()
    }

    private fun extractEd25519RawPublicKey(tinkKeysetBytes: ByteArray): ByteArray {
        // Parse Tink JSON keyset to extract the raw 32-byte Ed25519 public key
        val keysetJson = String(tinkKeysetBytes, Charsets.UTF_8)
        
        // Tink Ed25519 public key is base64-encoded in the "keyValue" field
        val keyValuePattern = """"keyValue":\s*"([^"]+)"""".toRegex()
        val match = keyValuePattern.find(keysetJson)
        
        return if (match != null) {
            val base64Key = match.groupValues[1]
            Base64.decode(base64Key, Base64.NO_WRAP)
        } else {
            // Fallback: assume the bytes are already the raw 32-byte key
            if (tinkKeysetBytes.size == 32) {
                tinkKeysetBytes
            } else {
                throw AuthError.KeyError.InvalidFormat("Cannot extract Ed25519 public key from Tink keyset")
            }
        }
    }

    private fun extractP256UncompressedPoint(x509Bytes: ByteArray): ByteArray {
        // X.509 SubjectPublicKeyInfo structure for EC keys:
        // The actual public key point is at the end (65 bytes for uncompressed)
        // It starts with 0x04 (uncompressed point indicator) followed by X (32 bytes) and Y (32 bytes)
        
        // Find the 0x04 byte followed by 64 bytes
        for (i in 0 until x509Bytes.size - 64) {
            if (x509Bytes[i] == 0x04.toByte()) {
                // Check if we have 64 more bytes
                if (i + 64 < x509Bytes.size) {
                    return x509Bytes.copyOfRange(i, i + 65)
                }
            }
        }
        
        throw AuthError.KeyError.InvalidFormat("Cannot extract P-256 uncompressed point from X.509 encoding")
    }

    private fun ByteArrayOutputStream.writeInt32(value: Int) {
        write((value shr 24) and 0xFF)
        write((value shr 16) and 0xFF)
        write((value shr 8) and 0xFF)
        write(value and 0xFF)
    }
}

@org.koin.core.annotation.Single
actual fun providePlatformKeyManager(): KeyManager {
    // This will be resolved by Koin DI - AndroidKeyManager requires dependencies
    throw NotImplementedError("Use Koin DI to inject AndroidKeyManager with dependencies")
}
