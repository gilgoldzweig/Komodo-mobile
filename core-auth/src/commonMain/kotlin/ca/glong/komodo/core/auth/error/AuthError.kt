package ca.glong.komodo.core.auth.error

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class AuthError : RuntimeException() {
    abstract override val message: String
    @Transient override val cause: Throwable? = null

    /**
     * Key management errors: keystore unavailable, invalid format, generation failures
     */
    @Serializable
    sealed class KeyError(override val message: String) : AuthError() {
        @Serializable
        data class KeystoreUnavailable(val details: String? = null) : KeyError(
            message = "Keystore unavailable${details?.let { ": $it" } ?: ""}"
        )

        @Serializable
        data class InvalidFormat(val format: String) : KeyError(
            message = "Invalid key format: $format"
        )

        @Serializable
        data class GenerationFailed(val reason: String) : KeyError(
            message = "Key generation failed: $reason"
        )

        @Serializable
        data class LoadFailed(val keyId: String) : KeyError(
            message = "Failed to load key: $keyId"
        )

        @Serializable
        data class StorageFailed(val reason: String) : KeyError(
            message = "Key storage failed: $reason"
        )

        @Serializable
        data class OperationFailed(val reason: String) : KeyError(
            message = "Key operation failed: $reason"
        )
    }

    /**
     * Secure storage errors: unavailable, corruption, write failures
     */
    @Serializable
    sealed class StorageError(override val message: String) : AuthError() {
        @Serializable
        data class Unavailable(val reason: String = "Storage unavailable") : StorageError(
            message = reason
        )

        @Serializable
        data class CorruptionDetected(val details: String? = null) : StorageError(
            message = "Storage corruption detected${details?.let { ": $it" } ?: ""}"
        )

        @Serializable
        data class WriteFailed(val key: String) : StorageError(
            message = "Failed to write to storage: $key"
        )

        @Serializable
        data class ReadFailed(val key: String) : StorageError(
            message = "Failed to read from storage: $key"
        )
    }

    /**
     * Token errors: expiration, validation, malformed
     */
    @Serializable
    sealed class TokenError(override val message: String) : AuthError() {
        @Serializable
        data class Expired(val expiresAt: Long? = null) : TokenError(
            message = "Token expired${expiresAt?.let { " at $it" } ?: ""}"
        )

        @Serializable
        data class Invalid(val reason: String) : TokenError(
            message = "Invalid token: $reason"
        )

        @Serializable
        data class Malformed(val details: String? = null) : TokenError(
            message = "Malformed token${details?.let { ": $it" } ?: ""}"
        )

        @Serializable
        data class RefreshFailed(val reason: String) : TokenError(
            message = "Token refresh failed: $reason"
        )
    }

    /**
     * Envelope encryption/decryption errors
     */
    @Serializable
    sealed class EnvelopeError(override val message: String) : AuthError() {
        @Serializable
        data class EncryptionFailed(val reason: String) : EnvelopeError(
            message = "Encryption failed: $reason"
        )

        @Serializable
        data class DecryptionFailed(val reason: String) : EnvelopeError(
            message = "Decryption failed: $reason"
        )

        @Serializable
        data class InvalidEnvelope(val details: String? = null) : EnvelopeError(
            message = "Invalid envelope${details?.let { ": $it" } ?: ""}"
        )
    }

    /**
     * Passkey/biometric authentication errors
     */
    @Serializable
    sealed class PasskeyError(override val message: String) : AuthError() {
        @Serializable
        data class BiometricUnavailable(val reason: String = "Biometric unavailable") : PasskeyError(
            message = reason
        )

        @Serializable
        data class AuthenticationFailed(val errorCode: Int? = null) : PasskeyError(
            message = "Biometric authentication failed${errorCode?.let { " (code: $it)" } ?: ""}"
        )

        @Serializable
        data class UserCancelled(val dummy: Boolean = true) : PasskeyError(
            message = "User cancelled authentication"
        )

        @Serializable
        data class NotEnrolled(val dummy: Boolean = true) : PasskeyError(
            message = "No biometric enrolled"
        )

        @Serializable
        data class NoCredentials(val dummy: Boolean = true) : PasskeyError(
            message = "No passkey credentials available"
        )

        @Serializable
        data class ProviderUnavailable(val reason: String = "Credential provider unavailable") : PasskeyError(
            message = reason
        )

        @Serializable
        data class OperationFailed(val reason: String) : PasskeyError(
            message = "Passkey operation failed: $reason"
        )
    }

    /**
     * Unknown/uncategorized errors
     */
    @Serializable
    data class Unknown(val details: String? = null) : AuthError() {
        override val message: String = "Unknown error${details?.let { ": $it" } ?: ""}"
    }

    companion object {
        /**
         * Factory method for common keystore error scenarios
         */
        fun keystoreError(details: String? = null): KeyError.KeystoreUnavailable =
            KeyError.KeystoreUnavailable(details)

        /**
         * Factory method for storage unavailability
         */
        fun storageUnavailable(reason: String = "Storage unavailable"): StorageError.Unavailable =
            StorageError.Unavailable(reason)

        /**
         * Factory method for token expiration
         */
        fun tokenExpired(expiresAt: Long? = null): TokenError.Expired =
            TokenError.Expired(expiresAt)

        /**
         * Factory method for biometric failures
         */
        fun biometricFailed(errorCode: Int? = null): PasskeyError.AuthenticationFailed =
            PasskeyError.AuthenticationFailed(errorCode)
    }
}
