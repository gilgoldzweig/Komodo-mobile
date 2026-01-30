package ca.glong.komodo.feature.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.ui.Screen
import ca.glong.komodo.feature.dashboard.ui.DashboardScreen as DashboardScreenUI
import ca.glong.komodo.feature.dashboard.ui.DashboardViewModel

class DashboardScreen(
    private val onNavigateToResources: () -> Unit,
    private val onNavigateToAlerts: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel { DashboardViewModel() }
        DashboardScreenUI(
            viewModel = viewModel,
            onNavigateToResources = onNavigateToResources,
            onNavigateToAlerts = onNavigateToAlerts
        )
    }
}
