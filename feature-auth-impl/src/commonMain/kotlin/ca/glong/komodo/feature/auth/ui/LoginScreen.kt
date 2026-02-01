package ca.glong.komodo.feature.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import org.koin.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val navStack = remember { mutableStateListOf<Any>(LoginKey) }


    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            navStack.add(DashboardKey)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(username, password) },
            enabled = state !is LoginState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state is LoginState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Login")
            }
        }

        if (state is LoginState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (state as LoginState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
