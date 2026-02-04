package ca.glong.komodo.core.auth.storage

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import ca.glong.komodo.core.auth.error.AuthError
import ca.glong.komodo.core.auth.keys.KeyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class AndroidSecureStorage(
    val dataStore: DataStore<Preferences>,
    val keyManager: KeyManager
) : SecureStorage {

    val bytesToStringSeperator = "|"
    val keyAlias = "appkey"
    val ivToStringSeparator= ":iv:"

    suspend fun <T> putSecurePreference(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            val result = keyManager.encryptData(keyAlias, value.toByteArray())
            if (result.isFailure) return@edit
            val (iv, secureByteArray) = result.getOrThrow()
            val secureString = iv.joinToString(bytesToStringSeperator) + ivToStringSeparator + secureByteArray.joinToString(bytesToStringSeperator)
            preferences[key] = secureString
        }
    }


    suspend inline fun getSecureString(
        key: Preferences.Key<String>,
        defaultValue: String,
    ): Flow<T> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val secureString = preferences[key] ?: return@map defaultValue
        val (ivString, encryptedString) = secureString.split(ivToStringSeparator, limit = 2)
        val iv = ivString.split(bytesToStringSeperator).map { it.toByte() }.toByteArray()
        val encryptedData = encryptedString.split(bytesToStringSeperator).map { it.toByte() }.toByteArray()
       keyManager.decryptData(keyAlias, iv, encryptedData)
    }


    override suspend fun save(key: String, value: String): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            val result = keyManager.encryptData(keyAlias, value.toByteArray())
            if (result.isFailure) return@edit
            val (iv, secureByteArray) = result.getOrThrow()
            val secureString = iv.joinToString(bytesToStringSeperator) + ivToStringSeparator + secureByteArray.joinToString(bytesToStringSeperator)
            preferences[stringPreferencesKey(key)] = secureString
        }
    }

    override suspend fun read(key: String): Result<String?> {
       val encrypted =  dataStore.data.map { preferences ->
           preferences[stringPreferencesKey(key)]
           Result.failure<AuthError>(AuthError.KeyStoreError)
        }
        ""
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
