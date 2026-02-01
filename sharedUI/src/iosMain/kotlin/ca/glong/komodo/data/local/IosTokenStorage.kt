package ca.glong.komodo.data.local

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFTypeRefVar
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Foundation.setValue
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.SecItemUpdate
import platform.Security.errSecSuccess
import org.koin.core.annotation.Single

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Single
class IosTokenStorage : TokenStorage {
    private val service = "ca.glong.komodo.auth"

    @Suppress("CAST_NEVER_SUCCEEDS") // It does succeed in ObjC interop
    private fun key(ref: platform.CoreFoundation.CFStringRef?): String {
        return ref as String
    }

    // cast to Any? for values
    private fun value(ref: platform.CoreFoundation.CFStringRef?): Any? = ref

    override suspend fun saveToken(id: String, token: String) {
        val data = NSString.create(string = token).dataUsingEncoding(NSUTF8StringEncoding)

        val query = NSMutableDictionary().apply {
            setValue(value(kSecClassGenericPassword), forKey = key(kSecClass))
            setValue(service, forKey = key(kSecAttrService))
            setValue(id, forKey = key(kSecAttrAccount))
        }

        val queryRef = CFBridgingRetain(query) as? CFDictionaryRef

        try {
            memScoped {
                val result = alloc<CFTypeRefVar>()
                val status = SecItemCopyMatching(queryRef, result.ptr)

                if (status == errSecSuccess) {
                    val attributesToUpdate = NSMutableDictionary().apply {
                         setValue(data, forKey = key(kSecValueData))
                    }
                    val attributesRef = CFBridgingRetain(attributesToUpdate) as? CFDictionaryRef
                    try {
                        SecItemUpdate(queryRef, attributesRef)
                    } finally {
                        CFBridgingRelease(attributesRef)
                    }
                } else {
                     val newItem = NSMutableDictionary().apply {
                        setValue(value(kSecClassGenericPassword), forKey = key(kSecClass))
                        setValue(service, forKey = key(kSecAttrService))
                        setValue(id, forKey = key(kSecAttrAccount))
                        setValue(data, forKey = key(kSecValueData))
                    }
                    val newItemRef = CFBridgingRetain(newItem) as? CFDictionaryRef
                    try {
                        SecItemAdd(newItemRef, null)
                    } finally {
                        CFBridgingRelease(newItemRef)
                    }
                }
            }
        } finally {
            CFBridgingRelease(queryRef)
        }
    }

    override suspend fun getToken(id: String): String? {
        val query = NSMutableDictionary().apply {
            setValue(value(kSecClassGenericPassword), forKey = key(kSecClass))
            setValue(service, forKey = key(kSecAttrService))
            setValue(id, forKey = key(kSecAttrAccount))
            setValue(true, forKey = key(kSecReturnData))
            setValue(value(kSecMatchLimitOne), forKey = key(kSecMatchLimit))
        }

        val queryRef = CFBridgingRetain(query) as? CFDictionaryRef

        try {
            memScoped {
                val result = alloc<CFTypeRefVar>()
                val status = SecItemCopyMatching(queryRef, result.ptr)

                if (status == errSecSuccess) {
                    val data = CFBridgingRelease(result.value) as? NSData
                    return data?.let {
                        NSString.create(data = it, encoding = NSUTF8StringEncoding).toString()
                    }
                }
            }
        } finally {
            CFBridgingRelease(queryRef)
        }
        return null
    }

    override suspend fun deleteToken(id: String) {
        val query = NSMutableDictionary().apply {
            setValue(value(kSecClassGenericPassword), forKey = key(kSecClass))
            setValue(service, forKey = key(kSecAttrService))
            setValue(id, forKey = key(kSecAttrAccount))
        }

        val queryRef = CFBridgingRetain(query) as? CFDictionaryRef
        try {
            SecItemDelete(queryRef)
        } finally {
            CFBridgingRelease(queryRef)
        }
    }

    override suspend fun deleteAllTokens() {
        val query = NSMutableDictionary().apply {
            setValue(value(kSecClassGenericPassword), forKey = key(kSecClass))
            setValue(service, forKey = key(kSecAttrService))
        }

        val queryRef = CFBridgingRetain(query) as? CFDictionaryRef
        try {
            SecItemDelete(queryRef)
        } finally {
            CFBridgingRelease(queryRef)
        }
    }
}
