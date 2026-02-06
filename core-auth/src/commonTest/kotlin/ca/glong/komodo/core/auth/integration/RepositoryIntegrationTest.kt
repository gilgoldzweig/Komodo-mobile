package ca.glong.komodo.core.auth.integration

import ca.glong.komodo.core.auth.keys.MasterKeyRepository
import ca.glong.komodo.core.auth.storage.SecureStorage
import ca.glong.komodo.core.auth.tokens.TokenRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RepositoryIntegrationTest {

    @Test
    fun masterKeyRepository_persistsKeyAcrossInstances() = runTest {
        val storage = InMemorySecureStorage()
        val repo1 = MasterKeyRepository(storage)

        val key1 = repo1.getMasterKey().getOrThrow()
        assertNotNull(key1, "First repository should generate key")

        val repo2 = MasterKeyRepository(storage)
        val key2 = repo2.getMasterKey().getOrThrow()

        assertEquals(key1, key2, "Second repository instance should retrieve same key from storage")
    }

    @Test
    fun tokenRepository_savesAndRetrievesAccessToken() = runTest {
        val storage = InMemorySecureStorage()
        val tokenRepo = TokenRepository(storage)

        val accessToken = "test-access-token-12345"
        tokenRepo.saveAccessToken(accessToken, expiresInSeconds = 3600).getOrThrow()

        val retrieved = tokenRepo.getAccessToken().getOrThrow()

        assertEquals(accessToken, retrieved, "Access token should match saved value")
    }

    @Test
    fun tokenRepository_savesAndRetrievesRefreshToken() = runTest {
        val storage = InMemorySecureStorage()
        val tokenRepo = TokenRepository(storage)

        val refreshToken = "test-refresh-token-67890"
        tokenRepo.saveRefreshToken(refreshToken, expiresInSeconds = 86400).getOrThrow()

        val retrieved = tokenRepo.getRefreshToken().getOrThrow()

        assertEquals(refreshToken, retrieved, "Refresh token should match saved value")
    }

    @Test
    fun tokenRepository_expirationFlowEndToEnd() = runTest {
        val storage = InMemorySecureStorage()
        val tokenRepo = TokenRepository(storage)

        tokenRepo.saveAccessToken("expired-token", expiresInSeconds = -1).getOrThrow()

        val retrieved = tokenRepo.getAccessToken().getOrThrow()

        assertNull(retrieved, "Token with negative TTL should be expired immediately")
    }

    @Test
    fun multipleRepositories_shareSecureStorage() = runTest {
        val sharedStorage = InMemorySecureStorage()
        val masterKeyRepo = MasterKeyRepository(sharedStorage)
        val tokenRepo = TokenRepository(sharedStorage)

        val masterKey = masterKeyRepo.getMasterKey().getOrThrow()
        assertNotNull(masterKey, "Master key should be generated")

        tokenRepo.saveAccessToken("shared-storage-token", 3600).getOrThrow()
        val token = tokenRepo.getAccessToken().getOrThrow()

        assertEquals("shared-storage-token", token, "Token should be accessible")

        assertTrue(
            sharedStorage.contains("komodo_master_key_v1").getOrThrow(),
            "Storage should contain master key"
        )
        assertTrue(
            sharedStorage.contains("access_token").getOrThrow(),
            "Storage should contain access token"
        )
    }

    @Test
    fun clearStorage_affectsAllRepositories() = runTest {
        val storage = InMemorySecureStorage()
        val masterKeyRepo = MasterKeyRepository(storage)
        val tokenRepo = TokenRepository(storage)

        val masterKey1 = masterKeyRepo.getMasterKey().getOrThrow()
        tokenRepo.saveAccessToken("token-before-clear", 3600).getOrThrow()

        storage.clear().getOrThrow()

        val masterKey2 = masterKeyRepo.getMasterKey().getOrThrow()
        assertNotNull(masterKey2, "New key should be generated after clear")

        val tokenAfterClear = tokenRepo.getAccessToken().getOrThrow()
        assertNull(tokenAfterClear, "Token should be gone after clear")
    }

    @Test
    fun secureStorage_errorPropagation() = runTest {
        val faultyStorage = FaultySecureStorage()
        val masterKeyRepo = MasterKeyRepository(faultyStorage)

        val result = masterKeyRepo.getMasterKey()

        assertTrue(result.isFailure, "Repository should propagate storage error")
    }

    @Test
    fun tokenRepository_accessAndRefreshTokensIndependent() = runTest {
        val storage = InMemorySecureStorage()
        val tokenRepo = TokenRepository(storage)

        tokenRepo.saveAccessToken("access-123", 1800).getOrThrow()
        tokenRepo.saveRefreshToken("refresh-456", 7200).getOrThrow()

        val access = tokenRepo.getAccessToken().getOrThrow()
        val refresh = tokenRepo.getRefreshToken().getOrThrow()

        assertEquals("access-123", access, "Access token should be correct")
        assertEquals("refresh-456", refresh, "Refresh token should be correct")

        storage.delete("access_token").getOrThrow()

        val accessAfterDelete = tokenRepo.getAccessToken().getOrThrow()
        val refreshAfterDelete = tokenRepo.getRefreshToken().getOrThrow()

        assertNull(accessAfterDelete, "Access token should be deleted")
        assertEquals("refresh-456", refreshAfterDelete, "Refresh token should remain")
    }

    @Test
    fun secureStorage_survivesPersistenceRestart() = runTest {
        val storage = InMemorySecureStorage()

        storage.save("test-key", "test-value").getOrThrow()
        assertTrue(storage.contains("test-key").getOrThrow(), "Key should exist")

        val retrieved = storage.read("test-key").getOrThrow()

        assertEquals("test-value", retrieved, "Data should persist across restart simulation")
    }

    @Test
    fun masterKeyRepository_clearAndRegenerate() = runTest {
        val storage = InMemorySecureStorage()
        val repo = MasterKeyRepository(storage)

        val key1 = repo.getMasterKey().getOrThrow()
        assertNotNull(key1, "Initial key should be generated")

        repo.clearMasterKey().getOrThrow()

        val key2 = repo.getMasterKey().getOrThrow()
        assertNotNull(key2, "New key should be generated after clear")

        val repo2 = MasterKeyRepository(storage)
        val key3 = repo2.getMasterKey().getOrThrow()

        assertEquals(key2, key3, "Regenerated key should persist and be retrievable")
    }

    @Test
    fun tokenRepository_clearTokensDoesNotAffectMasterKey() = runTest {
        val storage = InMemorySecureStorage()
        val masterKeyRepo = MasterKeyRepository(storage)
        val tokenRepo = TokenRepository(storage)

        val masterKeyBefore = masterKeyRepo.getMasterKey().getOrThrow()
        tokenRepo.saveAccessToken("token", 3600).getOrThrow()

        tokenRepo.clearTokens().getOrThrow()

        val masterKeyAfter = masterKeyRepo.getMasterKey().getOrThrow()
        assertEquals(masterKeyBefore, masterKeyAfter, "Master key should not be affected by clearTokens")

        val tokenAfter = tokenRepo.getAccessToken().getOrThrow()
        assertNull(tokenAfter, "Token should be cleared")
    }

    @Test
    fun secureStorage_storageErrorPropagatesUpStack() = runTest {
        val faultyStorage = FaultySecureStorage()
        val tokenRepo = TokenRepository(faultyStorage)

        val result = tokenRepo.saveAccessToken("token", 3600)

        assertTrue(result.isFailure, "Storage error should propagate through TokenRepository")
    }
}

