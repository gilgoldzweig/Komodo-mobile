package ca.glong.komodo.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.bearerAuth

class AuthInterceptorConfig {
    var tokenProvider: (suspend () -> String?)? = null
}

val AuthInterceptor = createClientPlugin("AuthInterceptor", ::AuthInterceptorConfig) {
    val tokenProvider = pluginConfig.tokenProvider

    onRequest { request, _ ->
        tokenProvider?.invoke()?.let { token ->
            request.bearerAuth(token)
        }
    }
}
