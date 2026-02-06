package ca.glong.komodo.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    fun create(config: HttpClientConfig): HttpClient = HttpClient(createPlatformEngine()) {
        install(ContentNegotiation) {
            json(json)
        }

        if (config.enableLogging) {
            install(Logging) {
                level = LogLevel.BODY
            }
        }

        config.tokenProvider?.let { tokenProvider ->
            install(AuthInterceptor) {
                this.tokenProvider = tokenProvider
            }
        }

        defaultRequest {
            url(config.baseUrl)
            contentType(ContentType.Application.Json)
        }
    }
}
