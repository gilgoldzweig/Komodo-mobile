package ca.glong.komodo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.auth.api.ServerSetupKey
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.resources.api.ResourceListKey

@Composable
fun LauncherScreen() {
    val navStack = remember { mutableStateListOf<Any>(LauncherKey) }


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
                        navStack.add(ServerSetupKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Login",
                    onClick = {
                        navStack.add(LoginKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Dashboard",
                    onClick = {
                        navStack.add(DashboardKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Resources",
                    onClick = {
                        navStack.add(ResourceListKey)
                    }
                )
            }

            item {
                FeatureCard(
                    title = "Alerts",
                    onClick = {
                        navStack.add(AlertsKey)
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
