package ca.glong.komodo.core.auth.storage

import ca.glong.komodo.core.auth.error.AuthError
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.value
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointed
import org.koin.core.annotation.Single
import platform.CoreFoundation.CFAllocatorRef
import platform.CoreFoundation.CFDictionaryCreate
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFBooleanTrue
import platform.CoreFoundation.kCFTypeDictionaryKeyCallBacks
import platform.CoreFoundation.kCFTypeDictionaryValueCallBacks
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.SecItemUpdate
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.Security.errSecSuccess
import platform.Security.errSecItemNotFound
import platform.Security.errSecDuplicateItem

import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlock

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Single
class IosSecureStorage : SecureStorage {

    private val serviceName = "ca.glong.komodo.auth"

    override suspend fun save(key: String, value: String): Result<Unit> = runCatching {
        val nsValue = NSString.create(string = value)
        val data = nsValue.dataUsingEncoding(NSUTF8StringEncoding)
            ?: throw AuthError.StorageError("Failed to encode value")

        memScoped {
            val nsKey = NSString.create(string = key)
            val nsServiceName = NSString.create(string = serviceName)
            
            val keyRef = CFBridgingRetain(nsKey)
            val serviceNameRef = CFBridgingRetain(nsServiceName)
            val dataRef = CFBridgingRetain(data)
            
            try {
                val keys = allocArray<CFTypeRefVar>(5)
                val values = allocArray<CFTypeRefVar>(5)

                keys[0] = kSecClass?.reinterpret()
                values[0] = kSecClassGenericPassword?.reinterpret()

                keys[1] = kSecAttrService?.reinterpret()
                values[1] = serviceNameRef?.reinterpret()

                keys[2] = kSecAttrAccount?.reinterpret()
                values[2] = keyRef?.reinterpret()

                keys[3] = kSecValueData?.reinterpret()
                values[3] = dataRef?.reinterpret()
                
                keys[4] = kSecAttrAccessible?.reinterpret()
                values[4] = kSecAttrAccessibleAfterFirstUnlock?.reinterpret()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    5,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )
                
                try {
                    val status = SecItemAdd(query, null)

                    if (status == errSecDuplicateItem) {
                        val updateKeys = allocArray<CFTypeRefVar>(3)
                        val updateValues = allocArray<CFTypeRefVar>(3)

                        updateKeys[0] = kSecClass?.reinterpret()
                        updateValues[0] = kSecClassGenericPassword?.reinterpret()

                        updateKeys[1] = kSecAttrService?.reinterpret()
                        updateValues[1] = serviceNameRef?.reinterpret()

                        updateKeys[2] = kSecAttrAccount?.reinterpret()
                        updateValues[2] = keyRef?.reinterpret()
                        
                        val updateQuery = CFDictionaryCreate(
                            kCFAllocatorDefault,
                            updateKeys,
                            updateValues,
                            3,
                            kCFTypeDictionaryKeyCallBacks.ptr,
                            kCFTypeDictionaryValueCallBacks.ptr
                        )

                        val attrKeys = allocArray<CFTypeRefVar>(2)
                        val attrValues = allocArray<CFTypeRefVar>(2)
                        attrKeys[0] = kSecValueData?.reinterpret()
                        attrValues[0] = dataRef?.reinterpret()
                        
                        attrKeys[1] = kSecAttrAccessible?.reinterpret()
                        attrValues[1] = kSecAttrAccessibleAfterFirstUnlock?.reinterpret()

                        val attributesToUpdate = CFDictionaryCreate(
                            kCFAllocatorDefault,
                            attrKeys,
                            attrValues,
                            2,
                            kCFTypeDictionaryKeyCallBacks.ptr,
                            kCFTypeDictionaryValueCallBacks.ptr
                        )

                        try {
                            val updateStatus = SecItemUpdate(updateQuery, attributesToUpdate)
                            if (updateStatus != errSecSuccess) {
                                throw AuthError.StorageError("Failed to update existing item: $updateStatus")
                            }
                        } finally {
                             if (updateQuery != null) CFRelease(updateQuery)
                             if (attributesToUpdate != null) CFRelease(attributesToUpdate)
                        }
                    } else if (status != errSecSuccess) {
                        throw AuthError.StorageError("Failed to save item: $status")
                    }
                } finally {
                    if (query != null) CFRelease(query)
                }
            } finally {
                if (keyRef != null) CFRelease(keyRef)
                if (serviceNameRef != null) CFRelease(serviceNameRef)
                if (dataRef != null) CFRelease(dataRef)
            }
        }
        Unit
    }

    override suspend fun read(key: String): Result<String?> = runCatching {
        memScoped {
            val nsKey = NSString.create(string = key)
            val nsServiceName = NSString.create(string = serviceName)

            val keyRef = CFBridgingRetain(nsKey)
            val serviceNameRef = CFBridgingRetain(nsServiceName)
            
            try {
                val keys = allocArray<CFTypeRefVar>(5)
                val values = allocArray<CFTypeRefVar>(5)

                keys[0] = kSecClass?.reinterpret()
                values[0] = kSecClassGenericPassword?.reinterpret()

                keys[1] = kSecAttrService?.reinterpret()
                values[1] = serviceNameRef?.reinterpret()

                keys[2] = kSecAttrAccount?.reinterpret()
                values[2] = keyRef?.reinterpret()

                keys[3] = kSecReturnData?.reinterpret()
                values[3] = kCFBooleanTrue?.reinterpret()

                keys[4] = kSecMatchLimit?.reinterpret()
                values[4] = kSecMatchLimitOne?.reinterpret()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    5,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )

                try {
                    val result = alloc<CFTypeRefVar>()
                    val status = SecItemCopyMatching(query, result.ptr)

                    when (status) {
                        errSecSuccess -> {
                            val resultRef = result.value
                            if (resultRef != null) {
                                val releasedData = platform.Foundation.CFBridgingRelease(resultRef) as? NSData
                                releasedData?.let { NSString.create(it, NSUTF8StringEncoding)?.toString() }
                            } else {
                                null
                            }
                        }
                        errSecItemNotFound -> null
                        else -> throw AuthError.StorageError("Failed to read item: $status")
                    }
                } finally {
                     if (query != null) CFRelease(query)
                }
            } finally {
                if (keyRef != null) CFRelease(keyRef)
                if (serviceNameRef != null) CFRelease(serviceNameRef)
            }
        }
    }

    override suspend fun delete(key: String): Result<Unit> = runCatching {
        memScoped {
            val nsKey = NSString.create(string = key)
            val nsServiceName = NSString.create(string = serviceName)

            val keyRef = CFBridgingRetain(nsKey)
            val serviceNameRef = CFBridgingRetain(nsServiceName)
            
            try {
                val keys = allocArray<CFTypeRefVar>(3)
                val values = allocArray<CFTypeRefVar>(3)

                keys[0] = kSecClass?.reinterpret()
                values[0] = kSecClassGenericPassword?.reinterpret()

                keys[1] = kSecAttrService?.reinterpret()
                values[1] = serviceNameRef?.reinterpret()

                keys[2] = kSecAttrAccount?.reinterpret()
                values[2] = keyRef?.reinterpret()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    3,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )
                
                try {
                    val status = SecItemDelete(query)
                    if (status != errSecSuccess && status != errSecItemNotFound) {
                        throw AuthError.StorageError("Failed to delete item: $status")
                    }
                } finally {
                    if (query != null) CFRelease(query)
                }
            } finally {
                if (keyRef != null) CFRelease(keyRef)
                if (serviceNameRef != null) CFRelease(serviceNameRef)
            }
        }
        Unit
    }

    override suspend fun contains(key: String): Result<Boolean> = runCatching {
        memScoped {
            val nsKey = NSString.create(string = key)
            val nsServiceName = NSString.create(string = serviceName)

            val keyRef = CFBridgingRetain(nsKey)
            val serviceNameRef = CFBridgingRetain(nsServiceName)
            
            try {
                val keys = allocArray<CFTypeRefVar>(4)
                val values = allocArray<CFTypeRefVar>(4)

                keys[0] = kSecClass?.reinterpret()
                values[0] = kSecClassGenericPassword?.reinterpret()

                keys[1] = kSecAttrService?.reinterpret()
                values[1] = serviceNameRef?.reinterpret()

                keys[2] = kSecAttrAccount?.reinterpret()
                values[2] = keyRef?.reinterpret()
                
                keys[3] = kSecMatchLimit?.reinterpret()
                values[3] = kSecMatchLimitOne?.reinterpret()

                val query = CFDictionaryCreate(
                    kCFAllocatorDefault,
                    keys,
                    values,
                    4,
                    kCFTypeDictionaryKeyCallBacks.ptr,
                    kCFTypeDictionaryValueCallBacks.ptr
                )
                
                try {
                    val status = SecItemCopyMatching(query, null)
                    status == errSecSuccess
                } finally {
                    if (query != null) CFRelease(query)
                }
            } finally {
                if (keyRef != null) CFRelease(keyRef)
                if (serviceNameRef != null) CFRelease(serviceNameRef)
            }
        }
    }
}
