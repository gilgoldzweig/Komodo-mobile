package ca.glong.komodo.core.auth.passkeys

import ca.glong.komodo.core.auth.error.AuthError

interface AuthProvider {
    suspend fun createCredential(
        challenge: ByteArray,
        rpId: String,
        userId: String
    ): Result<AttestationResponse>

    suspend fun getAssertion(
        challenge: ByteArray,
        rpId: String
    ): Result<AssertionResponse>
}
