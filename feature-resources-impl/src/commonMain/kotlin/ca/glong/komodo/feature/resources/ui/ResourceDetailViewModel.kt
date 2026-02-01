package ca.glong.komodo.feature.resources.ui

import androidx.lifecycle.ViewModel
import ca.glong.komodo.feature.resources.api.ResourceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class ResourceDetailViewModel(
    private val resourceId: String,
    private val resourceType: ResourceType
) : ViewModel() {
    private val _resource = MutableStateFlow<ResourceDetail?>(null)
    val resource = _resource.asStateFlow()

    init {
        loadResource()
    }

    private fun loadResource() {
        _resource.value = ResourceDetail(
            id = resourceId,
            name = "Resource $resourceId",
            type = resourceType,
            status = "Running",
            description = "Sample ${resourceType.name.lowercase()}"
        )
    }
}

data class ResourceDetail(
    val id: String,
    val name: String,
    val type: ResourceType,
    val status: String,
    val description: String
)
