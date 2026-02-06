package ca.glong.komodo.core.auth.di

import ca.glong.komodo.core.auth.encryption.IosEnvelopeEncryption
import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.keys.IosKeyManager
import ca.glong.komodo.core.auth.keys.KeyManager
import ca.glong.komodo.core.auth.storage.IosSecureStorage
import ca.glong.komodo.core.auth.storage.SecureStorage

actual fun platformCreateEnvelopeEncryption(): EnvelopeEncryption {
    return IosEnvelopeEncryption()
}

actual fun platformCreateSecureStorage(): SecureStorage {
    return IosSecureStorage()
}

actual fun platformCreateKeyManager(
    envelopeEncryption: EnvelopeEncryption,
    secureStorage: SecureStorage
): KeyManager {
    return IosKeyManager()
}
