package ca.glong.komodo.core.auth.storage

data class StorageEntry(
    val key: String,
    val value: String,
    val version: Int,
    val createdAt: Long
)
