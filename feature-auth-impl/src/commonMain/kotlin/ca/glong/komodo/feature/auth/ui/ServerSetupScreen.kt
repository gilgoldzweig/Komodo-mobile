package ca.glong.komodo.feature.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.auth.api.ServerSetupKey
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ServerSetupScreen(
    viewModel: ServerSetupViewModel = koinViewModel()
) {
    val serverUrl by viewModel.serverUrl.collectAsState()
    val navStack = remember { mutableStateListOf<Any>(ServerSetupKey) }

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
                navStack.add(LoginKey)
            },
            enabled = serverUrl.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
