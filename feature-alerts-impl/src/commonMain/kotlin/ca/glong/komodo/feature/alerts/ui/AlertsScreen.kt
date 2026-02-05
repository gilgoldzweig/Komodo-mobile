package ca.glong.komodo.feature.alerts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.glong.komodo.ui.theme.KomodoTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AlertsScreen(
    viewModel: AlertsViewModel = koinViewModel()
) {
    val nav = KomodoTheme.navigator
    val alerts by viewModel.alerts.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(tonalElevation = 3.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                TextButton(onClick = nav::goBack) {
                    Text("← Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Alerts", style = MaterialTheme.typography.titleLarge)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(alerts) { alert ->
                AlertCard(alert)
            }
        }
    }
}

@Composable
private fun AlertCard(alert: AlertItem) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(alert.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    alert.severity,
                    color = if (alert.severity == "Error") {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Source: ${alert.source}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
