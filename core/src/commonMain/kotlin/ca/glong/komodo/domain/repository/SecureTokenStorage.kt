package ca.glong.komodo.domain.repository

interface SecureTokenStorage {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
}
