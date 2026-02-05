package ca.glong.komodo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import ca.glong.komodo.shared.infra.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun NavigationTestScreen(
    navigator: Navigator = koinInject()
) {
    Column {
        Button(onClick = { navigator.goTo(AlertsKey) }) {
            Text("Go to Alerts")
        }
        Button(onClick = { navigator.goTo(LoginKey) }) {
            Text("Go to Login")
        }
        Button(onClick = { navigator.goTo(DashboardKey) }) {
            Text("Go to Dashboard")
        }
        Button(onClick = { navigator.goTo(ResourceListKey) }) {
            Text("Go to Resources")
        }
    }
}
