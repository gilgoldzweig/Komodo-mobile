package ca.glong.komodo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation3.ui.NavHost
import androidx.navigation3.ui.rememberNavStack
import ca.glong.komodo.feature.alerts.navigation.AlertsScreen
import ca.glong.komodo.feature.auth.navigation.LoginScreen
import ca.glong.komodo.feature.auth.navigation.ServerSetupScreen
import ca.glong.komodo.feature.dashboard.navigation.DashboardScreen
import ca.glong.komodo.feature.resources.navigation.ResourceDetailScreen
import ca.glong.komodo.feature.resources.navigation.ResourceListScreen
import ca.glong.komodo.navigation.LauncherScreen

@Composable
fun App() {
    MaterialTheme {
        val navStack = rememberNavStack(initialScreen = LauncherScreen())

        NavHost(navStack) { screen ->
            screen.Content()
        }
    }
}
