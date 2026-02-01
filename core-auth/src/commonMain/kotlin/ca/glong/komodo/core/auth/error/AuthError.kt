package ca.glong.komodo.core.auth.error

/**
 * Sealed hierarchy for authentication and cryptography errors.
 * Used with kotlin.Result for type-safe error handling.
 */
sealed class AuthError(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    /**
     * Errors related to KeyStore/Keychain operations
     */
    sealed class KeyStoreError(message: String, cause: Throwable? = null) : AuthError(message, cause) {
        data class KeyGenerationFailed(
            val algorithm: String,
            override val cause: Throwable? = null
        ) : KeyStoreError("Failed to generate key with algorithm: $algorithm", cause)

        data class KeyNotFound(
            val alias: String
        ) : KeyStoreError("Key with alias '$alias' not found in keystore")

        data class KeyRetrievalFailed(
            val alias: String,
            override val cause: Throwable? = null
        ) : KeyStoreError("Failed to retrieve key with alias: $alias", cause)

        data class KeyDeletionFailed(
            val alias: String,
            override val cause: Throwable? = null
        ) : KeyStoreError("Failed to delete key with alias: $alias", cause)

        data class KeyStoreUnavailable(
            override val cause: Throwable? = null
        ) : KeyStoreError("KeyStore is unavailable or could not be initialized", cause)

        data class KeyImportFailed(
            val reason: String,
            override val cause: Throwable? = null
        ) : KeyStoreError("Failed to import key: $reason", cause)

        data class SigningFailed(
            val alias: String,
            override val cause: Throwable? = null
        ) : KeyStoreError("Failed to sign data with key: $alias", cause)
    }

    /**
     * Errors related to biometric authentication
     */
    sealed class BioAuthError(message: String, cause: Throwable? = null) : AuthError(message, cause) {
        data object NotAvailable : BioAuthError("Biometric authentication is not available on this device")
        data object NotEnrolled : BioAuthError("No biometric credentials are enrolled")
        data object HardwareUnavailable : BioAuthError("Biometric hardware is currently unavailable")
        data object LockoutTemporary : BioAuthError("Too many failed attempts. Biometric locked temporarily")
        data object LockoutPermanent : BioAuthError("Too many failed attempts. Biometric locked permanently")
        data class AuthenticationFailed(
            override val cause: Throwable? = null
        ) : BioAuthError("Biometric authentication failed", cause)
        data object UserCancelled : BioAuthError("Biometric authentication was cancelled by user")
    }

    /**
     * Errors related to key format and parsing
     */
    sealed class InvalidKeyFormat(message: String, cause: Throwable? = null) : AuthError(message, cause) {
        data class InvalidPemFormat(
            val details: String,
            override val cause: Throwable? = null
        ) : InvalidKeyFormat("Invalid PEM format: $details", cause)

        data class UnsupportedKeyType(
            val keyType: String
        ) : InvalidKeyFormat("Unsupported key type: $keyType")

        data class InvalidKeyLength(
            val expected: Int,
            val actual: Int
        ) : InvalidKeyFormat("Invalid key length. Expected: $expected, Actual: $actual")

        data class CorruptedKeyData(
            override val cause: Throwable? = null
        ) : InvalidKeyFormat("Key data is corrupted or invalid", cause)
    }

    /**
     * Errors related to secure storage operations
     */
    sealed class StorageError(message: String, cause: Throwable? = null) : AuthError(message, cause) {
        data class SaveFailed(
            val key: String,
            override val cause: Throwable? = null
        ) : StorageError("Failed to save data for key: $key", cause)

        data class ReadFailed(
            val key: String,
            override val cause: Throwable? = null
        ) : StorageError("Failed to read data for key: $key", cause)

        data class DeleteFailed(
            val key: String,
            override val cause: Throwable? = null
        ) : StorageError("Failed to delete data for key: $key", cause)

        data class DataNotFound(
            val key: String
        ) : StorageError("Data not found for key: $key")
    }

    /**
     * Errors related to token management
     */
    sealed class TokenError(message: String, cause: Throwable? = null) : AuthError(message, cause) {
        data class TokenExpired(
            val tokenId: String
        ) : TokenError("Token with id '$tokenId' has expired")

        data class TokenNotFound(
            val tokenId: String
        ) : TokenError("Token with id '$tokenId' not found")

        data class TokenStorageFailed(
            val tokenId: String,
            override val cause: Throwable? = null
        ) : TokenError("Failed to store token with id: $tokenId", cause)
    }

    /**
     * Errors related to WebAuthn/Passkey operations
     */
    sealed class PasskeyError(message: String, cause: Throwable? = null) : AuthError(message, cause) {
        data object NotSupported : PasskeyError("Passkeys are not supported on this device")

        data class RegistrationFailed(
            val reason: String,
            override val cause: Throwable? = null
        ) : PasskeyError("Passkey registration failed: $reason", cause)

        data class AuthenticationFailed(
            val reason: String,
            override val cause: Throwable? = null
        ) : PasskeyError("Passkey authentication failed: $reason", cause)

        data class InvalidChallenge(
            override val cause: Throwable? = null
        ) : PasskeyError("Invalid challenge received from server", cause)

        data object UserCancelled : PasskeyError("Passkey operation was cancelled by user")
    }
}
