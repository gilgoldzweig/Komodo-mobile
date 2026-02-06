package ca.glong.komodo.core.network.client

data class HttpClientConfig(
    val baseUrl: String,
    val tokenProvider: (suspend () -> String?)? = null,
    val enableLogging: Boolean = false
)
