package ca.glong.komodo.feature.resources.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.resources.api.ResourceDetailKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import ca.glong.komodo.feature.resources.api.ResourceType
import org.koin.compose.koinViewModel

@Composable
fun ResourceListScreen(
    viewModel: ResourceListViewModel = koinViewModel()
) {
    val resources by viewModel.resources.collectAsState()
    val navStack = remember { mutableStateListOf<Any>(ResourceListKey) }


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
                    text = "Resources",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(resources) { resource ->
                ResourceCard(
                    resource = resource,
                    onClick = {
                        navStack.add(ResourceDetailKey(resource.id, resource.type))
                    }
                )
            }
        }
    }
}

@Composable
private fun ResourceCard(
    resource: ResourceItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = resource.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = resource.type.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
