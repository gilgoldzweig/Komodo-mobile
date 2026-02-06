package ca.glong.komodo.feature.passkeytest.data

import ca.glong.komodo.feature.passkeytest.data.dto.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

@Serializable
internal data class LoginFinishResponse(val data: LoginFinishData)

@Serializable
internal data class LoginFinishData(val access_token: String)

interface PasskeyTestRepository {
    suspend fun startRegistration(username: String, email: String): Result<RegisterData>
    suspend fun finishRegistration(handshakeId: String, credential: RegisterFinishRequest): Result<Unit>
    suspend fun startLogin(username: String): Result<LoginData>
    suspend fun finishLogin(request: LoginFinishRequest): Result<String>
}

@Single(binds = [PasskeyTestRepository::class])
class PasskeyTestRepositoryImpl(
    private val httpClient: HttpClient
) : PasskeyTestRepository {
    
    private val baseUrl = "http://localhost:1111/api/v1/auth"
    
    override suspend fun startRegistration(username: String, email: String): Result<RegisterData> = 
        runCatching {
            val response: RegisterStartResponse = httpClient.post("$baseUrl/webauthn/register/start") {
                contentType(ContentType.Application.Json)
                setBody(RegisterStartRequest(username, email))
            }.body()
            response.data
        }
    
    override suspend fun finishRegistration(handshakeId: String, credential: RegisterFinishRequest): Result<Unit> =
        runCatching {
            httpClient.post("$baseUrl/webauthn/register/finish") {
                contentType(ContentType.Application.Json)
                header("Handshake-ID", handshakeId)
                setBody(credential)
            }
            Unit
        }
    
    override suspend fun startLogin(username: String): Result<LoginData> =
        runCatching {
            val response: LoginStartResponse = httpClient.post("$baseUrl/webauthn/login/start") {
                contentType(ContentType.Application.Json)
                setBody(LoginStartRequest(username))
            }.body()
            response.data
        }
    
    override suspend fun finishLogin(request: LoginFinishRequest): Result<String> =
        runCatching {
            val response: LoginFinishResponse = httpClient.post("$baseUrl/webauthn/login/finish") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
            response.data.access_token
        }
}
