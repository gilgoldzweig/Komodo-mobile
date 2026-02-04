package ca.glong.komodo.core.auth.keys

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class IosKeyManagerTest {

    private val keyManager = IosKeyManager()
    private val testAlias = "test_key_alias"

    @Test
    fun testKeyGenerationAndRetrieval() = runBlocking {
        keyManager.deleteKey(testAlias)
        
        val generationResult = keyManager.generateKeyPair(testAlias)
        assertTrue(generationResult.isSuccess, "Key generation failed: ${generationResult.exceptionOrNull()}")
        
        val hasKeyResult = keyManager.hasKey(testAlias)
        assertTrue(hasKeyResult.isSuccess, "Has key check failed")
        assertTrue(hasKeyResult.getOrNull() == true, "Key should exist")
        
        val publicKeyResult = keyManager.getPublicKey(testAlias)
        assertTrue(publicKeyResult.isSuccess, "Public key retrieval failed")
        assertNotNull(publicKeyResult.getOrNull(), "Public key should not be null")
        assertTrue(publicKeyResult.getOrNull()!!.isNotEmpty(), "Public key should not be empty")
        
        keyManager.deleteKey(testAlias)
        Unit
    }
    
    @Test
    fun testSigning() = runBlocking {
        keyManager.deleteKey(testAlias)
        keyManager.generateKeyPair(testAlias)
        
        val dataToSign = "Hello Komodo".encodeToByteArray()
        
        val signatureResult = keyManager.signData(testAlias, dataToSign)
        assertTrue(signatureResult.isSuccess, "Signing failed: ${signatureResult.exceptionOrNull()}")
        val signature = signatureResult.getOrNull()
        assertNotNull(signature, "Signature should not be null")
        assertTrue(signature.isNotEmpty(), "Signature should not be empty")
        
        keyManager.deleteKey(testAlias)
        Unit
    }
}
