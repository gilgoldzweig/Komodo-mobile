package ca.glong.komodo.core.auth.passkeys

import ca.glong.komodo.core.auth.error.AuthError
import org.koin.core.annotation.Single

@Single(binds = [AuthProvider::class])
class AndroidAuthProvider : AuthProvider {
    
    override suspend fun createCredential(
        challenge: ByteArray,
        rpId: String,
        userId: String
    ): Result<AttestationResponse> {
        return Result.failure(
            AuthError.PasskeyError.BiometricUnavailable(
                "Android Credential Manager not yet implemented. " +
                "This is a stub for testing the UI flow."
            )
        )
    }
    
    override suspend fun getAssertion(
        challenge: ByteArray,
        rpId: String
    ): Result<AssertionResponse> {
        return Result.failure(
            AuthError.PasskeyError.BiometricUnavailable(
                "Android Credential Manager not yet implemented. " +
                "This is a stub for testing the UI flow."
            )
        )
    }
}
