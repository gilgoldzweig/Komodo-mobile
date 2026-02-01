package ca.glong.komodo.feature.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.dashboard.ui.DashboardScreen
import ca.glong.komodo.feature.dashboard.ui.DashboardViewModel
import dev.zacsweers.metrox.viewmodel.metroViewModel

/**
 * Dashboard feature navigation entries.
 */
fun EntryProviderScope<Any>.featureDashboardEntries() {
    entry<DashboardKey> {
        val viewModel = metroViewModel<DashboardViewModel>()
        DashboardScreen(viewModel = viewModel)
    }
}
