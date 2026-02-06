package ca.glong.komodo.core.db.keys

/**
 * Manages database encryption keys with hardware-backed security.
 *
 * This interface defines the contract for managing cryptographic keys used to encrypt databases.
 * Keys are generated using CSPRNG and stored encrypted with hardware-backed key wrapping
 * (Android Keystore on Android, Secure Enclave on iOS).
 *
 * Security guarantees:
 * - Keys are generated using platform-specific CSPRNG (never leave hardware-backed storage)
 * - Keys are automatically wrapped with hardware-backed encryption keys
 * - Key deletion is permanent and cryptographic (not just file deletion)
 */
interface DatabaseKeyManager {
	/**
	 * Retrieves the existing database key or generates a new one if none exists.
	 * The key is generated using CSPRNG and stored encrypted with hardware-backed key wrapping.
	 *
	 * @return Result containing 32-byte database encryption key, or error if generation/retrieval fails
	 */
	suspend fun getOrCreateDatabaseKey(): Result<ByteArray>

	/**
	 * Checks if a database key exists in secure storage.
	 *
	 * @return Result containing true if key exists, false otherwise, or error if check fails
	 */
	fun hasDatabaseKey(): Result<Boolean>

	/**
	 * Deletes the stored database key.
	 * WARNING: This will make existing encrypted databases inaccessible.
	 *
	 * @return Result indicating success or failure
	 */
	suspend fun deleteDatabaseKey(): Result<Unit>

	/**
	 * Exports the database key wrapped with a recovery key.
	 * Allows backup and restore across devices.
	 *
	 * @param recoveryKey 32-byte recovery key (derived from user's recovery code)
	 * @return Result containing wrapped key bytes, or error if export fails
	 */
	suspend fun exportWrappedKey(recoveryKey: ByteArray): Result<ByteArray>

	/**
	 * Imports a wrapped database key using a recovery key.
	 * Replaces any existing database key.
	 *
	 * @param wrappedKey Encrypted database key from exportWrappedKey
	 * @param recoveryKey 32-byte recovery key (derived from user's recovery code)
	 * @return Result indicating success or failure
	 */
	suspend fun importWrappedKey(wrappedKey: ByteArray, recoveryKey: ByteArray): Result<Unit>
}

/**
 * Generates cryptographically secure random bytes using platform-specific CSPRNG.
 * - Android: java.security.SecureRandom
 * - iOS: SecRandomCopyBytes with kSecRandomDefault
 *
 * @param size Number of bytes to generate
 * @return ByteArray of random bytes
 */
internal expect fun generateSecureRandomBytes(size: Int): ByteArray
