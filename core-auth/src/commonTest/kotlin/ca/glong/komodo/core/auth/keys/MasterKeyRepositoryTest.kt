package ca.glong.komodo.core.auth.keys

import ca.glong.komodo.core.auth.storage.SecureStorage
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals
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

class MasterKeyRepositoryTest {

    @Test
    fun getMasterKey_generatesNewKeyOnFirstCall() = runTest {
        val storage = FakeSecureStorage()
        val repository = MasterKeyRepository(storage)

        val result = repository.getMasterKey()

        assertTrue(result.isSuccess, "getMasterKey should succeed")
        val key = result.getOrNull()
        assertEquals(64, key?.length, "Generated key should be 64 characters (32 bytes as hex)")
        assertTrue(key?.matches(Regex("^[0-9a-f]{64}$")) == true, "Key should be valid hex string")
    }

    @Test
    fun getMasterKey_returnsExistingKeyOnSubsequentCalls() = runTest {
        val storage = FakeSecureStorage()
        val repository = MasterKeyRepository(storage)

        val firstResult = repository.getMasterKey()
        val firstKey = firstResult.getOrNull()

        val secondResult = repository.getMasterKey()
        val secondKey = secondResult.getOrNull()

        assertEquals(firstKey, secondKey, "Subsequent calls should return the same key")
    }

    @Test
    fun getMasterKey_generatesValidHexString() = runTest {
        val storage = FakeSecureStorage()
        val repository = MasterKeyRepository(storage)

        val result = repository.getMasterKey()
        val key = result.getOrNull()

        assertTrue(key?.matches(Regex("^[0-9a-f]{64}$")) == true, "Key must be 64-char hex string")
    }

    @Test
    fun clearMasterKey_removesKeyFromStorage() = runTest {
        val storage = FakeSecureStorage()
        val repository = MasterKeyRepository(storage)

        val firstKey = repository.getMasterKey().getOrNull()
        val clearResult = repository.clearMasterKey()
        val secondKey = repository.getMasterKey().getOrNull()

        assertTrue(clearResult.isSuccess, "clearMasterKey should succeed")
        assertNotEquals(firstKey, secondKey, "After clear, a new key should be generated")
    }

    @Test
    fun getMasterKey_generatesNewKeyAfterClear() = runTest {
        val storage = FakeSecureStorage()
        val repository = MasterKeyRepository(storage)

        val beforeClear = repository.getMasterKey().getOrNull()
        repository.clearMasterKey()
        val afterClear = repository.getMasterKey().getOrNull()

        assertNotEquals(
            beforeClear,
            afterClear,
            "Key after clear should be different from key before clear"
        )
    }

    @Test
    fun getMasterKey_handlesStorageReadFailure() = runTest {
        val storage = FakeSecureStorage(failOnRead = true)
        val repository = MasterKeyRepository(storage)

        val result = repository.getMasterKey()

        assertTrue(result.isFailure, "getMasterKey should fail when storage read fails")
    }

    @Test
    fun getMasterKey_handlesSaveFailureAfterGeneration() = runTest {
        val storage = FakeSecureStorage(failOnSave = true)
        val repository = MasterKeyRepository(storage)

        val result = repository.getMasterKey()

        assertTrue(
            result.isFailure,
            "getMasterKey should fail if generated key cannot be saved to storage"
        )
    }

    @Test
    fun clearMasterKey_handlesStorageDeleteFailure() = runTest {
        val storage = FakeSecureStorage(failOnDelete = true)
        val repository = MasterKeyRepository(storage)

        repository.getMasterKey()
        val clearResult = repository.clearMasterKey()

        assertTrue(clearResult.isFailure, "clearMasterKey should fail when storage delete fails")
    }

    @Test
    fun getMasterKey_multipleCallsGenerateSingleKey() = runTest {
        val storage = FakeSecureStorage()
        val repository = MasterKeyRepository(storage)

        val key1 = repository.getMasterKey().getOrNull()
        val key2 = repository.getMasterKey().getOrNull()
        val key3 = repository.getMasterKey().getOrNull()

        assertEquals(key1, key2, "Second call should return same key")
        assertEquals(key2, key3, "Third call should return same key")
    }
}
