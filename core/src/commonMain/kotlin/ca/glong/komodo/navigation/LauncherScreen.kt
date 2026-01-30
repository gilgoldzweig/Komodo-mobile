package ca.glong.komodo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.ui.LocalNavStack
import androidx.navigation3.ui.Screen
import ca.glong.komodo.feature.alerts.navigation.AlertsScreen
import ca.glong.komodo.feature.auth.navigation.LoginScreen
import ca.glong.komodo.feature.auth.navigation.ServerSetupScreen
import ca.glong.komodo.feature.dashboard.navigation.DashboardScreen
import ca.glong.komodo.feature.resources.navigation.ResourceDetailScreen
import ca.glong.komodo.feature.resources.navigation.ResourceListScreen

class LauncherScreen : Screen {
    @Composable
    override fun Content() {
        val navStack = LocalNavStack.current

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
                            navStack.push(ServerSetupScreen(
                                onSetupComplete = { navStack.push(LoginScreen(onLoginSuccess = {})) }
                            ))
                        }
                    )
                }

                item {
                    FeatureCard(
                        title = "Login",
                        onClick = {
                            navStack.push(LoginScreen(
                                onLoginSuccess = { navStack.push(DashboardScreen({}, {})) }
                            ))
                        }
                    )
                }

                item {
                    FeatureCard(
                        title = "Dashboard",
                        onClick = {
                            navStack.push(DashboardScreen(
                                onNavigateToResources = { navStack.push(ResourceListScreen({ _, _ -> }, { navStack.pop() })) },
                                onNavigateToAlerts = { navStack.push(AlertsScreen { navStack.pop() }) }
                            ))
                        }
                    )
                }

                item {
                    FeatureCard(
                        title = "Resources",
                        onClick = {
                            navStack.push(ResourceListScreen(
                                onResourceClick = { id, type ->
                                    navStack.push(ResourceDetailScreen(id, type) { navStack.pop() })
                                },
                                onBackClick = { navStack.pop() }
                            ))
                        }
                    )
                }

                item {
                    FeatureCard(
                        title = "Alerts",
                        onClick = {
                            navStack.push(AlertsScreen(
                                onBackClick = { navStack.pop() }
                            ))
                        }
                    )
                }
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
