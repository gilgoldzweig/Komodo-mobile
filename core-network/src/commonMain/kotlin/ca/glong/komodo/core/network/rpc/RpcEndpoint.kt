package ca.glong.komodo.core.network.rpc

enum class RpcEndpoint(val path: String) {
    AUTH("/auth"),
    READ("/read"),
    WRITE("/write"),
    EXECUTE("/execute")
}
