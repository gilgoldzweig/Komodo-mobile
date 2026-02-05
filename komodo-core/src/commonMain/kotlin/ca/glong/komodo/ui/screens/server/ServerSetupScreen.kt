package ca.glong.komodo.ui.screens.server

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.glong.komodo.shared.infra.state.State
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ServerSetupScreen(
    viewModel: ServerSetupViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle(State.Created())

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var url by remember { mutableStateOf("https://") }

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Server URL") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { viewModel.connect(url) },
            enabled = state !is State.Loading
        ) {
            Text("Connect")
        }

        when (val s = state) {
            is State.Loading -> CircularProgressIndicator()
            is State.Error -> Text("Error: ${s.error}")
            is State.Success -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            else -> {}
        }
    }
}
