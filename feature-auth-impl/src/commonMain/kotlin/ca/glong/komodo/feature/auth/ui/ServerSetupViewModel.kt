package ca.glong.komodo.feature.auth.ui

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Inject
class ServerSetupViewModel : ViewModel() {
    private val _serverUrl = MutableStateFlow("")
    val serverUrl = _serverUrl.asStateFlow()

    fun updateServerUrl(url: String) {
        _serverUrl.value = url
    }

    fun saveServerConfig() {
        // TODO: Save server configuration
    }
}
