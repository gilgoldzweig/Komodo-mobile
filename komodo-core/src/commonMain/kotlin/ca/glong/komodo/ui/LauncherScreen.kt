package ca.glong.komodo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.auth.api.ServerSetupKey
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import ca.glong.komodo.navigation.LauncherKey
import ca.glong.komodo.shared.infra.navigation.Navigator
import ca.glong.komodo.shared.infra.navigation.NavigatorMain
import org.koin.compose.koinInject

@Composable
fun LauncherScreen(navigator: Navigator = koinInject()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Komodo Mobile",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FeatureCard(
                    title = "Server Setup",
                    onClick = {
                        navigator.goTo(ServerSetupKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Login",
                    onClick = {
                        navigator.goTo(LoginKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Dashboard",
                    onClick = {
                        navigator.goTo(DashboardKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Resources",
                    onClick = {
                        navigator.goTo(ResourceListKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Alerts",
                    onClick = {
                        navigator.goTo(AlertsKey)
                    }
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@Preview
fun LauncherScreenPreview() {
    val navigator = remember {
        NavigatorMain(LauncherKey)
    }
    LauncherScreen(navigator)
}
