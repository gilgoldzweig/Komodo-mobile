package ca.glong.komodo.feature.resources.ui

import androidx.lifecycle.ViewModel
import ca.glong.komodo.feature.resources.api.ResourceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class ResourceListViewModel : ViewModel() {
    private val _resources = MutableStateFlow<List<ResourceItem>>(emptyList())
    val resources = _resources.asStateFlow()
    
    init {
        loadResources()
    }
    
    private fun loadResources() {
        // TODO: Load actual resources from repository
        _resources.value = listOf(
            ResourceItem("server-1", "Production Server", ResourceType.SERVER),
            ResourceItem("stack-1", "Backend Stack", ResourceType.STACK),
            ResourceItem("deploy-1", "API Deployment", ResourceType.DEPLOYMENT),
            ResourceItem("server-2", "Dev Server", ResourceType.SERVER),
        )
    }
}

data class ResourceItem(
    val id: String,
    val name: String,
    val type: ResourceType
)
