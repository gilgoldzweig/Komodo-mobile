package ca.glong.komodo.core.db.keys

import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.storage.SecureStorage
import kotlinx.coroutines.runBlocking
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

internal class DatabaseKeyManagerImpl(
    private val envelopeEncryption: EnvelopeEncryption,
    private val secureStorage: SecureStorage
) : DatabaseKeyManager {

    override suspend fun getOrCreateDatabaseKey(): Result<ByteArray> = runCatching {
        val storedWrapped = readWrappedKey()
            .getOrElse { throw it }

        if (storedWrapped != null) {
            unwrapWithMasterKey(storedWrapped)
        } else {
            val newKey = generateSecureRandomBytes(DB_KEY_SIZE_BYTES)
            val wrapped = wrapWithMasterKey(newKey)
            saveWrappedKey(wrapped)
            newKey
        }
    }

    override fun hasDatabaseKey(): Result<Boolean> = runCatching {
        runBlocking {
            secureStorage.read(STORAGE_KEY)
                .map { it != null }
                .getOrElse { throw it }
        }
    }

    override suspend fun deleteDatabaseKey(): Result<Unit> = runCatching {
        secureStorage.delete(STORAGE_KEY)
            .getOrElse { throw it }
    }

    override suspend fun exportWrappedKey(recoveryKey: ByteArray): Result<ByteArray> = runCatching {
        val storedWrapped = readWrappedKey()
            .getOrElse { throw it }
            ?: throw IllegalStateException("Database key not found")
        val dbKey = unwrapWithMasterKey(storedWrapped)
        val recoveryAlias = recoveryAlias(recoveryKey)
        envelopeEncryption.encrypt(dbKey, recoveryAlias)
            .getOrElse { throw it }
    }

    override suspend fun importWrappedKey(
        wrappedKey: ByteArray,
        recoveryKey: ByteArray
    ): Result<Unit> = runCatching {
        val recoveryAlias = recoveryAlias(recoveryKey)
        val dbKey = envelopeEncryption.decrypt(wrappedKey, recoveryAlias)
            .getOrElse { throw it }
        val wrapped = wrapWithMasterKey(dbKey)
        saveWrappedKey(wrapped)
    }

    private suspend fun readWrappedKey(): Result<ByteArray?> =
        secureStorage.read(STORAGE_KEY).mapCatching { stored ->
            stored?.let { decodeBase64(it) }
        }

    private suspend fun saveWrappedKey(wrapped: ByteArray): Result<Unit> =
        secureStorage.save(STORAGE_KEY, encodeBase64(wrapped))

    private suspend fun wrapWithMasterKey(data: ByteArray): ByteArray =
        envelopeEncryption.encrypt(data, KEY_ALIAS)
            .getOrElse { throw it }

    private suspend fun unwrapWithMasterKey(wrapped: ByteArray): ByteArray =
        envelopeEncryption.decrypt(wrapped, KEY_ALIAS)
            .getOrElse { throw it }

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeBase64(bytes: ByteArray): String = Base64.Default.encode(bytes)

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64(encoded: String): ByteArray = Base64.Default.decode(encoded)

    @OptIn(ExperimentalEncodingApi::class)
    private fun recoveryAlias(recoveryKey: ByteArray): String =
        "core-db/recovery/" + Base64.Default.encode(recoveryKey)

    private companion object {
        const val KEY_ALIAS = "komodo_core_db_master_v1"
        const val STORAGE_KEY = "core-db/wrapped_db_key_v1"
        const val DB_KEY_SIZE_BYTES = 32
    }
}
