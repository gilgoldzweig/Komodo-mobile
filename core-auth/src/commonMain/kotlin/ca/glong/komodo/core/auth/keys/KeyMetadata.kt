package ca.glong.komodo.core.auth.keys

import kotlinx.serialization.Serializable

@Serializable
data class KeyMetadata(
    val alias: String,
    val algorithm: KeyAlgorithm,
    val createdAt: Long,
    val isPrivate: Boolean,
    val keySize: Int,
    val metadata: Map<String, String> = emptyMap()
)
