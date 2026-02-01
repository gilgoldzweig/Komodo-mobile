package ca.glong.komodo.ui.screens.server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.glong.komodo.data.dto.GetLoginOptionsResponse
import ca.glong.komodo.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import ca.glong.komodo.shared.infra.state.DefaultStateFlow
import ca.glong.komodo.shared.infra.state.MutableDefaultStateFlow
import org.koin.core.annotation.KoinViewModel

abstract class IServerSetupViewMoel : ViewModel() {
    abstract val uiState: DefaultStateFlow<GetLoginOptionsResponse>
    abstract fun connect(url: String)
}

@KoinViewModel
class ServerSetupViewModel(
    private val authRepository: AuthRepository
) : IServerSetupViewMoel() {

    override val uiState = MutableDefaultStateFlow<GetLoginOptionsResponse>()

    override fun connect(url: String) {
        uiState.emitLoading()

        viewModelScope.launch {
            val result = authRepository.getLoginOptions(url)
            result.onSuccess { response ->
//                settingsRepository.setServerUrl(url)
                uiState.emitSuccess(response)
            }.onFailure { error ->
                uiState.emitError(error)
            }
        }
    }
}
