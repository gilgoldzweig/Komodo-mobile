package ca.glong.komodo.core.auth.di

import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.keys.KeyManager
import ca.glong.komodo.core.auth.keys.MasterKeyRepository
import ca.glong.komodo.core.auth.storage.SecureStorage
import ca.glong.komodo.core.auth.tokens.TokenRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val coreAuthModule = module {
    single<EnvelopeEncryption> { platformCreateEnvelopeEncryption() }
    single<SecureStorage> { platformCreateSecureStorage() }
    single<KeyManager> { 
        platformCreateKeyManager(
            envelopeEncryption = get(),
            secureStorage = get()
        )
    }

    single { MasterKeyRepository(secureStorage = get()) }
    single { TokenRepository(secureStorage = get()) }
}

expect fun platformCreateEnvelopeEncryption(): EnvelopeEncryption
expect fun platformCreateSecureStorage(): SecureStorage
expect fun platformCreateKeyManager(
    envelopeEncryption: EnvelopeEncryption,
    secureStorage: SecureStorage
): KeyManager
