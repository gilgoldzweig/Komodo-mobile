package ca.glong.komodo.feature.passkeytest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable`
fun PasskeyTestScreen(
    viewModel: PasskeyTestViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(state.logs.size) {
        if (state.logs.isNotEmpty()) {
            listState.animateScrollToItem(state.logs.size - 1)
        }
    }

    PasskeyTestContent(
        state = state,
        listState = listState,
        onRegEmailChange = viewModel::updateRegEmail,
        onRegUsernameChange = viewModel::updateRegUsername,
        onRegisterClick = viewModel::registerPasskey,
        onLoginUsernameChange = viewModel::updateLoginUsername,
        onLoginClick = viewModel::loginPasskey
    )
}

@Composable
private fun PasskeyTestContent(
    state: PasskeyTestState,
    listState: LazyListState,
    onRegEmailChange: (String) -> Unit,
    onRegUsernameChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginUsernameChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenHeader()

        RegistrationCard(
            email = state.regEmail,
            username = state.regUsername,
            isLoading = state.isLoading,
            onEmailChange = onRegEmailChange,
            onUsernameChange = onRegUsernameChange,
            onRegisterClick = onRegisterClick
        )

        LoginCard(
            username = state.loginUsername,
            isLoading = state.isLoading,
            onUsernameChange = onLoginUsernameChange,
            onLoginClick = onLoginClick
        )

        LogViewer(
            logs = state.logs,
            listState = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
private fun ScreenHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Passkey Test Bench",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Testing GGG Auth Service API",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RegistrationCard(
    email: String,
    username: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "1. Register Passkey",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = onRegisterClick,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Passkey")
            }
        }
    }
}

@Composable
private fun LoginCard(
    username: String,
    isLoading: Boolean,
    onUsernameChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "2. Login with Passkey",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = onLoginClick,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF16A34A)
                )
            ) {
                Text("Authenticate")
            }
        }
    }
}

@Composable
private fun LogViewer(
    logs: List<LogEntry>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF0F172A)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(logs) { entry ->
                LogEntryText(entry = entry)
            }
        }
    }
}

@Composable
private fun LogEntryText(entry: LogEntry) {
    Text(
        text = if (entry.timestamp.isNotEmpty())
            "[${entry.timestamp}] ${entry.message}"
        else entry.message,
        color = if (entry.isError) Color(0xFFF87171) else Color(0xFF4ADE80),
        fontFamily = FontFamily.Monospace,
        style = MaterialTheme.typography.bodySmall
    )
}

// ============================================================================
// Previews
// ============================================================================

@Preview
@Composable
private fun PreviewScreenHeader() {
    MaterialTheme {
        ScreenHeader()
    }
}

@Preview
@Composable
private fun PreviewRegistrationCard() {
    MaterialTheme {
        RegistrationCard(
            email = "user@example.com",
            username = "testuser",
            isLoading = false,
            onEmailChange = {},
            onUsernameChange = {},
            onRegisterClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewRegistrationCardLoading() {
    MaterialTheme {
        RegistrationCard(
            email = "user@example.com",
            username = "testuser",
            isLoading = true,
            onEmailChange = {},
            onUsernameChange = {},
            onRegisterClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewLoginCard() {
    MaterialTheme {
        LoginCard(
            username = "testuser",
            isLoading = false,
            onUsernameChange = {},
            onLoginClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewLoginCardLoading() {
    MaterialTheme {
        LoginCard(
            username = "testuser",
            isLoading = true,
            onUsernameChange = {},
            onLoginClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewLogViewer() {
    MaterialTheme {
        LogViewer(
            logs = listOf(
                LogEntry(timestamp = "14:23:01", message = "Starting passkey registration...", isError = false),
                LogEntry(timestamp = "14:23:02", message = "Challenge received", isError = false),
                LogEntry(timestamp = "14:23:05", message = "Registration successful!", isError = false),
                LogEntry(timestamp = "14:24:10", message = "Connection timeout", isError = true)
            ),
            listState = rememberLazyListState(),
            modifier = Modifier.size(400.dp, 300.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewLogEntry() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .width(400.dp)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            LogEntryText(
                entry = LogEntry(
                    timestamp = "14:23:01",
                    message = "Registration successful",
                    isError = false
                )
            )
            LogEntryText(
                entry = LogEntry(
                    timestamp = "14:23:02",
                    message = "Connection failed",
                    isError = true
                )
            )
        }
    }
}