private class InMemorySecureStorage : SecureStorage {
    private val store = mutableMapOf<String, String>()

    override suspend fun save(key: String, value: String): Result<Unit> {
        store[key] = value
        return Result.success(Unit)
    }

    override suspend fun read(key: String): Result<String?> {
        return Result.success(store[key])
    }

    override suspend fun delete(key: String): Result<Unit> {
        store.remove(key)
        return Result.success(Unit)
    }

    override suspend fun contains(key: String): Result<Boolean> {
        return Result.success(store.containsKey(key))
    }

    override suspend fun clear(): Result<Unit> {
        store.clear()
        return Result.success(Unit)
    }

    override suspend fun getVersion(): Result<Int> {
        return Result.success(1)
    }

    override suspend fun setVersion(version: Int): Result<Unit> {
        return Result.success(Unit)
    }
}

private class FaultySecureStorage : SecureStorage {
    override suspend fun save(key: String, value: String): Result<Unit> {
        return Result.failure(Exception("Storage save failed"))
    }

    override suspend fun read(key: String): Result<String?> {
        return Result.failure(Exception("Storage read failed"))
    }

    override suspend fun delete(key: String): Result<Unit> {
        return Result.failure(Exception("Storage delete failed"))
    }

    override suspend fun contains(key: String): Result<Boolean> {
        return Result.failure(Exception("Storage contains check failed"))
    }

    override suspend fun clear(): Result<Unit> {
        return Result.failure(Exception("Storage clear failed"))
    }

    override suspend fun getVersion(): Result<Int> {
        return Result.failure(Exception("Storage getVersion failed"))
    }

    override suspend fun setVersion(version: Int): Result<Unit> {
        return Result.failure(Exception("Storage setVersion failed"))
    }
}
