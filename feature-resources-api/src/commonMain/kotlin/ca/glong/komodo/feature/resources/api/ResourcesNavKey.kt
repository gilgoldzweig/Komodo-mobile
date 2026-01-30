package ca.glong.komodo.feature.resources.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object ResourceListKey : NavKey

@Serializable
data class ResourceDetailKey(
    val id: String,
    val type: ResourceType
) : NavKey

@Serializable
enum class ResourceType {
    SERVER,
    STACK,
    DEPLOYMENT
}

