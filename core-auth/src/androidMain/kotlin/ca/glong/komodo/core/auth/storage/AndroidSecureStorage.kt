package ca.glong.komodo.core.auth.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import ca.glong.komodo.core.auth.error.AuthError
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AndroidSecureStorage(
    private val dataStore: DataStore<Preferences>
) : SecureStorage {

    override suspend fun save(key: String, value: String): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun read(key: String): Result<String?> = runCatching {
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun delete(key: String): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

    override suspend fun contains(key: String): Result<Boolean> = runCatching {
        dataStore.data.map { preferences ->
            preferences.contains(stringPreferencesKey(key))
        }.firstOrNull() ?: false
    }
}
