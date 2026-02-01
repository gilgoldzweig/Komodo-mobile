package ca.glong.komodo.feature.resources.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.resources.api.ResourceDetailKey
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ResourceDetailScreen(
    key: ResourceDetailKey,
    viewModel: ResourceDetailViewModel = koinViewModel { parametersOf(key.id, key.type) }
) {
    val resource by viewModel.resource.collectAsState()
    val navStack = remember { mutableStateListOf<Any>(key) }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar
        Surface(
            tonalElevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextButton(onClick = { navStack.removeLastOrNull() }) {
                    Text("← Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Resource Detail",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        resource?.let { res ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = res.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DetailRow("Type", res.type.name)
                        DetailRow("Status", res.status)
                        DetailRow("ID", res.id)
                    }
                }

                Text(
                    text = res.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
