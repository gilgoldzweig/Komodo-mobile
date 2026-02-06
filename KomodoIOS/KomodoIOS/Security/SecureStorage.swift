import Foundation
import Security

enum SecureStorageError: Error {
    case itemNotFound
    case duplicateItem
    case authenticationFailed
    case unexpectedStatus(OSStatus)
    case invalidKey
    case corruptedData
}

@objc public class SecureStorage: NSObject {
    
    private let serviceName = "ca.glong.komodo"
    private let versionKey = "__storage_version__"
    
    func save(key: String, value: String) throws {
        guard !key.isEmpty else {
            throw SecureStorageError.invalidKey
        }
        
        guard let valueData = value.data(using: .utf8) else {
            throw SecureStorageError.corruptedData
        }
        
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: serviceName,
            kSecAttrAccount as String: key,
            kSecAttrAccessible as String: kSecAttrAccessibleWhenUnlocked
        ]
        
        let existsStatus = SecItemCopyMatching(query as CFDictionary, nil)
        
        if existsStatus == errSecSuccess {
            let updateQuery: [String: Any] = [
                kSecValueData as String: valueData
            ]
            let status = SecItemUpdate(query as CFDictionary, updateQuery as CFDictionary)
            guard status == errSecSuccess else {
                throw SecureStorageError.unexpectedStatus(status)
            }
        } else if existsStatus == errSecItemNotFound {
            var addQuery = query
            addQuery[kSecValueData as String] = valueData
            
            let status = SecItemAdd(addQuery as CFDictionary, nil)
            guard status == errSecSuccess else {
                throw SecureStorageError.unexpectedStatus(status)
            }
        } else {
            throw SecureStorageError.unexpectedStatus(existsStatus)
        }
    }
    
    func read(key: String) throws -> String? {
        guard !key.isEmpty else {
            throw SecureStorageError.invalidKey
        }
        
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: serviceName,
            kSecAttrAccount as String: key,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]
        
        var result: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &result)
        
        if status == errSecItemNotFound {
            return nil
        }
        
        guard status == errSecSuccess else {
            throw SecureStorageError.unexpectedStatus(status)
        }
        
        guard let data = result as? Data,
              let value = String(data: data, encoding: .utf8) else {
            throw SecureStorageError.corruptedData
        }
        
        return value
    }
    
    func delete(key: String) throws {
        guard !key.isEmpty else {
            throw SecureStorageError.invalidKey
        }
        
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: serviceName,
            kSecAttrAccount as String: key
        ]
        
        let status = SecItemDelete(query as CFDictionary)
        
        guard status == errSecSuccess || status == errSecItemNotFound else {
            throw SecureStorageError.unexpectedStatus(status)
        }
    }
    
    func contains(key: String) throws -> Bool {
        guard !key.isEmpty else {
            throw SecureStorageError.invalidKey
        }
        
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: serviceName,
            kSecAttrAccount as String: key,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]
        
        let status = SecItemCopyMatching(query as CFDictionary, nil)
        
        if status == errSecSuccess {
            return true
        } else if status == errSecItemNotFound {
            return false
        } else {
            throw SecureStorageError.unexpectedStatus(status)
        }
    }
    
    func clear() throws {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: serviceName
        ]
        
        let status = SecItemDelete(query as CFDictionary)
        
        guard status == errSecSuccess || status == errSecItemNotFound else {
            throw SecureStorageError.unexpectedStatus(status)
        }
    }
    
    func getVersion() throws -> Int {
        if let versionString = try read(key: versionKey),
           let version = Int(versionString) {
            return version
        }
        return 1
    }
    
    func setVersion(version: Int) throws {
        try save(key: versionKey, value: String(version))
    }
}
