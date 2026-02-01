package ca.glong.komodo.feature.auth.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()

    fun login(username: String, password: String) {
        _state.value = LoginState.Loading
        // TODO: Implement actual login logic
        _state.value = LoginState.Success
    }
}

sealed interface LoginState {
    data object Idle : LoginState
    data object Loading : LoginState
    data object Success : LoginState
    data class Error(val message: String) : LoginState
}
