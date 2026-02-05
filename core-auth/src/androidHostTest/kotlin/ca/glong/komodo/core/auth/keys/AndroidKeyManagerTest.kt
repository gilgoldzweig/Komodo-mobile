package ca.glong.komodo.core.auth.keys

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class AndroidKeyManagerTest {

    private val keyManager = AndroidKeyManager()
    private val testAlias = "test_key_alias"

    @Test
    @org.junit.Ignore("Requires AndroidKeyStore which is not fully supported in Robolectric unit tests. Run on device.")
    fun testKeyGenerationAndRetrieval() = runTest {
        keyManager.deleteKey(testAlias)

        val generationResult = keyManager.generateKeyPair(testAlias)
        assertTrue("Key generation failed: ${generationResult.exceptionOrNull()}", generationResult.isSuccess)

        val hasKeyResult = keyManager.hasKey(testAlias)
        assertTrue("Has key check failed", hasKeyResult.isSuccess)
        assertTrue("Key should exist", hasKeyResult.getOrNull() == true)

        val publicKeyResult = keyManager.getPublicKey(testAlias)
        assertTrue("Public key retrieval failed", publicKeyResult.isSuccess)
        val publicKey = publicKeyResult.getOrNull()
        assertNotNull("Public key should not be null", publicKey)
        assertTrue("Public key should not be empty", publicKey!!.isNotEmpty())
    }

    @Test
    @org.junit.Ignore("Requires AndroidKeyStore which is not fully supported in Robolectric unit tests. Run on device.")
    fun testSigning() = runTest {
        keyManager.deleteKey(testAlias)
        
        val generationResult = keyManager.generateKeyPair(testAlias)
        assertTrue("Key generation failed: ${generationResult.exceptionOrNull()}", generationResult.isSuccess)

        val dataToSign = "Hello Komodo".toByteArray()

        val signatureResult = keyManager.signData(testAlias, dataToSign)
        assertTrue("Signing failed: ${signatureResult.exceptionOrNull()}", signatureResult.isSuccess)
        val signature = signatureResult.getOrNull()
        assertNotNull("Signature should not be null", signature)
        assertTrue("Signature should not be empty", signature!!.isNotEmpty())
    }
}
