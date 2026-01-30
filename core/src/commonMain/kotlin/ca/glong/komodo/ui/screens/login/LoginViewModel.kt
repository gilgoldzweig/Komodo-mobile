package ca.glong.komodo.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.glong.komodo.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import ca.glong.komodo.state.MutableUnitStateFlow
import ca.glong.komodo.state.UnitStateFlow

abstract class ILoginViewModel : ViewModel() {
    abstract val uiState: UnitStateFlow
    abstract fun login(username: String, pass: String)
}

@KoinViewModel([ILoginViewModel::class])
class LoginViewModel(
    private val authRepository: AuthRepository
) : ILoginViewModel() {

    override val uiState = MutableUnitStateFlow()

    override fun login(username: String, pass: String) {
        uiState.emitLoading()
        viewModelScope.launch {
            val result = authRepository.loginLocal(username, pass)
            result.onSuccess {
                uiState.emitSuccess(Unit)
            }.onFailure {
                uiState.emitError(it)
            }
        }
    }
}
