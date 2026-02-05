package ca.glong.komodo.core.auth.storage

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
abstract class SecureStorageContractTest {

    protected abstract fun createStorage(): SecureStorage

    @Test
    fun saveStoresValueSuccessfully() = runTest {
        val storage = createStorage()
        val result = storage.save("test_key", "test_value")
        assertTrue(result.isSuccess)

        val readResult = storage.read("test_key")
        assertTrue(readResult.isSuccess)
        assertEquals("test_value", readResult.getOrNull())
    }

    @Test
    fun readReturnsNullForMissingKey() = runTest {
        val storage = createStorage()
        val result = storage.read("missing_key")
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun containsReturnsTrueForExistingKey() = runTest {
        val storage = createStorage()
        storage.save("exists", "value")
        val result = storage.contains("exists")
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }

    @Test
    fun containsReturnsFalseForMissingKey() = runTest {
        val storage = createStorage()
        val result = storage.contains("missing")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull() == true)
    }

    @Test
    fun deleteRemovesKey() = runTest {
        val storage = createStorage()
        storage.save("to_delete", "value")
        assertTrue(storage.contains("to_delete").getOrNull() == true)

        val deleteResult = storage.delete("to_delete")
        assertTrue(deleteResult.isSuccess)

        val checkResult = storage.contains("to_delete")
        assertFalse(checkResult.getOrNull() == true)
    }
}
