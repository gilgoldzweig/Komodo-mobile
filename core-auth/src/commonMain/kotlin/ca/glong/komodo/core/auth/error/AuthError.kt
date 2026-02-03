package ca.glong.komodo.core.auth.error

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class AuthError : RuntimeException() {
    abstract override val message: String
    @Transient override val cause: Throwable? = null

    @Serializable
    data class KeyStoreError(
        override val message: String, 
        val underlyingError: String? = null
    ) : AuthError()

    @Serializable
    data class BioAuthError(
        override val message: String,
        val errorCode: Int? = null
    ) : AuthError()

    @Serializable
    data class InvalidKeyFormat(
        override val message: String,
        val format: String
    ) : AuthError()

    @Serializable
    data class StorageError(
        override val message: String
    ) : AuthError()
    
    @Serializable
    data class UnknownError(
        override val message: String
    ) : AuthError()
}
