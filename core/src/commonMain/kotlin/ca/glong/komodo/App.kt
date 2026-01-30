package ca.glong.komodo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import ca.glong.komodo.ui.navigation.Route
import ca.glong.komodo.ui.screens.dashboard.DashboardScreen
import ca.glong.komodo.ui.screens.login.LoginScreen
import ca.glong.komodo.ui.screens.server.ServerSetupScreen
import org.koin.compose.KoinContext
import androidx.compose.material3.MaterialTheme

// Since Nav3 API is alpha and not fully known without docs/IDE,
// using a state-based approach as a placeholder for navigation structure.
// If NavHost is available in androidx.navigation3, it can be swapped here.

@Composable
fun App() {
    val backStack = remember { mutableStateListOf<Route>(Route.ServerSetup) }
        MaterialTheme {
             val backStack = remember { mutableStateListOf<Route>(Route.ServerSetup) }

             val navigate: (Route) -> Unit = { route ->
                 backStack.add(route)
             }

             val navigateBack: () -> Unit = {
                 if (backStack.size > 1) {
                     backStack.removeLast()
                 }
             }

             val currentRoute = backStack.lastOrNull() ?: Route.ServerSetup

             when(currentRoute) {
                 is Route.ServerSetup -> ServerSetupScreen(
                     onLoginSuccess = { navigate(Route.Login) }
                 )
                 is Route.Login -> LoginScreen(
                     onLoginSuccess = { navigate(Route.Dashboard) }
                 )
                 is Route.Dashboard -> DashboardScreen()
             }
        }
}
