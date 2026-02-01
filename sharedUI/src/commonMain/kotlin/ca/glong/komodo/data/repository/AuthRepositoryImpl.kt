package ca.glong.komodo.data.repository

import ca.glong.komodo.data.dto.EmptyParams
import ca.glong.komodo.data.dto.GetLoginOptionsResponse
import ca.glong.komodo.data.dto.JwtResponse
import ca.glong.komodo.data.dto.LoginLocalUserParams
import ca.glong.komodo.data.dto.RpcRequest
import ca.glong.komodo.domain.repository.AuthRepository
import ca.glong.komodo.domain.repository.SettingsRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Single

@Single(binds = [AuthRepository::class])
class AuthRepositoryImpl(
    private val httpClient: HttpClient,
//    private val settingsRepository: SettingsRepository
) : AuthRepository {

    private val logger = KotlinLogging.logger {}

    override suspend fun getLoginOptions(url: String): Result<GetLoginOptionsResponse> = runCatching {
        val cleanUrl = if (url.endsWith("/")) url.dropLast(1) else url
        val fullUrl = "$cleanUrl/auth"

        httpClient.post(fullUrl) {
            contentType(ContentType.Application.Json)
            setBody(RpcRequest("GetLoginOptions", EmptyParams()))
        }.body()
    }

    override suspend fun loginLocal(username: String, password: String): Result<Unit> = runCatching {
        val params = LoginLocalUserParams(username, password)
        logger.debug { "Attempting login for user: $username" }

        val response = httpClient.post("/auth") {
            contentType(ContentType.Application.Json)
            setBody(RpcRequest("LoginLocalUser", params))
        }.body<JwtResponse>()

        logger.debug { "Login successful, saving token" }
//        settingsRepository.setApiToken(response.token)
    }
}
