package ca.glong.komodo.core.auth.keys

import org.koin.core.annotation.Single

interface KeyManager {
    suspend fun generateKeyPair(alias: String, type: KeyType = KeyType.EC_P256): Result<Unit>
    suspend fun getPublicKey(alias: String): Result<ByteArray?>
    suspend fun signData(alias: String, data: ByteArray): Result<ByteArray>
    suspend fun deleteKey(alias: String): Result<Unit>
    suspend fun hasKey(alias: String): Result<Boolean>
    suspend fun exportSshKey(alias: String): Result<String>
}

enum class KeyType {
    RSA_4096,
    EC_P256,
    ED25519
}

@Single
expect fun providePlatformKeyManager(): KeyManager
