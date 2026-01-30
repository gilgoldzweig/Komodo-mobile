package ca.glong.komodo.feature.resources.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.ui.Screen
import ca.glong.komodo.feature.resources.api.ResourceType
import ca.glong.komodo.feature.resources.ui.ResourceDetailScreen as ResourceDetailScreenUI
import ca.glong.komodo.feature.resources.ui.ResourceDetailViewModel
import ca.glong.komodo.feature.resources.ui.ResourceListScreen as ResourceListScreenUI
import ca.glong.komodo.feature.resources.ui.ResourceListViewModel

class ResourceListScreen(
    private val onResourceClick: (String, ResourceType) -> Unit,
    private val onBackClick: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel { ResourceListViewModel() }
        ResourceListScreenUI(
            viewModel = viewModel,
            onResourceClick = onResourceClick,
            onBackClick = onBackClick
        )
    }
}

class ResourceDetailScreen(
    private val resourceId: String,
    private val resourceType: ResourceType,
    private val onBackClick: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel { ResourceDetailViewModel(resourceId, resourceType) }
        ResourceDetailScreenUI(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}
