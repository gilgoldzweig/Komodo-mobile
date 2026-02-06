package ca.glong.komodo.core.auth.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import ca.glong.komodo.core.auth.encryption.AndroidEnvelopeEncryption
import ca.glong.komodo.core.auth.keys.EnvelopeEncryption
import ca.glong.komodo.core.auth.keys.AndroidKeyManager
import ca.glong.komodo.core.auth.keys.KeyManager
import ca.glong.komodo.core.auth.storage.AndroidSecureStorage
import ca.glong.komodo.core.auth.storage.SecureStorage
import org.koin.core.Koin
import java.io.File

actual fun platformCreateEnvelopeEncryption(): EnvelopeEncryption {
    return AndroidEnvelopeEncryption()
}

actual fun platformCreateSecureStorage(): SecureStorage {
    val context = getKoinContext().get<Context>()
    val dataStore = PreferenceDataStoreFactory.create {
        File(context.filesDir, "komodo_secure_storage.preferences_pb")
    }
    return AndroidSecureStorage(dataStore, context)
}

actual fun platformCreateKeyManager(
    envelopeEncryption: EnvelopeEncryption,
    secureStorage: SecureStorage
): KeyManager {
    val context = getKoinContext().get<Context>()
    return AndroidKeyManager(
        envelopeEncryption = envelopeEncryption,
        secureStorage = secureStorage as AndroidSecureStorage,
        context = context
    )
}

private fun getKoinContext(): Koin {
    return try {
        org.koin.core.context.GlobalContext.get()
    } catch (e: Exception) {
        throw IllegalStateException("Koin context not initialized", e)
    }
}
