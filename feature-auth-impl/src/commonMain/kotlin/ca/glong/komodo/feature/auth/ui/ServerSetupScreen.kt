package ca.glong.komodo.feature.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ServerSetupScreen(
    viewModel: ServerSetupViewModel,
    onSetupComplete: () -> Unit
) {
    val serverUrl by viewModel.serverUrl.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Server Setup",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = serverUrl,
            onValueChange = { viewModel.updateServerUrl(it) },
            label = { Text("Server URL") },
            placeholder = { Text("https://your-komodo-server.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.saveServerConfig()
                onSetupComplete()
            },
            enabled = serverUrl.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
