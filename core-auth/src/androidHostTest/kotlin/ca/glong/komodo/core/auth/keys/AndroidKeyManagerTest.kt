package ca.glong.komodo.core.auth.keys

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import ca.glong.komodo.core.auth.encryption.AndroidEnvelopeEncryption
import ca.glong.komodo.core.auth.error.AuthError
import ca.glong.komodo.core.auth.storage.AndroidSecureStorage
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File

private val Context.testDataStore: DataStore<Preferences> by preferencesDataStore(name = "test_secure_storage")

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class AndroidKeyManagerTest {

    private lateinit var context: Context
    private lateinit var envelopeEncryption: AndroidEnvelopeEncryption
    private lateinit var secureStorage: AndroidSecureStorage
    private lateinit var keyManager: AndroidKeyManager

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        
        // Clear any existing data
        val prefsDir = File(context.filesDir, "datastore")
        prefsDir.deleteRecursively()
        
        envelopeEncryption = AndroidEnvelopeEncryption()
        secureStorage = AndroidSecureStorage(context.testDataStore, context)
        keyManager = AndroidKeyManager(envelopeEncryption, secureStorage, context)
    }

    @After
    fun tearDown() = runTest {
        // Clean up test keys
        listOf("test_ed25519", "test_p256", "test_persist", "test_ssh_ed25519", "test_ssh_p256")
            .forEach { alias ->
                keyManager.deleteKey(alias).getOrNull()
            }
    }

    // Test 1: Generate Ed25519 key
    @Test
    fun testGenerateEd25519Key() = runTest {
        val alias = "test_ed25519"
        
        val result = keyManager.generateKeyPair(alias, KeyType.ED25519)
        
        if (result.isFailure) {
            val exception = result.exceptionOrNull()
            throw AssertionError("Ed25519 key generation failed: ${exception?.message}", exception)
        }
        
        assertTrue("Ed25519 key generation should succeed", result.isSuccess)
        
        val hasKey = keyManager.hasKey(alias).getOrThrow()
        assertTrue("Key should exist after generation", hasKey)
    }

    // Test 2: Generate P-256 key
    @Test
    fun testGenerateP256Key() = runTest {
        val alias = "test_p256"
        
        val result = keyManager.generateKeyPair(alias, KeyType.EC_P256)
        
        assertTrue("P-256 key generation should succeed", result.isSuccess)
        
        val hasKey = keyManager.hasKey(alias).getOrThrow()
        assertTrue("Key should exist after generation", hasKey)
    }

    // Test 3: Sign with Ed25519
    @Test
    fun testSignWithEd25519() = runTest {
        val alias = "test_ed25519"
        keyManager.generateKeyPair(alias, KeyType.ED25519).getOrThrow()
        
        val data = "Hello Komodo".toByteArray()
        val result = keyManager.signData(alias, data)
        
        assertTrue("Signing should succeed", result.isSuccess)
        val signature = result.getOrThrow()
        assertNotNull("Signature should not be null", signature)
        assertTrue("Signature should not be empty", signature.isNotEmpty())
        assertEquals("Ed25519 signature should be 64 bytes", 64, signature.size)
    }

    // Test 4: Sign with P-256
    @Test
    fun testSignWithP256() = runTest {
        val alias = "test_p256"
        keyManager.generateKeyPair(alias, KeyType.EC_P256).getOrThrow()
        
        val data = "Hello Komodo".toByteArray()
        val result = keyManager.signData(alias, data)
        
        assertTrue("Signing should succeed", result.isSuccess)
        val signature = result.getOrThrow()
        assertNotNull("Signature should not be null", signature)
        assertTrue("Signature should not be empty", signature.isNotEmpty())
        // P-256 signature size varies (typically 70-72 bytes in DER encoding)
        assertTrue("P-256 signature should be reasonable size", signature.size in 64..80)
    }

    // Test 5: Sign same data twice produces different signatures (due to randomness in ECDSA)
    @Test
    fun testSignatureRandomness() = runTest {
        val alias = "test_p256"
        keyManager.generateKeyPair(alias, KeyType.EC_P256).getOrThrow()
        
        val data = "Test data".toByteArray()
        val signature1 = keyManager.signData(alias, data).getOrThrow()
        val signature2 = keyManager.signData(alias, data).getOrThrow()
        
        // For P-256 (ECDSA), signatures should differ due to random k value
        assertFalse("ECDSA signatures should be different", signature1.contentEquals(signature2))
    }

    // Test 6: Get public key returns correct bytes
    @Test
    fun testGetPublicKey() = runTest {
        val alias = "test_ed25519"
        keyManager.generateKeyPair(alias, KeyType.ED25519).getOrThrow()
        
        val result = keyManager.getPublicKey(alias)
        
        assertTrue("Getting public key should succeed", result.isSuccess)
        val publicKey = result.getOrThrow()
        assertNotNull("Public key should not be null", publicKey)
        assertTrue("Public key should not be empty", publicKey!!.isNotEmpty())
    }

    // Test 7: Delete key removes key
    @Test
    fun testDeleteKey() = runTest {
        val alias = "test_ed25519"
        keyManager.generateKeyPair(alias, KeyType.ED25519).getOrThrow()
        
        assertTrue("Key should exist before deletion", keyManager.hasKey(alias).getOrThrow())
        
        val deleteResult = keyManager.deleteKey(alias)
        assertTrue("Delete should succeed", deleteResult.isSuccess)
        
        assertFalse("Key should not exist after deletion", keyManager.hasKey(alias).getOrThrow())
    }

    // Test 8: Get non-existent key returns null
    @Test
    fun testGetNonExistentKey() = runTest {
        val alias = "non_existent_key"
        
        val result = keyManager.getPublicKey(alias)
        
        assertTrue("Getting non-existent key should succeed but return null", result.isSuccess)
        val publicKey = result.getOrThrow()
        assertNull("Public key should be null for non-existent key", publicKey)
    }

    // Test 9: Sign with non-existent key fails
    @Test
    fun testSignWithNonExistentKey() = runTest {
        val alias = "non_existent_key"
        val data = "Test".toByteArray()
        
        val result = keyManager.signData(alias, data)
        
        assertTrue("Signing with non-existent key should fail", result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue("Exception should be KeyError.LoadFailed", exception is AuthError.KeyError.LoadFailed)
    }

    // Test 10: Has key returns false for non-existent key
    @Test
    fun testHasKeyNonExistent() = runTest {
        val alias = "non_existent_key"
        
        val result = keyManager.hasKey(alias)
        
        assertTrue("hasKey should succeed", result.isSuccess)
        assertFalse("Key should not exist", result.getOrThrow())
    }

    // Test 11: Export Ed25519 SSH key
    @Test
    fun testExportEd25519SshKey() = runTest {
        val alias = "test_ssh_ed25519"
        keyManager.generateKeyPair(alias, KeyType.ED25519).getOrThrow()
        
        val result = keyManager.exportSshKey(alias)
        
        assertTrue("SSH key export should succeed", result.isSuccess)
        val sshKey = result.getOrThrow()
        assertTrue("SSH key should start with ssh-ed25519", sshKey.startsWith("ssh-ed25519 "))
        assertTrue("SSH key should end with alias@android", sshKey.endsWith("$alias@android"))
        
        // Verify format: "ssh-ed25519 <base64> alias@android"
        val parts = sshKey.split(" ")
        assertEquals("SSH key should have 3 parts", 3, parts.size)
        assertEquals("First part should be key type", "ssh-ed25519", parts[0])
        assertTrue("Second part should be base64", parts[1].matches(Regex("[A-Za-z0-9+/]+=*")))
        assertEquals("Third part should be comment", "$alias@android", parts[2])
    }

    // Test 12: Export P-256 SSH key
    @Test
    fun testExportP256SshKey() = runTest {
        val alias = "test_ssh_p256"
        keyManager.generateKeyPair(alias, KeyType.EC_P256).getOrThrow()
        
        val result = keyManager.exportSshKey(alias)
        
        assertTrue("SSH key export should succeed", result.isSuccess)
        val sshKey = result.getOrThrow()
        assertTrue("SSH key should start with ecdsa-sha2-nistp256", sshKey.startsWith("ecdsa-sha2-nistp256 "))
        assertTrue("SSH key should end with alias@android", sshKey.endsWith("$alias@android"))
        
        // Verify format: "ecdsa-sha2-nistp256 <base64> alias@android"
        val parts = sshKey.split(" ")
        assertEquals("SSH key should have 3 parts", 3, parts.size)
        assertEquals("First part should be key type", "ecdsa-sha2-nistp256", parts[0])
        assertTrue("Second part should be base64", parts[1].matches(Regex("[A-Za-z0-9+/]+=*")))
        assertEquals("Third part should be comment", "$alias@android", parts[2])
    }

    // Test 13: Key persistence - generate, recreate manager, verify exists
    @Test
    fun testKeyPersistence() = runTest {
        val alias = "test_persist"
        keyManager.generateKeyPair(alias, KeyType.ED25519).getOrThrow()
        
        // Create new manager instance (simulating app restart)
        val newKeyManager = AndroidKeyManager(envelopeEncryption, secureStorage, context)
        
        val hasKey = newKeyManager.hasKey(alias).getOrThrow()
        assertTrue("Key should persist across manager instances", hasKey)
        
        val publicKey = newKeyManager.getPublicKey(alias).getOrThrow()
        assertNotNull("Public key should be retrievable after restart", publicKey)
    }

    // Test 14: Sign after persistence
    @Test
    fun testSignAfterPersistence() = runTest {
        val alias = "test_persist"
        val data = "Persistence test".toByteArray()
        
        keyManager.generateKeyPair(alias, KeyType.ED25519).getOrThrow()
        val signature1 = keyManager.signData(alias, data).getOrThrow()
        
        // Create new manager instance
        val newKeyManager = AndroidKeyManager(envelopeEncryption, secureStorage, context)
        val signature2 = newKeyManager.signData(alias, data).getOrThrow()
        
        // Ed25519 signatures are deterministic in Tink, so they should be identical
        assertArrayEquals("Signatures should be identical for Ed25519", signature1, signature2)
    }

    // Test 15: Generate duplicate key fails
    @Test
    fun testGenerateDuplicateKeyFails() = runTest {
        val alias = "test_p256"
        
        keyManager.generateKeyPair(alias, KeyType.EC_P256).getOrThrow()
        
        val result = keyManager.generateKeyPair(alias, KeyType.EC_P256)
        
        assertTrue("Generating duplicate key should fail", result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue("Exception should be KeyError.GenerationFailed", exception is AuthError.KeyError.GenerationFailed)
    }

    // Test 16: RSA_4096 not supported
    @Test
    fun testRsaNotSupported() = runTest {
        val alias = "test_rsa"
        
        val result = keyManager.generateKeyPair(alias, KeyType.RSA_4096)
        
        assertTrue("RSA generation should fail", result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue("Exception should be KeyError.GenerationFailed", exception is AuthError.KeyError.GenerationFailed)
    }
}
