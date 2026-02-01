package ca.glong.komodo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RpcRequest<T>(
    val type: String, // OperationName
    val params: T
)

@Serializable
data class RpcError(
    val error: String,
    val trace: List<String> = emptyList()
)
