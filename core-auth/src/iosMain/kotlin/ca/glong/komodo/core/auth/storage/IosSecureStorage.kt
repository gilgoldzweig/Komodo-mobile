package ca.glong.komodo.core.auth.storage

import ca.glong.komodo.core.auth.error.AuthError
import kotlinx.cinterop.*
import org.koin.core.annotation.Single
import platform.CoreFoundation.*
import platform.Foundation.*
import platform.Security.*
import platform.posix.memcpy

/**
 * iOS implementation of SecureStorage using Keychain Services.
 * All data stored in Keychain with transparent encryption.
 */
@Single(binds = [SecureStorage::class])
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosSecureStorage : SecureStorage {

    private companion object {
        const val SERVICE_NAME = "ca.glong.komodo"
        const val VERSION_KEY = "__storage_version__"
    }

    override suspend fun save(key: String, value: String): Result<Unit> = runCatching {
        saveToKeychain(key, value)
    }

    override suspend fun read(key: String): Result<String?> = runCatching {
        readFromKeychain(key)
    }

    override suspend fun delete(key: String): Result<Unit> = runCatching {
        deleteFromKeychain(key)
        Unit
    }

    override suspend fun contains(key: String): Result<Boolean> = runCatching {
        containsInKeychain(key)
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        clearKeychain()
        Unit
    }

    override suspend fun getVersion(): Result<Int> = runCatching {
        readFromKeychain(VERSION_KEY)?.toIntOrNull() ?: 1
    }

    override suspend fun setVersion(version: Int): Result<Unit> = runCatching {
        saveToKeychain(VERSION_KEY, version.toString())
    }

    private fun saveToKeychain(key: String, value: String) = memScoped {
        val keyStr = NSString.create(string = key)
        val valueStr = NSString.create(string = value)
        val valueData = valueStr.dataUsingEncoding(NSUTF8StringEncoding)
            ?: throw AuthError.StorageError.WriteFailed(key)

        val keys = allocArray<CFTypeRefVar>(5)
        val values = allocArray<CFTypeRefVar>(5)

        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()

        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()

        keys[2] = kSecAttrAccount?.reinterpret<CPointed>()
        val keyRef = CFBridgingRetain(keyStr)
        values[2] = keyRef?.reinterpret<CPointed>()

        keys[3] = kSecValueData?.reinterpret<CPointed>()
        val valueRef = CFBridgingRetain(valueData)
        values[3] = valueRef?.reinterpret<CPointed>()

        keys[4] = kSecAttrAccessible?.reinterpret<CPointed>()
        values[4] = kSecAttrAccessibleWhenUnlocked?.reinterpret<CPointed>()

        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            5,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )

        try {
            SecItemDelete(query)
            val status = SecItemAdd(query, null)
            if (status != errSecSuccess) {
                throw AuthError.StorageError.WriteFailed(key)
            }
        } finally {
            if (query != null) CFRelease(query)
            if (serviceRef != null) CFRelease(serviceRef)
            if (keyRef != null) CFRelease(keyRef)
            if (valueRef != null) CFRelease(valueRef)
        }
    }

    private fun readFromKeychain(key: String): String? = memScoped {
        val keyStr = NSString.create(string = key)
        val query = createReadQuery(keyStr)

        val result = alloc<ObjCObjectVar<NSData?>>()
        val status = SecItemCopyMatching(query, result.ptr.reinterpret())

        if (query != null) CFRelease(query)

        when (status) {
            errSecSuccess -> {
                val data = result.value ?: return@memScoped null
                val nsString = NSString.create(data = data, encoding = NSUTF8StringEncoding)
                nsString as? String
            }
            errSecItemNotFound -> null
            else -> throw AuthError.StorageError.ReadFailed(key)
        }
    }

    private fun deleteFromKeychain(key: String) = memScoped {
        val keyStr = NSString.create(string = key)
        val query = createBaseQuery(keyStr)

        val status = SecItemDelete(query)
        if (query != null) CFRelease(query)

        if (status != errSecSuccess && status != errSecItemNotFound) {
            throw AuthError.StorageError.WriteFailed(key)
        }
    }

    private fun containsInKeychain(key: String): Boolean = memScoped {
        val keyStr = NSString.create(string = key)
        val query = createBaseQuery(keyStr)

        val status = SecItemCopyMatching(query, null)
        if (query != null) CFRelease(query)

        status == errSecSuccess
    }

    private fun clearKeychain() = memScoped {
        val keys = allocArray<CFTypeRefVar>(2)
        val values = allocArray<CFTypeRefVar>(2)

        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()

        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()

        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            2,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )

        try {
            val status = SecItemDelete(query)
            if (status != errSecSuccess && status != errSecItemNotFound) {
                throw AuthError.StorageError.WriteFailed("__all__")
            }
        } finally {
            if (query != null) CFRelease(query)
            if (serviceRef != null) CFRelease(serviceRef)
        }
    }

    private fun createBaseQuery(key: NSString): CFDictionaryRef? = memScoped {
        val keys = allocArray<CFTypeRefVar>(3)
        val values = allocArray<CFTypeRefVar>(3)

        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()

        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()

        keys[2] = kSecAttrAccount?.reinterpret<CPointed>()
        val keyRef = CFBridgingRetain(key)
        values[2] = keyRef?.reinterpret<CPointed>()

        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            3,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )

        if (serviceRef != null) CFRelease(serviceRef)
        if (keyRef != null) CFRelease(keyRef)

        query
    }

    private fun createReadQuery(key: NSString): CFDictionaryRef? = memScoped {
        val keys = allocArray<CFTypeRefVar>(4)
        val values = allocArray<CFTypeRefVar>(4)

        keys[0] = kSecClass?.reinterpret<CPointed>()
        values[0] = kSecClassGenericPassword?.reinterpret<CPointed>()

        keys[1] = kSecAttrService?.reinterpret<CPointed>()
        val serviceRef = CFBridgingRetain(NSString.create(string = SERVICE_NAME))
        values[1] = serviceRef?.reinterpret<CPointed>()

        keys[2] = kSecAttrAccount?.reinterpret<CPointed>()
        val keyRef = CFBridgingRetain(key)
        values[2] = keyRef?.reinterpret<CPointed>()

        keys[3] = kSecReturnData?.reinterpret<CPointed>()
        values[3] = kCFBooleanTrue?.reinterpret<CPointed>()

        val query = CFDictionaryCreate(
            kCFAllocatorDefault,
            keys,
            values,
            4,
            kCFTypeDictionaryKeyCallBacks.ptr,
            kCFTypeDictionaryValueCallBacks.ptr
        )

        if (serviceRef != null) CFRelease(serviceRef)
        if (keyRef != null) CFRelease(keyRef)

        query
    }
}
