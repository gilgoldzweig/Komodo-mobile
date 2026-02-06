package ca.glong.komodo.core.auth.storage

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import ca.glong.komodo.core.auth.error.AuthError
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AndroidSecureStorage(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) : SecureStorage {

    private val aead: Aead by lazy {
        try {
            AeadConfig.register()
            val keysetHandle = AndroidKeysetManager.Builder()
                .withSharedPref(context, "tink_keyset", "master_keyset")
                .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
                .withMasterKeyUri("android-keystore://master_key")
                .build()
                .keysetHandle
            keysetHandle.getPrimitive(Aead::class.java)
        } catch (e: Exception) {
            throw AuthError.KeyError.KeystoreUnavailable("Failed to initialize Tink AEAD: ${e.message}")
        }
    }

    override suspend fun save(key: String, value: String): Result<Unit> = runCatching {
        try {
            val encryptedBytes = aead.encrypt(value.toByteArray(Charsets.UTF_8), key.toByteArray(Charsets.UTF_8))
            val encryptedString = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = encryptedString
            }
        } catch (e: Exception) {
            throw AuthError.StorageError.WriteFailed(key)
        }
    }

    override suspend fun read(key: String): Result<String?> = runCatching {
        try {
            val encryptedString = dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(key)]
            }.firstOrNull()
            
            if (encryptedString == null) {
                return@runCatching null
            }
            
            val encryptedBytes = Base64.decode(encryptedString, Base64.NO_WRAP)
            val decryptedBytes = aead.decrypt(encryptedBytes, key.toByteArray(Charsets.UTF_8))
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: IllegalArgumentException) {
            throw AuthError.StorageError.CorruptionDetected("Invalid Base64 encoding for key: $key")
        } catch (e: Exception) {
            throw AuthError.StorageError.CorruptionDetected("Decryption failed for key: $key - ${e.message}")
        }
    }

    override suspend fun delete(key: String): Result<Unit> = runCatching {
        try {
            dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(key))
            }
        } catch (e: Exception) {
            throw AuthError.StorageError.WriteFailed(key)
        }
    }

    override suspend fun contains(key: String): Result<Boolean> = runCatching {
        try {
            dataStore.data.map { preferences ->
                preferences.contains(stringPreferencesKey(key))
            }.firstOrNull() ?: false
        } catch (e: Exception) {
            throw AuthError.StorageError.ReadFailed(key)
        }
    }
    
    override suspend fun clear(): Result<Unit> = runCatching {
        try {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        } catch (e: Exception) {
            throw AuthError.StorageError.Unavailable("Failed to clear storage: ${e.message}")
        }
    }
    
    override suspend fun getVersion(): Result<Int> = runCatching {
        try {
            dataStore.data.map { preferences ->
                preferences[stringPreferencesKey("__storage_version__")]?.toIntOrNull() ?: 1
            }.firstOrNull() ?: 1
        } catch (e: Exception) {
            throw AuthError.StorageError.ReadFailed("__storage_version__")
        }
    }
    
    override suspend fun setVersion(version: Int): Result<Unit> = runCatching {
        try {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey("__storage_version__")] = version.toString()
            }
        } catch (e: Exception) {
            throw AuthError.StorageError.WriteFailed("__storage_version__")
        }
    }
}
