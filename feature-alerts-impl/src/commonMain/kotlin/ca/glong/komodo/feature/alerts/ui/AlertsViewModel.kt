package ca.glong.komodo.feature.alerts.ui

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
class AlertsViewModel : ViewModel() {
    // ...existing code...
}
    private val _alerts = MutableStateFlow<List<AlertItem>>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        loadAlerts()
    }

    private fun loadAlerts() {
        _alerts.value = listOf(
            AlertItem("alert-1", "High CPU Usage", "Server 1", "Warning"),
            AlertItem("alert-2", "Deployment Failed", "Deploy 3", "Error"),
            AlertItem("alert-3", "Memory Threshold", "Server 2", "Warning"),
        )
    }
}

data class AlertItem(
    val id: String,
    val title: String,
    val source: String,
    val severity: String
)
