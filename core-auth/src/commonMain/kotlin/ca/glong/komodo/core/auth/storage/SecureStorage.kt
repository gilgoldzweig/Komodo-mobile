package ca.glong.komodo.core.auth.storage

/**
 * Platform-agnostic interface for secure storage operations.
 *
 * On Android: Implemented using EncryptedDataStore with MasterKey
 * On iOS: Implemented using Keychain Services
 *
 * All operations return kotlin.Result to handle platform-specific failures gracefully.
 */
interface SecureStorage {
    /**
     * Saves a string value securely.
     *
     * @param key The key under which to store the value
     * @param value The string value to store
     * @return Result indicating success or a specific storage error
     */
    suspend fun save(key: String, value: String): Result<Unit>

    /**
     * Retrieves a string value from secure storage.
     *
     * @param key The key of the value to retrieve
     * @return Result containing the value or a storage error (including DataNotFound)
     */
    suspend fun read(key: String): Result<String>

    /**
     * Deletes a value from secure storage.
     *
     * @param key The key of the value to delete
     * @return Result indicating success or a specific storage error
     */
    suspend fun delete(key: String): Result<Unit>

    /**
     * Checks if a key exists in secure storage.
     *
     * @param key The key to check
     * @return Result containing true if the key exists, false otherwise
     */
    suspend fun contains(key: String): Result<Boolean>
}

/**
 * Keys used for storing authentication-related data.
 */
object SecureStorageKeys {
    const val API_KEY = "komodo_api_key"
    const val API_SECRET = "komodo_api_secret"
    const val SERVER_URL = "komodo_server_url"
    const val PRIVATE_KEY = "komodo_private_key"
    const val PUBLIC_KEY = "komodo_public_key"
    const val JWT_TOKEN = "komodo_jwt_token"
    const val REFRESH_TOKEN = "komodo_refresh_token"
    const val USER_ID = "komodo_user_id"
}
