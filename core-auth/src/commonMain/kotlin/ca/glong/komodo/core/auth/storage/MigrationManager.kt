package ca.glong.komodo.core.auth.storage

interface MigrationManager {
    suspend fun migrate(fromVersion: Int, toVersion: Int): Result<Unit>
    suspend fun getCurrentVersion(): Result<Int>
    suspend fun getLatestVersion(): Result<Int>
}
