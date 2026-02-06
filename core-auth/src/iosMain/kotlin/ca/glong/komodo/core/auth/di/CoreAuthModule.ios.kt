package ca.glong.komodo.core.auth.di

import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.keys.KeyManager
import ca.glong.komodo.core.auth.storage.SecureStorage

actual fun platformCreateEnvelopeEncryption(): EnvelopeEncryption {
    TODO("iOS EnvelopeEncryption not implemented yet (Task 6)")
}

actual fun platformCreateSecureStorage(): SecureStorage {
    TODO("iOS SecureStorage not implemented yet (Task 8)")
}

actual fun platformCreateKeyManager(
    envelopeEncryption: EnvelopeEncryption,
    secureStorage: SecureStorage
): KeyManager {
    TODO("iOS KeyManager not implemented yet (Task 10)")
}
