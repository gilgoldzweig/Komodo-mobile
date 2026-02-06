package ca.glong.komodo.core.auth.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import ca.glong.komodo.core.auth.error.AuthError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class AndroidSecureStorageTest {

    private lateinit var context: Context
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var storage: AndroidSecureStorage
    private lateinit var testDataStoreFile: File
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        testDataStoreFile = File(context.filesDir, "test_datastore.preferences_pb")
        
        dataStore = PreferenceDataStoreFactory.create(
            scope = kotlinx.coroutines.CoroutineScope(testDispatcher),
            produceFile = { testDataStoreFile }
        )
        
        storage = AndroidSecureStorage(dataStore, context)
    }
    
    @After
    fun tearDown() {
        testDataStoreFile.delete()
        File(context.filesDir, "tink_keyset").delete()
    }

    @Test
    fun `save and read string successfully`() = runTest(testDispatcher) {
        val key = "test_key"
        val value = "test_value"
        
        val saveResult = storage.save(key, value)
        assertTrue(saveResult.isSuccess, "Save should succeed")
        
        val readResult = storage.read(key)
        assertTrue(readResult.isSuccess, "Read should succeed")
        assertEquals(value, readResult.getOrNull(), "Read value should match saved value")
    }

    @Test
    fun `read returns null for missing key`() = runTest(testDispatcher) {
        val result = storage.read("missing_key")
        assertTrue(result.isSuccess, "Read should succeed even for missing key")
        assertNull(result.getOrNull(), "Missing key should return null")
    }

    @Test
    fun `delete removes key`() = runTest(testDispatcher) {
        val key = "to_delete"
        storage.save(key, "value").getOrThrow()
        
        val containsBeforeResult = storage.contains(key)
        assertTrue(containsBeforeResult.isSuccess && containsBeforeResult.getOrNull() == true)
        
        val deleteResult = storage.delete(key)
        assertTrue(deleteResult.isSuccess, "Delete should succeed")
        
        val containsAfterResult = storage.contains(key)
        assertTrue(containsAfterResult.isSuccess && containsAfterResult.getOrNull() == false)
    }

    @Test
    fun `contains returns true for existing key`() = runTest(testDispatcher) {
        val key = "exists"
        storage.save(key, "value").getOrThrow()
        
        val result = storage.contains(key)
        assertTrue(result.isSuccess, "Contains should succeed")
        assertTrue(result.getOrNull() == true, "Contains should return true for existing key")
    }

    @Test
    fun `contains returns false for missing key`() = runTest(testDispatcher) {
        val result = storage.contains("missing")
        assertTrue(result.isSuccess, "Contains should succeed")
        assertFalse(result.getOrNull() == true, "Contains should return false for missing key")
    }

    @Test
    fun `clear removes all entries`() = runTest(testDispatcher) {
        storage.save("key1", "value1").getOrThrow()
        storage.save("key2", "value2").getOrThrow()
        storage.save("key3", "value3").getOrThrow()
        
        val clearResult = storage.clear()
        assertTrue(clearResult.isSuccess, "Clear should succeed")
        
        val contains1 = storage.contains("key1").getOrNull()
        val contains2 = storage.contains("key2").getOrNull()
        val contains3 = storage.contains("key3").getOrNull()
        
        assertFalse(contains1 == true, "key1 should not exist after clear")
        assertFalse(contains2 == true, "key2 should not exist after clear")
        assertFalse(contains3 == true, "key3 should not exist after clear")
    }

    @Test
    fun `version tracking get and set works`() = runTest(testDispatcher) {
        val initialVersion = storage.getVersion()
        assertTrue(initialVersion.isSuccess, "getVersion should succeed")
        assertEquals(1, initialVersion.getOrNull(), "Default version should be 1")
        
        val setResult = storage.setVersion(5)
        assertTrue(setResult.isSuccess, "setVersion should succeed")
        
        val newVersion = storage.getVersion()
        assertTrue(newVersion.isSuccess, "getVersion should succeed")
        assertEquals(5, newVersion.getOrNull(), "Version should be updated to 5")
    }

    @Test
    fun `encryption round-trip verifies data is actually encrypted`() = runTest(testDispatcher) {
        val plaintext = "sensitive_data"
        val key = "secure_key"
        
        storage.save(key, plaintext).getOrThrow()
        
        // Read raw value directly from DataStore (bypassing decryption)
        val rawValue = dataStore.data.map { prefs ->
            prefs[stringPreferencesKey(key)]
        }.first()
        
        assertNotNull(rawValue, "Encrypted value should exist in DataStore")
        assertNotEquals(plaintext, rawValue, "Stored value should be encrypted, not plaintext")
        assertTrue(rawValue.length > plaintext.length, "Encrypted value should be longer than plaintext")
        
        val decrypted = storage.read(key).getOrNull()
        assertEquals(plaintext, decrypted, "Decrypted value should match original plaintext")
    }

    @Test
    fun `corrupt data returns CorruptionDetected error`() = runTest(testDispatcher) {
        val key = "corrupt_key"
        
        val rawDataStore = PreferenceDataStoreFactory.create(
            scope = kotlinx.coroutines.CoroutineScope(testDispatcher),
            produceFile = { testDataStoreFile }
        )
        
        rawDataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = "not_valid_base64_or_encrypted_data!!!"
        }
        
        val readResult = storage.read(key)
        assertTrue(readResult.isFailure, "Reading corrupted data should fail")
        
        val exception = readResult.exceptionOrNull()
        assertIs<AuthError.StorageError.CorruptionDetected>(exception, "Should return CorruptionDetected error")
    }

    @Test
    fun `invalid base64 returns CorruptionDetected error`() = runTest(testDispatcher) {
        val key = "invalid_base64"
        
        val rawDataStore = PreferenceDataStoreFactory.create(
            scope = kotlinx.coroutines.CoroutineScope(testDispatcher),
            produceFile = { testDataStoreFile }
        )
        
        rawDataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = "invalid base64!@#$%"
        }
        
        val readResult = storage.read(key)
        assertTrue(readResult.isFailure, "Reading invalid base64 should fail")
        
        val exception = readResult.exceptionOrNull()
        assertIs<AuthError.StorageError.CorruptionDetected>(exception)
    }

    @Test
    fun `empty string encrypts and decrypts correctly`() = runTest(testDispatcher) {
        val key = "empty_key"
        val emptyValue = ""
        
        val saveResult = storage.save(key, emptyValue)
        assertTrue(saveResult.isSuccess, "Saving empty string should succeed")
        
        val readResult = storage.read(key)
        assertTrue(readResult.isSuccess, "Reading empty string should succeed")
        assertEquals(emptyValue, readResult.getOrNull(), "Empty string should round-trip correctly")
    }

    @Test
    fun `large string encrypts and decrypts correctly`() = runTest(testDispatcher) {
        val key = "large_key"
        val largeValue = "x".repeat(10000)
        
        val saveResult = storage.save(key, largeValue)
        assertTrue(saveResult.isSuccess, "Saving large string should succeed")
        
        val readResult = storage.read(key)
        assertTrue(readResult.isSuccess, "Reading large string should succeed")
        assertEquals(largeValue, readResult.getOrNull(), "Large string should round-trip correctly")
    }

    @Test
    fun `unicode strings encrypt and decrypt correctly`() = runTest(testDispatcher) {
        val key = "unicode_key"
        val unicodeValue = "Hello 世界 🌍 Привет"
        
        val saveResult = storage.save(key, unicodeValue)
        assertTrue(saveResult.isSuccess, "Saving unicode string should succeed")
        
        val readResult = storage.read(key)
        assertTrue(readResult.isSuccess, "Reading unicode string should succeed")
        assertEquals(unicodeValue, readResult.getOrNull(), "Unicode string should round-trip correctly")
    }

    @Test
    fun `multiple keys can coexist`() = runTest(testDispatcher) {
        storage.save("key1", "value1").getOrThrow()
        storage.save("key2", "value2").getOrThrow()
        storage.save("key3", "value3").getOrThrow()
        
        assertEquals("value1", storage.read("key1").getOrNull())
        assertEquals("value2", storage.read("key2").getOrNull())
        assertEquals("value3", storage.read("key3").getOrNull())
    }

    @Test
    fun `overwriting key updates value`() = runTest(testDispatcher) {
        val key = "overwrite_key"
        
        storage.save(key, "first_value").getOrThrow()
        assertEquals("first_value", storage.read(key).getOrNull())
        
        storage.save(key, "second_value").getOrThrow()
        assertEquals("second_value", storage.read(key).getOrNull())
    }
}
