package ca.glong.komodo.core.auth.error

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthErrorSerializationTest {

    @Test
    fun keyErrorKeystoreUnavailableSerializationRoundTrip() {
        val error = AuthError.KeyError.KeystoreUnavailable("Security framework unavailable")
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.KeyError.KeystoreUnavailable).message)
    }

    @Test
    fun keyErrorInvalidFormatSerializationRoundTrip() {
        val error = AuthError.KeyError.InvalidFormat("PKCS#8")
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.KeyError.InvalidFormat).message)
    }

    @Test
    fun storageErrorUnavailableSerializationRoundTrip() {
        val error = AuthError.StorageError.Unavailable("Disk full")
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.StorageError.Unavailable).message)
    }

    @Test
    fun tokenErrorExpiredSerializationRoundTrip() {
        val expiryTime = 1707148800000L
        val error = AuthError.TokenError.Expired(expiryTime)
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.TokenError.Expired).message)
    }

    @Test
    fun envelopeErrorEncryptionFailedSerializationRoundTrip() {
        val error = AuthError.EnvelopeError.EncryptionFailed("AES cipher failed")
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.EnvelopeError.EncryptionFailed).message)
    }

    @Test
    fun passkeyErrorBiometricUnavailableSerializationRoundTrip() {
        val error = AuthError.PasskeyError.BiometricUnavailable("Face ID not available")
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.PasskeyError.BiometricUnavailable).message)
    }

    @Test
    fun passkeyErrorAuthenticationFailedSerializationRoundTrip() {
        val error = AuthError.PasskeyError.AuthenticationFailed(errorCode = -2)
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.PasskeyError.AuthenticationFailed).message)
    }

    @Test
    fun unknownErrorSerializationRoundTrip() {
        val error = AuthError.Unknown("Something went wrong")
        val json = Json.encodeToString(AuthError.serializer(), error)
        val deserialized = Json.decodeFromString(AuthError.serializer(), json)
        
        assertEquals(error.message, (deserialized as AuthError.Unknown).message)
    }

    @Test
    fun factoryMethodKeystoreError() {
        val error = AuthError.keystoreError("Backend unavailable")
        assertEquals("Keystore unavailable: Backend unavailable", error.message)
    }

    @Test
    fun factoryMethodTokenExpired() {
        val expiryTime = 1707148800000L
        val error = AuthError.tokenExpired(expiryTime)
        assertEquals("Token expired at $expiryTime", error.message)
    }

    @Test
    fun factoryMethodBiometricFailed() {
        val error = AuthError.biometricFailed(errorCode = -1001)
        assertEquals("Biometric authentication failed (code: -1001)", error.message)
    }
}
