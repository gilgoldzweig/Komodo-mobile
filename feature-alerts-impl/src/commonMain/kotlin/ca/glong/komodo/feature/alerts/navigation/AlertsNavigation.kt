package ca.glong.komodo.feature.alerts.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.ui.Screen
import ca.glong.komodo.feature.alerts.ui.AlertsScreen as AlertsScreenUI
import ca.glong.komodo.feature.alerts.ui.AlertsViewModel

class AlertsScreen(
    private val onBackClick: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel { AlertsViewModel() }
        AlertsScreenUI(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}
