package ca.glong.komodo.core.db.driver

import android.content.Context
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ca.glong.komodo.core.db.keys.DatabaseKeyManager
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

/**
 * Android implementation of encrypted database driver using SQLCipher.
 *
 * Security guarantees:
 * - Database keys retrieved from hardware-backed Android Keystore
 * - Keys are zeroed immediately after SQLCipher initialization (no lingering in memory)
 * - SQLCipher provides AES-256 transparent database encryption
 *
 * Key lifecycle:
 * 1. DatabaseKeyManager retrieves key (unwrapped from Keystore-encrypted storage)
 * 2. Key passed to SupportFactory for SQLCipher initialization
 * 3. Key zeroed via fill(0) in finally block
 * 4. SQLCipher handles encrypted I/O transparently
 *
 * @param context Android application context for database file storage
 * @param keyManager Provides hardware-backed database encryption keys
 */
internal class AndroidEncryptedDriverFactory(
	private val context: Context,
	private val keyManager: DatabaseKeyManager
) : EncryptedDriverFactory {
	private var currentDriver: SqlDriver? = null

	override suspend fun createDriver(databaseName: String): Result<SqlDriver> {
		return keyManager.getOrCreateDatabaseKey().mapCatching { keyBytes ->
			try {
				val factory = SupportOpenHelperFactory(keyBytes)
				val driver = AndroidSqliteDriver(
					schema = object : SqlSchema<QueryResult.Value<Unit>> {
						override val version: Long = 1
						override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
							return QueryResult.Unit
						}
						override fun migrate(
							driver: SqlDriver,
							oldVersion: Long,
							newVersion: Long,
							vararg callbacks: AfterVersion
						): QueryResult.Value<Unit> {
							return QueryResult.Unit
						}
					},
					context = context,
					name = databaseName,
					factory = factory
				)
				currentDriver = driver
				driver
			} finally {
				keyBytes.fill(0)
			}
		}
	}

	override suspend fun closeDriver(): Result<Unit> {
		return runCatching {
			currentDriver?.close()
			currentDriver = null
		}
	}
}
