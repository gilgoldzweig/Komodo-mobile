package ca.glong.komodo.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import ca.glong.komodo.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private val KEY_SERVER_URL = stringPreferencesKey("server_url")
    private val KEY_API_TOKEN = stringPreferencesKey("api_token")

    override fun getServerUrl() = dataStore.data.map { preferences ->
        preferences[KEY_SERVER_URL]
    }

    override suspend fun setServerUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[KEY_SERVER_URL] = url
        }
    }

    override fun getApiToken() = dataStore.data.map { preferences ->
        preferences[KEY_API_TOKEN]
    }

    override suspend fun setApiToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_API_TOKEN] = token
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_API_TOKEN)
        }
    }
}
