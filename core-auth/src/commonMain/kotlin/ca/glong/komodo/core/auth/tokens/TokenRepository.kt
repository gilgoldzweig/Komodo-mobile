package ca.glong.komodo.core.auth.tokens

import ca.glong.komodo.core.auth.storage.SecureStorage
import kotlinx.datetime.Clock

/**
 * TokenRepository manages access and refresh tokens with lazy TTL expiration.
 * 
 * Tokens are stored in format: "{token}|{expiryEpochMillis}"
 * Expiration is checked lazily on read, not proactively with timers.
 */
class TokenRepository(
    private val secureStorage: SecureStorage
) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val SEPARATOR = "|"
    }

    /**
     * Save access token with expiration time.
     * @param token The access token string
     * @param expiresInSeconds Time to live in seconds from now
     * @return Result<Unit> Success or storage failure
     */
    suspend fun saveAccessToken(token: String, expiresInSeconds: Long): Result<Unit> {
        val expiryTimestamp = Clock.System.now().toEpochMilliseconds() + (expiresInSeconds * 1000)
        val value = "$token$SEPARATOR$expiryTimestamp"
        return secureStorage.save(ACCESS_TOKEN_KEY, value)
    }

    /**
     * Save refresh token with expiration time.
     * @param token The refresh token string
     * @param expiresInSeconds Time to live in seconds from now
     * @return Result<Unit> Success or storage failure
     */
    suspend fun saveRefreshToken(token: String, expiresInSeconds: Long): Result<Unit> {
        val expiryTimestamp = Clock.System.now().toEpochMilliseconds() + (expiresInSeconds * 1000)
        val value = "$token$SEPARATOR$expiryTimestamp"
        return secureStorage.save(REFRESH_TOKEN_KEY, value)
    }

    /**
     * Get access token if it exists and is not expired.
     * @return Result<String?> Token if valid and not expired, null if expired, failure on storage error
     */
    suspend fun getAccessToken(): Result<String?> {
        return getToken(ACCESS_TOKEN_KEY)
    }

    /**
     * Get refresh token if it exists and is not expired.
     * @return Result<String?> Token if valid and not expired, null if expired, failure on storage error
     */
    suspend fun getRefreshToken(): Result<String?> {
        return getToken(REFRESH_TOKEN_KEY)
    }

    /**
     * Clear both access and refresh tokens.
     * @return Result<Unit> Success or storage failure
     */
    suspend fun clearTokens(): Result<Unit> {
        val deleteAccessResult = secureStorage.delete(ACCESS_TOKEN_KEY)
        if (deleteAccessResult.isFailure) {
            return deleteAccessResult
        }
        
        val deleteRefreshResult = secureStorage.delete(REFRESH_TOKEN_KEY)
        return deleteRefreshResult
    }

    private suspend fun getToken(key: String): Result<String?> {
        return secureStorage.read(key).mapCatching { storedValue ->
            if (storedValue == null) {
                return@mapCatching null
            }

            val parts = storedValue.split(SEPARATOR)
            if (parts.size != 2) {
                return@mapCatching null
            }

            val token = parts[0]
            val expiryTimestamp = parts[1].toLongOrNull() ?: return@mapCatching null

            val currentTime = Clock.System.now().toEpochMilliseconds()
            if (currentTime >= expiryTimestamp) {
                null
            } else {
                token
            }
        }
    }
}
