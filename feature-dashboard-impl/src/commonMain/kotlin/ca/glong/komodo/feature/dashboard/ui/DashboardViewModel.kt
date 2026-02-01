package ca.glong.komodo.feature.dashboard.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class DashboardViewModel : ViewModel() {
    // ...existing code...
    private val _stats = MutableStateFlow(DashboardStats())
    val stats = _stats.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        // TODO: Load actual stats from repository
        _stats.value = DashboardStats(
            totalContainers = 12,
            activeDeployments = 8,
            serverCount = 3
        )
    }
}

data class DashboardStats(
    val totalContainers: Int = 0,
    val activeDeployments: Int = 0,
    val serverCount: Int = 0
)
