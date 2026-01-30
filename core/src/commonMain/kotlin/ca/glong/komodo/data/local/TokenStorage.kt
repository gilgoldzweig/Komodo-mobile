package ca.glong.komodo.data.local

interface TokenStorage {
    suspend fun saveToken(id: String, token: String)
    suspend fun getToken(id: String): String?
    suspend fun deleteToken(id: String)
    suspend fun deleteAllTokens()
}
