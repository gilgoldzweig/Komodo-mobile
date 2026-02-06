package ca.glong.komodo.core.auth.tokens

import ca.glong.komodo.core.auth.storage.SecureStorage
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FakeSecureStorage(
    private val failOnSave: Boolean = false,
    private val failOnRead: Boolean = false,
    private val failOnDelete: Boolean = false
) : SecureStorage {
    private val store = mutableMapOf<String, String>()

    override suspend fun save(key: String, value: String): Result<Unit> {
        return if (failOnSave) {
            Result.failure(Exception("Storage save failed"))
        } else {
            store[key] = value
            Result.success(Unit)
        }
    }

    override suspend fun read(key: String): Result<String?> {
        return if (failOnRead) {
            Result.failure(Exception("Storage read failed"))
        } else {
            Result.success(store[key])
        }
    }

    override suspend fun delete(key: String): Result<Unit> {
        return if (failOnDelete) {
            Result.failure(Exception("Storage delete failed"))
        } else {
            store.remove(key)
            Result.success(Unit)
        }
    }

    override suspend fun contains(key: String): Result<Boolean> {
        return Result.success(store.containsKey(key))
    }

    override suspend fun clear(): Result<Unit> {
        store.clear()
        return Result.success(Unit)
    }

    override suspend fun getVersion(): Result<Int> {
        return Result.success(1)
    }

    override suspend fun setVersion(version: Int): Result<Unit> {
        return Result.success(Unit)
    }
}

class TokenRepositoryTest {

    @Test
    fun saveAndGetAccessToken_beforeExpiry_returnsToken() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)
        val testToken = "test_access_token_12345"

        val saveResult = repository.saveAccessToken(testToken, 3600)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertEquals(testToken, getResult.getOrNull())
    }

    @Test
    fun saveAndGetRefreshToken_beforeExpiry_returnsToken() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)
        val testToken = "test_refresh_token_67890"

        val saveResult = repository.saveRefreshToken(testToken, 86400)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getRefreshToken()
        assertTrue(getResult.isSuccess)
        assertEquals(testToken, getResult.getOrNull())
    }

    @Test
    fun getAccessToken_afterExpiry_returnsNull() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)
        val testToken = "expired_access_token"

        val saveResult = repository.saveAccessToken(testToken, -1)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun getRefreshToken_afterExpiry_returnsNull() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)
        val testToken = "expired_refresh_token"

        val saveResult = repository.saveRefreshToken(testToken, -10)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getRefreshToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun clearTokens_removesBothTokens() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)

        repository.saveAccessToken("access_token", 3600)
        repository.saveRefreshToken("refresh_token", 86400)

        val clearResult = repository.clearTokens()
        assertTrue(clearResult.isSuccess)

        val accessResult = repository.getAccessToken()
        assertNull(accessResult.getOrNull())

        val refreshResult = repository.getRefreshToken()
        assertNull(refreshResult.getOrNull())
    }

    @Test
    fun saveToken_withStorageFailure_returnsFailure() = runTest {
        val storage = FakeSecureStorage(failOnSave = true)
        val repository = TokenRepository(storage)

        val saveResult = repository.saveAccessToken("token", 3600)
        assertTrue(saveResult.isFailure)
    }

    @Test
    fun getToken_withInvalidStoredFormat_returnsNull() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)

        storage.save("access_token", "invalid_format_no_separator")

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun saveToken_withZeroExpiry_immediatelyExpires() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)

        val saveResult = repository.saveAccessToken("token", 0)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun saveToken_withNegativeExpiry_immediatelyExpires() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)

        val saveResult = repository.saveAccessToken("token", -5)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun saveToken_withVeryLongExpiry_returnsToken() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)
        val testToken = "long_lived_token"

        val saveResult = repository.saveAccessToken(testToken, 31536000)
        assertTrue(saveResult.isSuccess)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertEquals(testToken, getResult.getOrNull())
    }

    @Test
    fun getToken_whenStorageReadFails_returnsFailure() = runTest {
        val storage = FakeSecureStorage(failOnRead = true)
        val repository = TokenRepository(storage)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isFailure)
    }

    @Test
    fun clearTokens_whenDeleteFails_returnsFailure() = runTest {
        val storage = FakeSecureStorage(failOnDelete = true)
        val repository = TokenRepository(storage)

        repository.saveAccessToken("token", 3600)

        val clearResult = repository.clearTokens()
        assertTrue(clearResult.isFailure)
    }

    @Test
    fun getToken_whenNoTokenStored_returnsNull() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun getToken_withInvalidExpiryFormat_returnsNull() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)

        storage.save("access_token", "token|not_a_number")

        val getResult = repository.getAccessToken()
        assertTrue(getResult.isSuccess)
        assertNull(getResult.getOrNull())
    }

    @Test
    fun tokenFormat_storesCorrectSeparatorAndTimestamp() = runTest {
        val storage = FakeSecureStorage()
        val repository = TokenRepository(storage)
        val testToken = "test_token_format"

        val beforeSave = Clock.System.now().toEpochMilliseconds()
        repository.saveAccessToken(testToken, 3600)
        val afterSave = Clock.System.now().toEpochMilliseconds()

        val storedValue = storage.read("access_token").getOrNull()
        assertNotNull(storedValue)

        val parts = storedValue.split("|")
        assertEquals(2, parts.size)
        assertEquals(testToken, parts[0])

        val expiryTimestamp = parts[1].toLong()
        val expectedExpiry = beforeSave + 3600000
        assertTrue(expiryTimestamp in expectedExpiry..(afterSave + 3600000 + 100))
    }
}
