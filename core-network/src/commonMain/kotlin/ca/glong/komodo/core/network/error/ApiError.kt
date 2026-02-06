package ca.glong.komodo.core.network.error

sealed class ApiError : Exception() {
    data class Unauthorized(override val message: String = "Authentication required") : ApiError()

    data class Forbidden(override val message: String = "Access denied") : ApiError()

    data class NotFound(val resource: String) : ApiError() {
        override val message: String = "Resource not found: $resource"
    }

    data class BadRequest(val errors: List<String>) : ApiError() {
        override val message: String = errors.joinToString("; ")
    }

    data class ServerError(val code: Int, override val message: String) : ApiError()

    data class NetworkError(override val cause: Throwable) : ApiError() {
        override val message: String = cause.message ?: "Network error"
    }

    data class RpcError(val type: String, override val message: String) : ApiError()

    data object Unknown : ApiError() {
        override val message: String = "An unknown error occurred"
    }
}
