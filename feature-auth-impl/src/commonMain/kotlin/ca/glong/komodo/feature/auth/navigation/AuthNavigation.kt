package ca.glong.komodo.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.ui.Screen
import ca.glong.komodo.feature.auth.ui.LoginScreen as LoginScreenUI
import ca.glong.komodo.feature.auth.ui.LoginViewModel
import ca.glong.komodo.feature.auth.ui.ServerSetupScreen as ServerSetupScreenUI
import ca.glong.komodo.feature.auth.ui.ServerSetupViewModel

class ServerSetupScreen(
    private val onSetupComplete: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel { ServerSetupViewModel() }
        ServerSetupScreenUI(
            viewModel = viewModel,
            onSetupComplete = onSetupComplete
        )
    }
}

class LoginScreen(
    private val onLoginSuccess: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel { LoginViewModel() }
        LoginScreenUI(
            viewModel = viewModel,
            onLoginSuccess = onLoginSuccess
        )
    }
}
