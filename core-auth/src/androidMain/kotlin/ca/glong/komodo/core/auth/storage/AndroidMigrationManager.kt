package ca.glong.komodo.core.auth.storage

import ca.glong.komodo.core.auth.error.AuthError

class AndroidMigrationManager(
    private val secureStorage: SecureStorage
) : MigrationManager {
    
    companion object {
        const val LATEST_VERSION = 1
    }
    
    override suspend fun migrate(fromVersion: Int, toVersion: Int): Result<Unit> = runCatching {
        if (fromVersion >= toVersion) {
            return Result.success(Unit)
        }
        
        for (version in fromVersion until toVersion) {
            val migrationResult = when (version) {
                else -> Result.success(Unit)
            }
            
            if (migrationResult.isFailure) {
                throw AuthError.StorageError.Unavailable(
                    "Migration from version $version to ${version + 1} failed: ${migrationResult.exceptionOrNull()?.message}"
                )
            }
        }
        
        secureStorage.setVersion(toVersion).getOrThrow()
    }
    
    override suspend fun getCurrentVersion(): Result<Int> = 
        secureStorage.getVersion()
    
    override suspend fun getLatestVersion(): Result<Int> = 
        Result.success(LATEST_VERSION)
}
