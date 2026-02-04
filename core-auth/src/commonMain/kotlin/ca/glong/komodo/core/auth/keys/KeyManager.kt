package ca.glong.komodo.core.auth.keys

import io.github.oshai.kotlinlogging.KotlinLogging
import org.koin.core.annotation.Single

interface KeyManager {
    suspend fun generateKeyPair(alias: String): Result<Unit>
    suspend fun getPublicKey(alias: String): Result<ByteArray?>
    suspend fun signData(alias: String, data: ByteArray): Result<ByteArray>
    suspend fun deleteKey(alias: String): Result<Unit>
    suspend fun hasKey(alias: String): Result<Boolean>

    suspend fun encryptData(alias: String, data: ByteArray): Result<Pair<ByteArray, ByteArray>>
    suspend fun decryptData(alias: String, iv: ByteArray, encryptedData: ByteArray): Result<ByteArray>
}

@Single
expect fun providePlatformKeyManager(): KeyManager
