package ca.glong.komodo.core.db.driver

import app.cash.sqldelight.db.SqlDriver

/**
 * Factory for creating encrypted SQLite database drivers.
 *
 * Implementations must ensure:
 * - Database encryption keys are retrieved securely from platform-specific storage
 * - Key material is zeroed immediately after use (no lingering in memory)
 * - Driver lifecycle is managed properly (close before creating new instances)
 *
 * Platform implementations:
 * - Android: Uses SQLCipher with hardware-backed key wrapping via Android Keystore
 * - iOS: Uses SQLCipher with Keychain-wrapped database keys via Secure Enclave
 */
interface EncryptedDriverFactory {
	/**
	 * Creates an encrypted SQLite driver for the specified database.
	 *
	 * Key lifecycle guarantees:
	 * 1. Retrieves encryption key from secure platform storage
	 * 2. Passes key to SQLCipher driver initialization
	 * 3. Immediately zeros key material from memory
	 *
	 * @param databaseName Name of the database file (e.g., "app.db")
	 * @return Result containing SqlDriver instance, or error if driver creation fails
	 */
	suspend fun createDriver(databaseName: String): Result<SqlDriver>

	/**
	 * Closes the currently active database driver and releases resources.
	 *
	 * @return Result indicating success or failure
	 */
	suspend fun closeDriver(): Result<Unit>
}
