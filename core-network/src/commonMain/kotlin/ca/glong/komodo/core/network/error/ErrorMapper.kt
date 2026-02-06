package ca.glong.komodo.core.network.error

import ca.glong.komodo.core.network.rpc.RpcError as RpcErrorDto
import io.ktor.http.HttpStatusCode

object ErrorMapper {
    fun fromHttpStatus(status: HttpStatusCode, body: String?): ApiError = when (status) {
        HttpStatusCode.Unauthorized -> ApiError.Unauthorized(body ?: "Authentication required")
        HttpStatusCode.Forbidden -> ApiError.Forbidden(body ?: "Access denied")
        HttpStatusCode.NotFound -> ApiError.NotFound(body ?: "Unknown resource")
        HttpStatusCode.BadRequest -> ApiError.BadRequest(listOfNotNull(body))
        else -> when {
            status.value in 500..599 -> ApiError.ServerError(status.value, body ?: "Server error")
            else -> ApiError.Unknown
        }
    }

    fun fromRpcError(error: RpcErrorDto): ApiError = ApiError.RpcError(
        type = error.error,
        message = error.trace.firstOrNull() ?: error.error
    )

    fun fromException(e: Throwable): ApiError = when (e) {
        is ApiError -> e
        else -> ApiError.NetworkError(e)
    }
}
