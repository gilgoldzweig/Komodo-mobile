package ca.glong.komodo.core.auth.encryption

import ca.glong.komodo.core.auth.error.AuthError
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class IosEnvelopeEncryptionTest {
    
    private lateinit var envelopeEncryption: IosEnvelopeEncryption
    private val testKeyAlias = "test_envelope_key"
    
    @BeforeTest
    fun setup() {
        envelopeEncryption = IosEnvelopeEncryption()
    }
    
    @Test
    fun encryptAndDecryptRoundTripSucceeds() = runTest {
        val plaintext = "test secret data".encodeToByteArray()
        
        val encryptResult = envelopeEncryption.encrypt(plaintext, testKeyAlias)
        assertTrue(encryptResult.isSuccess, "Encrypt should succeed")
        
        val encrypted = encryptResult.getOrThrow()
        assertTrue(encrypted.size > plaintext.size, "Encrypted data should be larger")
        
        val decryptResult = envelopeEncryption.decrypt(encrypted, testKeyAlias)
        assertTrue(decryptResult.isSuccess, "Decrypt should succeed")
        
        val decrypted = decryptResult.getOrThrow()
        assertContentEquals(plaintext, decrypted, "Round-trip should preserve data")
    }
    
    @Test
    fun encryptedOutputIncludesVersionByte() = runTest {
        val plaintext = "test".encodeToByteArray()
        
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        assertEquals(0x01.toByte(), encrypted[0], "First byte should be version")
    }
    
    @Test
    fun encryptedOutputIncludesAlgorithmIdentifier() = runTest {
        val plaintext = "test".encodeToByteArray()
        
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        assertEquals(0x01.toByte(), encrypted[1], "Second byte should be algorithm")
    }
    
    @Test
    fun tamperedDataReturnsEnvelopeError() = runTest {
        val plaintext = "test secret".encodeToByteArray()
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val tampered = encrypted.copyOf()
        tampered[tampered.size - 1] = (tampered[tampered.size - 1] + 1).toByte()
        
        val decryptResult = envelopeEncryption.decrypt(tampered, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Tampered data should fail")
        val exception = decryptResult.exceptionOrNull()
        assertIs<AuthError.EnvelopeError.DecryptionFailed>(exception)
    }
    
    @Test
    fun invalidVersionReturnsEnvelopeError() = runTest {
        val plaintext = "test".encodeToByteArray()
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val invalidVersion = encrypted.copyOf()
        invalidVersion[0] = 0x99.toByte()
        
        val decryptResult = envelopeEncryption.decrypt(invalidVersion, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Invalid version should fail")
        val exception = decryptResult.exceptionOrNull()
        assertIs<AuthError.EnvelopeError.InvalidEnvelope>(exception)
    }
    
    @Test
    fun invalidAlgorithmReturnsEnvelopeError() = runTest {
        val plaintext = "test".encodeToByteArray()
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val invalidAlgo = encrypted.copyOf()
        invalidAlgo[1] = 0x99.toByte()
        
        val decryptResult = envelopeEncryption.decrypt(invalidAlgo, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Invalid algorithm should fail")
        val exception = decryptResult.exceptionOrNull()
        assertIs<AuthError.EnvelopeError.InvalidEnvelope>(exception)
    }
    
    @Test
    fun tooShortEncryptedDataReturnsEnvelopeError() = runTest {
        val tooShort = ByteArray(10)
        
        val decryptResult = envelopeEncryption.decrypt(tooShort, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Too short data should fail")
    }
    
    @Test
    fun emptyPlaintextEncryptsAndDecryptsCorrectly() = runTest {
        val emptyPlaintext = ByteArray(0)
        
        val encrypted = envelopeEncryption.encrypt(emptyPlaintext, testKeyAlias).getOrThrow()
        val decrypted = envelopeEncryption.decrypt(encrypted, testKeyAlias).getOrThrow()
        
        assertContentEquals(emptyPlaintext, decrypted)
    }
    
    @Test
    fun largePlaintextEncryptsAndDecryptsCorrectly() = runTest {
        val largePlaintext = ByteArray(10000) { it.toByte() }
        
        val encrypted = envelopeEncryption.encrypt(largePlaintext, testKeyAlias).getOrThrow()
        val decrypted = envelopeEncryption.decrypt(encrypted, testKeyAlias).getOrThrow()
        
        assertContentEquals(largePlaintext, decrypted)
    }
    
    @Test
    fun getKeyMetadataReturnsMetadataForExistingKey() = runTest {
        val plaintext = "test".encodeToByteArray()
        envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val metadata = envelopeEncryption.getKeyMetadata(testKeyAlias).getOrThrow()
        
        assertTrue(metadata != null, "Metadata should exist")
        assertEquals(testKeyAlias, metadata?.alias)
    }
    
    @Test
    fun getKeyMetadataReturnsNullForNonExistentKey() = runTest {
        val metadata = envelopeEncryption.getKeyMetadata("non_existent_key").getOrThrow()
        
        assertEquals(null, metadata, "Metadata should be null for non-existent key")
    }
}
