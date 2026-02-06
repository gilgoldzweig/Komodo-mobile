package ca.glong.komodo.core.auth.keys

import ca.glong.komodo.core.auth.error.AuthError

interface EnvelopeEncryption {
    suspend fun encrypt(data: ByteArray, keyAlias: String): Result<ByteArray>
    suspend fun decrypt(encryptedData: ByteArray, keyAlias: String): Result<ByteArray>
    suspend fun getKeyMetadata(keyAlias: String): Result<KeyMetadata?>
}
