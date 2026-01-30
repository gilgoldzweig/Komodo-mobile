package ca.glong.komodo.auth

import ca.glong.komodo.data.local.TokenStorage
import org.koin.core.annotation.Single

interface JWTTokenProvider {
    suspend fun saveJWT(jwt: String)
    suspend fun getJWT(): String?
    suspend fun delete()
}

@Single
class JWTTokenStorage(
    private val tokenStorage: TokenStorage
) : JWTTokenProvider {

    private val keyId = "jwt_token"

    override suspend fun saveJWT(jwt: String) {
        tokenStorage.saveToken(keyId, jwt)
    }

    override suspend fun getJWT(): String? {
        return tokenStorage.getToken(keyId)
    }

    override suspend fun delete() {
        tokenStorage.deleteToken(keyId)
    }
}
