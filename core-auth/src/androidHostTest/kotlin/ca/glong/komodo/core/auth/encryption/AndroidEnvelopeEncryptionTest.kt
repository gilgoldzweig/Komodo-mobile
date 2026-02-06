package ca.glong.komodo.core.auth.encryption

import ca.glong.komodo.core.auth.error.AuthError
import ca.glong.komodo.core.auth.keys.KeyAlgorithm
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class AndroidEnvelopeEncryptionTest {
    
    private lateinit var envelopeEncryption: AndroidEnvelopeEncryption
    private val testKeyAlias = "test_envelope_key"
    
    @Before
    fun setup() {
        envelopeEncryption = AndroidEnvelopeEncryption()
    }
    
    @Test
    fun `encrypt and decrypt round-trip succeeds`() = runTest {
        val plaintext = "test secret data".toByteArray()
        
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
    fun `encrypted output includes version byte`() = runTest {
        val plaintext = "test".toByteArray()
        
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        assertEquals(0x01.toByte(), encrypted[0], "First byte should be version")
    }
    
    @Test
    fun `encrypted output includes algorithm identifier`() = runTest {
        val plaintext = "test".toByteArray()
        
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        assertEquals(0x01.toByte(), encrypted[1], "Second byte should be algorithm")
    }
    
    @Test
    fun `tampered data returns EnvelopeError`() = runTest {
        val plaintext = "test secret".toByteArray()
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val tampered = encrypted.copyOf()
        tampered[tampered.size - 1] = (tampered[tampered.size - 1] + 1).toByte()
        
        val decryptResult = envelopeEncryption.decrypt(tampered, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Tampered data should fail")
        val exception = decryptResult.exceptionOrNull()
        assertIs<AuthError.EnvelopeError.DecryptionFailed>(exception)
    }
    
    @Test
    fun `invalid version returns EnvelopeError`() = runTest {
        val plaintext = "test".toByteArray()
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val invalidVersion = encrypted.copyOf()
        invalidVersion[0] = 0x99.toByte()
        
        val decryptResult = envelopeEncryption.decrypt(invalidVersion, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Invalid version should fail")
        val exception = decryptResult.exceptionOrNull()
        assertIs<AuthError.EnvelopeError.InvalidEnvelope>(exception)
    }
    
    @Test
    fun `invalid algorithm returns EnvelopeError`() = runTest {
        val plaintext = "test".toByteArray()
        val encrypted = envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val invalidAlgo = encrypted.copyOf()
        invalidAlgo[1] = 0x99.toByte()
        
        val decryptResult = envelopeEncryption.decrypt(invalidAlgo, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Invalid algorithm should fail")
        val exception = decryptResult.exceptionOrNull()
        assertIs<AuthError.EnvelopeError.InvalidEnvelope>(exception)
    }
    
    @Test
    fun `too short encrypted data returns EnvelopeError`() = runTest {
        val tooShort = ByteArray(10)
        
        val decryptResult = envelopeEncryption.decrypt(tooShort, testKeyAlias)
        
        assertTrue(decryptResult.isFailure, "Too short data should fail")
    }
    
    @Test
    fun `empty plaintext encrypts and decrypts correctly`() = runTest {
        val emptyPlaintext = ByteArray(0)
        
        val encrypted = envelopeEncryption.encrypt(emptyPlaintext, testKeyAlias).getOrThrow()
        val decrypted = envelopeEncryption.decrypt(encrypted, testKeyAlias).getOrThrow()
        
        assertContentEquals(emptyPlaintext, decrypted)
    }
    
    @Test
    fun `large plaintext encrypts and decrypts correctly`() = runTest {
        val largePlaintext = ByteArray(10000) { it.toByte() }
        
        val encrypted = envelopeEncryption.encrypt(largePlaintext, testKeyAlias).getOrThrow()
        val decrypted = envelopeEncryption.decrypt(encrypted, testKeyAlias).getOrThrow()
        
        assertContentEquals(largePlaintext, decrypted)
    }
    
    @Test
    fun `getKeyMetadata returns metadata for existing key`() = runTest {
        val plaintext = "test".toByteArray()
        envelopeEncryption.encrypt(plaintext, testKeyAlias).getOrThrow()
        
        val metadata = envelopeEncryption.getKeyMetadata(testKeyAlias).getOrThrow()
        
        assertTrue(metadata != null, "Metadata should exist")
        assertEquals(testKeyAlias, metadata?.alias)
    }
    
    @Test
    fun `getKeyMetadata returns null for non-existent key`() = runTest {
        val metadata = envelopeEncryption.getKeyMetadata("non_existent_key").getOrThrow()
        
        assertEquals(null, metadata, "Metadata should be null for non-existent key")
    }
}

