package ca.glong.komodo.core.auth.keys

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import ca.glong.komodo.core.auth.error.AuthError
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class AndroidKeyManager : KeyManager {

    private val keyStoreType = "AndroidKeyStore"
    private val provider = "AndroidKeyStore"

    private val keyStore by lazy {
        KeyStore.getInstance(keyStoreType).apply {
            load(null)
        }
    }

    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }

    private val charset by lazy {
        charset("UTF-8")
    }

    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider)
    }

    override suspend fun generateKeyPair(alias: String): Result<Unit> = runCatching {
        if (!keyStore.containsAlias(alias)) {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                keyStoreType
            )

            val parameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            )
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                .setUserAuthenticationRequired(false)
                .build()

            keyPairGenerator.initialize(parameterSpec)
            keyPairGenerator.generateKeyPair()

        }
    }

    override suspend fun getPublicKey(alias: String): Result<ByteArray?> = runCatching {
        val entry = keyStore.getEntry(alias, null) as? KeyStore.PrivateKeyEntry
        entry?.certificate?.publicKey?.encoded
    }

    override suspend fun signData(alias: String, data: ByteArray): Result<ByteArray> = runCatching {
        val entry = keyStore.getEntry(alias, null) as? KeyStore.PrivateKeyEntry
            ?: throw AuthError.KeyStoreError("Key not found for alias: $alias")

        val signature = Signature.getInstance("SHA256withECDSA")
        signature.initSign(entry.privateKey)
        signature.update(data)
        signature.sign()
    }

    override suspend fun deleteKey(alias: String): Result<Unit> = runCatching {
        keyStore.deleteEntry(alias)
    }

    override suspend fun hasKey(alias: String): Result<Boolean> = runCatching {
        keyStore.containsAlias(alias)
    }

    fun encryptData(keyAlias: String, text: String): Pair<ByteArray, ByteArray> {
        val secretKey = generateSecretKey(keyAlias)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedData = cipher.doFinal(text.toByteArray(charset))
        val iv = cipher.iv
        return Pair(iv, encryptedData)
    }

    fun decryptData(keyAlias: String, iv: ByteArray, encryptedData: ByteArray): String {
        val secretKey = getSecretKey(keyAlias)
        val gcmParameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)
        return cipher.doFinal(encryptedData).toString(charset)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        val keyEntry = keyStore.getEntry(keyAlias, null)
        return if (keyEntry == null) {
             keyGenerator.apply {
                init(
                    KeyGenParameterSpec
                        .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE_GCM)
                        .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                        .build()
                )
            }.generateKey()
        } else {
            getSecretKey(keyAlias)
        }

    }

    private fun getSecretKey(keyAlias: String) =
        (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
}

@org.koin.core.annotation.Single
actual fun providePlatformKeyManager(): KeyManager {
    return AndroidKeyManager()
}
