package ca.glong.komodo.core.auth.storage

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class AndroidSecureStorageTest {

    private lateinit var context: Context
    private lateinit var storage: AndroidSecureStorage

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        storage = AndroidSecureStorage(context)
        
        // Mock implementation for Robolectric since EncryptedSharedPreferences doesn't work well
        storage.sharedPreferencesProvider = {
            context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `save stores value successfully`() = runTest {
        val result = storage.save("test_key", "test_value")
        assertTrue("Save failed: ${result.exceptionOrNull()}", result.isSuccess)
        
        val readResult = storage.read("test_key")
        assertTrue("Read failed: ${readResult.exceptionOrNull()}", readResult.isSuccess)
        assertEquals("test_value", readResult.getOrNull())
    }

    @Test
    fun `read returns null for missing key`() = runTest {
        val result = storage.read("missing_key")
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun `contains returns true for existing key`() = runTest {
        storage.save("exists", "value")
        val result = storage.contains("exists")
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }

    @Test
    fun `contains returns false for missing key`() = runTest {
        val result = storage.contains("missing")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull() == true)
    }
    
    @Test
    fun `delete removes key`() = runTest {
        storage.save("to_delete", "value")
        assertTrue(storage.contains("to_delete").getOrNull() == true)
        
        val deleteResult = storage.delete("to_delete")
        assertTrue(deleteResult.isSuccess)
        
        val checkResult = storage.contains("to_delete")
        assertFalse(checkResult.getOrNull() == true)
    }
}
