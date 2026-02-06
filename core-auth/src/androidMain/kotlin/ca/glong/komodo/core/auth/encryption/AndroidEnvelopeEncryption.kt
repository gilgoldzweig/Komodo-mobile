package ca.glong.komodo.core.auth.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import ca.glong.komodo.core.auth.error.AuthError
import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.keys.KeyMetadata
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Android implementation of envelope encryption using hardware-backed Keystore AES256_GCM.
 * 
 * Wraps Ed25519 private keys (or other sensitive data) with a hardware-backed AES key.
 * The master key never leaves the Android Keystore.
 *
 * Wrapped format: [version:1byte][algorithm:1byte][iv:12bytes][ciphertext][tag:16bytes]
 */
class AndroidEnvelopeEncryption : EnvelopeEncryption {
    
    private companion object {
        const val KEYSTORE_TYPE = "AndroidKeyStore"
        const val MASTER_KEY_ALIAS = "envelope_master_key"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val KEY_SIZE = 256
        const val GCM_IV_LENGTH = 12
        const val GCM_TAG_LENGTH = 128
        
        const val VERSION_V1: Byte = 0x01
        const val ALGORITHM_AES_GCM: Byte = 0x01
    }
    
    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(KEYSTORE_TYPE).apply { load(null) }
    }
    
    override suspend fun encrypt(data: ByteArray, keyAlias: String): Result<ByteArray> = runCatching {
        val masterKey = getOrCreateMasterKey(keyAlias)
        
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, masterKey)
        
        val iv = cipher.iv
        val ciphertext = cipher.doFinal(data)
        
        // Build wrapped format: version + algorithm + iv + ciphertext (includes tag)
        val wrapped = ByteArray(2 + iv.size + ciphertext.size)
        wrapped[0] = VERSION_V1
        wrapped[1] = ALGORITHM_AES_GCM
        System.arraycopy(iv, 0, wrapped, 2, iv.size)
        System.arraycopy(ciphertext, 0, wrapped, 2 + iv.size, ciphertext.size)
        
        wrapped
    }.recoverCatching { throwable ->
        throw AuthError.EnvelopeError.EncryptionFailed(
            reason = throwable.message ?: "Unknown encryption failure"
        )
    }
    
    override suspend fun decrypt(encryptedData: ByteArray, keyAlias: String): Result<ByteArray> = runCatching {
        require(encryptedData.size >= 2 + GCM_IV_LENGTH + GCM_TAG_LENGTH / 8) {
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
        
        val iv = ByteArray(GCM_IV_LENGTH)
        System.arraycopy(encryptedData, 2, iv, 0, GCM_IV_LENGTH)
        
        val ciphertextLength = encryptedData.size - 2 - GCM_IV_LENGTH
        val ciphertext = ByteArray(ciphertextLength)
        System.arraycopy(encryptedData, 2 + GCM_IV_LENGTH, ciphertext, 0, ciphertextLength)
        
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, masterKey, gcmSpec)
        
        cipher.doFinal(ciphertext)
    }.recoverCatching { throwable ->
        throw AuthError.EnvelopeError.DecryptionFailed(
            reason = throwable.message ?: "Unknown decryption failure"
        )
    }
    
    override suspend fun getKeyMetadata(keyAlias: String): Result<KeyMetadata?> = runCatching {
        if (keyStore.containsAlias(keyAlias)) {
            val entry = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
            entry?.let {
                KeyMetadata(
                    alias = keyAlias,
                    algorithm = ca.glong.komodo.core.auth.keys.KeyAlgorithm.ED25519,
                    createdAt = System.currentTimeMillis(),
                    isPrivate = true,
                    keySize = 256,
                    metadata = mapOf("type" to "envelope_encryption")
                )
            }
        } else {
            null
        }
    }
    
    private fun getOrCreateMasterKey(alias: String): SecretKey {
        return if (keyStore.containsAlias(alias)) {
            keyStore.getKey(alias, null) as SecretKey
        } else {
            generateMasterKey(alias)
        }
    }
    
    private fun generateMasterKey(alias: String): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE_TYPE
        )
        
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .setUserAuthenticationRequired(false)
            .build()
        
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }
}
