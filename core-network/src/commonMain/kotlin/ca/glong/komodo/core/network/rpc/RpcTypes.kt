package ca.glong.komodo.core.network.rpc

import kotlinx.serialization.Serializable

@Serializable
data class RpcRequest<T>(
    val type: String,
    val params: T
)

@Serializable
data class RpcError(
    val error: String,
    val trace: List<String> = emptyList()
)
