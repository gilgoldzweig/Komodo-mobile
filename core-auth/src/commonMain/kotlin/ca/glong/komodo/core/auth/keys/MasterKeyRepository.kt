package ca.glong.komodo.core.auth.keys

import ca.glong.komodo.core.auth.storage.SecureStorage
import kotlin.random.Random

class MasterKeyRepository(
    private val secureStorage: SecureStorage
) {
    private val MASTER_KEY_ALIAS = "komodo_master_key_v1"

    suspend fun getMasterKey(): Result<String> {
        return secureStorage.read(MASTER_KEY_ALIAS).mapCatching { storedKey ->
            if (storedKey != null) {
                storedKey
            } else {
                val newKey = generateRandomKey()
                secureStorage.save(MASTER_KEY_ALIAS, newKey).getOrThrow()
                newKey
            }
        }
    }
    
    suspend fun clearMasterKey(): Result<Unit> {
        return secureStorage.delete(MASTER_KEY_ALIAS)
    }

    private fun generateRandomKey(): String {
        val bytes = Random.nextBytes(32)
        return bytes.joinToString("") { it.toUByte().toString(16).padStart(2, '0') }
    }
}
