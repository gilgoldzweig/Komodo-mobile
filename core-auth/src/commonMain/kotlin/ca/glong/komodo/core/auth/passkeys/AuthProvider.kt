package ca.glong.komodo.core.auth.passkeys

import ca.glong.komodo.core.auth.error.AuthError

/**
 * Platform abstraction for WebAuthn/Passkey operations.
 * Android: Uses androidx.credentials.CredentialManager
 * iOS: Uses AuthenticationServices (ASAuthorization)
 */
interface AuthProvider {
    /**
     * Register a new passkey credential.
     * @param challenge Server-generated challenge (random bytes)
     * @param rpId Relying Party ID (domain, e.g., "example.com")
     * @param userId Unique user identifier
     * @param userName Human-readable username for credential picker
     * @return Attestation result containing credentialId and signed attestation
     */
    suspend fun createCredential(
        challenge: ByteArray,
        rpId: String,
        userId: String,
        userName: String
    ): Result<AttestationResult>

    /**
     * Authenticate with an existing passkey.
     * @param challenge Server-generated challenge (random bytes)
     * @param rpId Relying Party ID (domain, e.g., "example.com")
     * @param allowedCredentials Optional list of allowed credential IDs (empty = show all)
     * @return Assertion result containing signature over challenge and authenticator data
     */
    suspend fun getAssertion(
        challenge: ByteArray,
        rpId: String,
        allowedCredentials: List<ByteArray> = emptyList()
    ): Result<AssertionResult>
}

/**
 * WebAuthn attestation response from credential creation.
 */
data class AttestationResult(
    val credentialId: ByteArray,
    val attestationObject: ByteArray,
    val clientDataJson: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as AttestationResult
        if (!credentialId.contentEquals(other.credentialId)) return false
        if (!attestationObject.contentEquals(other.attestationObject)) return false
        if (!clientDataJson.contentEquals(other.clientDataJson)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = credentialId.contentHashCode()
        result = 31 * result + attestationObject.contentHashCode()
        result = 31 * result + clientDataJson.contentHashCode()
        return result
    }
}

/**
 * WebAuthn assertion response from authentication.
 */
data class AssertionResult(
    val credentialId: ByteArray,
    val authenticatorData: ByteArray,
    val signature: ByteArray,
    val clientDataJson: ByteArray,
    val userHandle: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as AssertionResult
        if (!credentialId.contentEquals(other.credentialId)) return false
        if (!authenticatorData.contentEquals(other.authenticatorData)) return false
        if (!signature.contentEquals(other.signature)) return false
        if (!clientDataJson.contentEquals(other.clientDataJson)) return false
        if (userHandle != null) {
            if (other.userHandle == null) return false
            if (!userHandle.contentEquals(other.userHandle)) return false
        } else if (other.userHandle != null) return false
        return true
    }

    override fun hashCode(): Int {
        var result = credentialId.contentHashCode()
        result = 31 * result + authenticatorData.contentHashCode()
        result = 31 * result + signature.contentHashCode()
        result = 31 * result + clientDataJson.contentHashCode()
        result = 31 * result + (userHandle?.contentHashCode() ?: 0)
        return result
    }
}
