package ca.glong.komodo.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getServerUrl(): Flow<String?>
    suspend fun setServerUrl(url: String)
    fun getApiToken(): Flow<String?>
    suspend fun setApiToken(token: String)
    suspend fun clearSession()
}
