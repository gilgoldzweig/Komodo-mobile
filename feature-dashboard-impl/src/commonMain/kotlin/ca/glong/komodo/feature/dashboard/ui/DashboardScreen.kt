package ca.glong.komodo.feature.dashboard.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val navStack = remember { mutableStateListOf<Any>(DashboardKey) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                title = "Total Containers",
                value = stats.totalContainers.toString(),
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "Active Deployments",
                value = stats.activeDeployments.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        StatCard(
            title = "Servers",
            value = stats.serverCount.toString(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navStack.add(ResourceListKey) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Resources")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navStack.add(AlertsKey) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Alerts")
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
