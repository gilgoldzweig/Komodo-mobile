package ca.glong.komodo.core.auth.storage

import ca.glong.komodo.core.auth.error.AuthError

interface SecureStorage {
    suspend fun save(key: String, value: String): Result<Unit>
    suspend fun read(key: String): Result<String?>
    suspend fun delete(key: String): Result<Unit>
    suspend fun contains(key: String): Result<Boolean>
}
