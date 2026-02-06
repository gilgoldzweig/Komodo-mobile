package ca.glong.komodo.feature.passkeytest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.glong.komodo.core.auth.passkeys.AuthProvider
import ca.glong.komodo.feature.passkeytest.data.PasskeyTestRepository
import ca.glong.komodo.feature.passkeytest.data.dto.AssertionResponseDto
import ca.glong.komodo.feature.passkeytest.data.dto.AttestationResponseDto
import ca.glong.komodo.feature.passkeytest.data.dto.LoginFinishRequest
import ca.glong.komodo.feature.passkeytest.data.dto.RegisterFinishRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.annotation.KoinViewModel
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@KoinViewModel
class PasskeyTestViewModel(
    private val repository: PasskeyTestRepository,
    private val authProvider: AuthProvider
) : ViewModel() {
    
    private val _state = MutableStateFlow(PasskeyTestState())
    val state = _state.asStateFlow()
    
    fun updateRegEmail(email: String) {
        _state.update { it.copy(regEmail = email) }
    }
    
    fun updateRegUsername(username: String) {
        _state.update { it.copy(regUsername = username) }
    }
    
    fun updateLoginUsername(username: String) {
        _state.update { it.copy(loginUsername = username) }
    }
    
    fun registerPasskey() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            log("Requesting registration options...")
            
            repository.startRegistration(
                state.value.regUsername,
                state.value.regEmail
            ).onSuccess { data ->
                log("Opening hardware prompt...")
                
                val challenge = base64UrlDecode(data.options.publicKey.challenge)
                val userId = data.options.publicKey.user.id
                val rpId = data.options.publicKey.rp.id
                
                authProvider.createCredential(
                    challenge = challenge,
                    rpId = rpId,
                    userId = userId
                ).onSuccess { attestation ->
                    log("Sending credential to finish...")
                    
                    val finishRequest = RegisterFinishRequest(
                        id = attestation.id,
                        rawId = base64UrlEncode(attestation.rawId),
                        response = AttestationResponseDto(
                            clientDataJSON = base64UrlEncode(attestation.response.clientDataJSON),
                            attestationObject = base64UrlEncode(attestation.response.attestationObject)
                        )
                    )
                    
                    repository.finishRegistration(data.handshakeId, finishRequest)
                        .onSuccess { log("Registration Successful!") }
                        .onFailure { log("Error: ${it.message}", isError = true) }
                        
                }.onFailure { log("Error: ${it.message}", isError = true) }
                
            }.onFailure { log("Error: ${it.message}", isError = true) }
            
            _state.update { it.copy(isLoading = false) }
        }
    }
    
    fun loginPasskey() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            log("Requesting login challenge...")
            
            repository.startLogin(state.value.loginUsername)
                .onSuccess { data ->
                    log("Opening hardware prompt...")
                    
                    val challenge = base64UrlDecode(data.publicKey.challenge)
                    val rpId = data.publicKey.rpId ?: ""
                    
                    authProvider.getAssertion(challenge, rpId)
                        .onSuccess { assertion ->
                            log("Verifying assertion...")
                            
                            val finishRequest = LoginFinishRequest(
                                handshakeId = data.handshakeId,
                                id = assertion.id,
                                rawId = base64UrlEncode(assertion.rawId),
                                response = AssertionResponseDto(
                                    authenticatorData = base64UrlEncode(assertion.response.authenticatorData),
                                    clientDataJSON = base64UrlEncode(assertion.response.clientDataJSON),
                                    signature = base64UrlEncode(assertion.response.signature),
                                    userHandle = assertion.response.userHandle?.let { base64UrlEncode(it) }
                                )
                            )
                            
                            repository.finishLogin(finishRequest)
                                .onSuccess { token ->
                                    log("Login Successful! Token received.")
                                    _state.update { it.copy(accessToken = token) }
                                }
                                .onFailure { log("Error: ${it.message}", isError = true) }
                        }
                        .onFailure { log("Error: ${it.message}", isError = true) }
                }
                .onFailure { log("Error: ${it.message}", isError = true) }
            
            _state.update { it.copy(isLoading = false) }
        }
    }
    
    private fun log(message: String, isError: Boolean = false) {
        val timestamp = Clock.System.now().toString().takeLast(12)
        val entry = LogEntry(timestamp, message, isError)
        _state.update { it.copy(logs = it.logs + entry) }
    }
    
    @OptIn(ExperimentalEncodingApi::class)
    private fun base64UrlEncode(bytes: ByteArray): String = 
        Base64.UrlSafe.encode(bytes).trimEnd('=')
    
    @OptIn(ExperimentalEncodingApi::class)
    private fun base64UrlDecode(str: String): ByteArray {
        val padded = str + "=".repeat((4 - str.length % 4) % 4)
        return Base64.UrlSafe.decode(padded)
    }
}

data class PasskeyTestState(
    val regEmail: String = "",
    val regUsername: String = "",
    val loginUsername: String = "",
    val isLoading: Boolean = false,
    val logs: List<LogEntry> = listOf(LogEntry("", "> Ready for testing...", false)),
    val accessToken: String? = null
)

data class LogEntry(
    val timestamp: String,
    val message: String,
    val isError: Boolean
)
