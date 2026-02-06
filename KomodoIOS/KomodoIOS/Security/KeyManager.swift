import Foundation
import CryptoKit
import Security

enum KeyAlgorithm: String, Codable {
    case ed25519 = "ED25519"
    case p256 = "P256"
    case rsa4096 = "RSA_4096"
}

struct KeyMetadata: Codable {
    let alias: String
    let algorithm: KeyAlgorithm
    let createdAt: Int64
    let isPrivate: Bool
    let keySize: Int
    let metadata: [String: String]
}

enum KeyManagerError: Error {
    case keyAlreadyExists(String)
    case keyNotFound(String)
    case generationFailed(String)
    case loadFailed(String)
    case signingFailed(String)
    case verificationFailed(String)
    case invalidFormat(String)
    case envelopeError(String)
    case storageError(String)
}

public class KeyManager {
    
    private let secureStorage: SecureStorage
    private let envelopeEncryption: EnvelopeEncryptionImpl
    
    private static let metadataSuffix = "_metadata"
    private static let publicKeySuffix = "_public"
    
    public init(secureStorage: SecureStorage, envelopeEncryption: EnvelopeEncryptionImpl) {
        self.secureStorage = secureStorage
        self.envelopeEncryption = envelopeEncryption
    }
    
    public func generateKeyPair(alias: String, algorithm: KeyAlgorithm) throws -> KeyMetadata {
        if try secureStorage.contains(key: alias) {
            throw KeyManagerError.keyAlreadyExists("Key with alias '\(alias)' already exists")
        }
        
        let createdAt = Int64(Date().timeIntervalSince1970 * 1000)
        
        switch algorithm {
        case .ed25519:
            return try generateEd25519KeyPair(alias: alias, createdAt: createdAt)
        case .p256:
            return try generateP256KeyPair(alias: alias, createdAt: createdAt)
        case .rsa4096:
            throw KeyManagerError.generationFailed("RSA_4096 not yet implemented")
        }
    }
    
    public func getPublicKey(alias: String) throws -> Data {
        let publicKeyAlias = alias + Self.publicKeySuffix
        
        guard let publicKeyHex = try secureStorage.read(key: publicKeyAlias) else {
            throw KeyManagerError.keyNotFound("Public key not found for alias '\(alias)'")
        }
        
        guard let publicKeyData = Data(hexString: publicKeyHex) else {
            throw KeyManagerError.invalidFormat("Invalid hex format for public key '\(alias)'")
        }
        
        return publicKeyData
    }
    
    public func signData(alias: String, data: Data) throws -> Data {
        guard let metadataString = try secureStorage.read(key: alias + Self.metadataSuffix),
              let metadataData = metadataString.data(using: .utf8),
              let metadata = try? JSONDecoder().decode(KeyMetadata.self, from: metadataData) else {
            throw KeyManagerError.keyNotFound("Key metadata not found for alias '\(alias)'")
        }
        
        switch metadata.algorithm {
        case .ed25519:
            return try signEd25519(alias: alias, data: data)
        case .p256:
            return try signP256(alias: alias, data: data)
        case .rsa4096:
            throw KeyManagerError.signingFailed("RSA_4096 not yet implemented")
        }
    }
    
    public func verifySignature(alias: String, data: Data, signature: Data) throws -> Bool {
        guard let metadataString = try secureStorage.read(key: alias + Self.metadataSuffix),
              let metadataData = metadataString.data(using: .utf8),
              let metadata = try? JSONDecoder().decode(KeyMetadata.self, from: metadataData) else {
            throw KeyManagerError.keyNotFound("Key metadata not found for alias '\(alias)'")
        }
        
        switch metadata.algorithm {
        case .ed25519:
            return try verifyEd25519(alias: alias, data: data, signature: signature)
        case .p256:
            return try verifyP256(alias: alias, data: data, signature: signature)
        case .rsa4096:
            throw KeyManagerError.verificationFailed("RSA_4096 not yet implemented")
        }
    }
    
    public func deleteKey(alias: String) throws {
        try secureStorage.delete(key: alias)
        try secureStorage.delete(key: alias + Self.metadataSuffix)
        try secureStorage.delete(key: alias + Self.publicKeySuffix)
    }
    
    public func hasKey(alias: String) throws -> Bool {
        return try secureStorage.contains(key: alias)
    }
    
    public func listKeys() throws -> [KeyMetadata] {
        var keys: [KeyMetadata] = []
        
        let allKeys = try getAllStorageKeys()
        let metadataKeys = allKeys.filter { $0.hasSuffix(Self.metadataSuffix) }
        
        for metadataKey in metadataKeys {
            if let metadataString = try secureStorage.read(key: metadataKey),
               let metadataData = metadataString.data(using: .utf8),
               let metadata = try? JSONDecoder().decode(KeyMetadata.self, from: metadataData) {
                keys.append(metadata)
            }
        }
        
        return keys
    }
    
    public func getKeyMetadata(alias: String) throws -> KeyMetadata? {
        let metadataAlias = alias + Self.metadataSuffix
        
        guard let metadataString = try secureStorage.read(key: metadataAlias),
              let metadataData = metadataString.data(using: .utf8),
              let metadata = try? JSONDecoder().decode(KeyMetadata.self, from: metadataData) else {
            return nil
        }
        
        return metadata
    }
    
    public func exportSshPublicKey(alias: String) throws -> String {
        let publicKey = try getPublicKey(alias: alias)
        
        guard let metadataString = try secureStorage.read(key: alias + Self.metadataSuffix),
              let metadataData = metadataString.data(using: .utf8),
              let metadata = try? JSONDecoder().decode(KeyMetadata.self, from: metadataData) else {
            throw KeyManagerError.keyNotFound("Key metadata not found for alias '\(alias)'")
        }
        
        switch metadata.algorithm {
        case .ed25519:
            return try exportSshEd25519(publicKey: publicKey, alias: alias)
        case .p256:
            throw KeyManagerError.invalidFormat("P-256 SSH export not yet implemented")
        case .rsa4096:
            throw KeyManagerError.invalidFormat("RSA_4096 SSH export not yet implemented")
        }
    }
    
    private func generateEd25519KeyPair(alias: String, createdAt: Int64) throws -> KeyMetadata {
        let privateKey = Curve25519.Signing.PrivateKey()
        let publicKey = privateKey.publicKey
        
        let privateKeyData = privateKey.rawRepresentation
        let publicKeyData = publicKey.rawRepresentation
        
        let wrappedPrivateKey = try envelopeEncryption.wrap(data: privateKeyData, keyAlias: alias)
        
        try secureStorage.save(key: alias, value: wrappedPrivateKey.base64EncodedString())
        try secureStorage.save(key: alias + Self.publicKeySuffix, value: publicKeyData.hexString)
        
        let metadata = KeyMetadata(
            alias: alias,
            algorithm: .ed25519,
            createdAt: createdAt,
            isPrivate: true,
            keySize: 256,
            metadata: ["type": "software"]
        )
        
        try saveMetadata(metadata: metadata)
        
        return metadata
    }
    
    private func generateP256KeyPair(alias: String, createdAt: Int64) throws -> KeyMetadata {
        let privateKey = P256.Signing.PrivateKey()
        let publicKey = privateKey.publicKey
        
        let privateKeyData = privateKey.rawRepresentation
        let publicKeyData = publicKey.x963Representation
        
        let wrappedPrivateKey = try envelopeEncryption.wrap(data: privateKeyData, keyAlias: alias)
        
        try secureStorage.save(key: alias, value: wrappedPrivateKey.base64EncodedString())
        try secureStorage.save(key: alias + Self.publicKeySuffix, value: publicKeyData.hexString)
        
        let metadata = KeyMetadata(
            alias: alias,
            algorithm: .p256,
            createdAt: createdAt,
            isPrivate: true,
            keySize: 256,
            metadata: ["type": "secure_enclave_compatible"]
        )
        
        try saveMetadata(metadata: metadata)
        
        return metadata
    }
    
    private func signEd25519(alias: String, data: Data) throws -> Data {
        let privateKeyData = try loadPrivateKey(alias: alias)
        
        guard let privateKey = try? Curve25519.Signing.PrivateKey(rawRepresentation: privateKeyData) else {
            throw KeyManagerError.loadFailed("Failed to recreate Ed25519 private key from unwrapped data")
        }
        
        let signature = try privateKey.signature(for: data)
        return signature
    }
    
    private func signP256(alias: String, data: Data) throws -> Data {
        let privateKeyData = try loadPrivateKey(alias: alias)
        
        guard let privateKey = try? P256.Signing.PrivateKey(rawRepresentation: privateKeyData) else {
            throw KeyManagerError.loadFailed("Failed to recreate P-256 private key from unwrapped data")
        }
        
        let signature = try privateKey.signature(for: data)
        return signature.derRepresentation
    }
    
    private func verifyEd25519(alias: String, data: Data, signature: Data) throws -> Bool {
        let publicKey = try getPublicKey(alias: alias)
        
        guard let ed25519PublicKey = try? Curve25519.Signing.PublicKey(rawRepresentation: publicKey) else {
            throw KeyManagerError.loadFailed("Failed to load Ed25519 public key")
        }
        
        return ed25519PublicKey.isValidSignature(signature, for: data)
    }
    
    private func verifyP256(alias: String, data: Data, signature: Data) throws -> Bool {
        let publicKey = try getPublicKey(alias: alias)
        
        guard let p256PublicKey = try? P256.Signing.PublicKey(x963Representation: publicKey) else {
            throw KeyManagerError.loadFailed("Failed to load P-256 public key")
        }
        
        guard let p256Signature = try? P256.Signing.ECDSASignature(derRepresentation: signature) else {
            throw KeyManagerError.verificationFailed("Invalid DER signature format for P-256")
        }
        
        return p256PublicKey.isValidSignature(p256Signature, for: data)
    }
    
    private func loadPrivateKey(alias: String) throws -> Data {
        guard let wrappedBase64 = try secureStorage.read(key: alias) else {
            throw KeyManagerError.keyNotFound("Private key not found for alias '\(alias)'")
        }
        
        guard let wrappedData = Data(base64Encoded: wrappedBase64) else {
            throw KeyManagerError.invalidFormat("Invalid Base64 encoding for wrapped key '\(alias)'")
        }
        
        let privateKeyData = try envelopeEncryption.unwrap(wrappedData: wrappedData, keyAlias: alias)
        return privateKeyData
    }
    
    private func saveMetadata(metadata: KeyMetadata) throws {
        let encoder = JSONEncoder()
        let metadataData = try encoder.encode(metadata)
        
        guard let metadataString = String(data: metadataData, encoding: .utf8) else {
            throw KeyManagerError.storageError("Failed to encode metadata as UTF-8 string")
        }
        
        try secureStorage.save(key: metadata.alias + Self.metadataSuffix, value: metadataString)
    }
    
    private func exportSshEd25519(publicKey: Data, alias: String) throws -> String {
        let keyType = "ssh-ed25519"
        
        var sshBlob = Data()
        
        let keyTypeData = keyType.data(using: .utf8)!
        var keyTypeLength = UInt32(keyTypeData.count).bigEndian
        sshBlob.append(Data(bytes: &keyTypeLength, count: 4))
        sshBlob.append(keyTypeData)
        
        var publicKeyLength = UInt32(publicKey.count).bigEndian
        sshBlob.append(Data(bytes: &publicKeyLength, count: 4))
        sshBlob.append(publicKey)
        
        let base64Encoded = sshBlob.base64EncodedString()
        
        return "\(keyType) \(base64Encoded) \(alias)"
    }
    
    private func getAllStorageKeys() throws -> [String] {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: "ca.glong.komodo",
            kSecReturnAttributes as String: true,
            kSecMatchLimit as String: kSecMatchLimitAll
        ]
        
        var result: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &result)
        
        if status == errSecItemNotFound {
            return []
        }
        
        guard status == errSecSuccess else {
            throw KeyManagerError.storageError("Failed to list keys: \(status)")
        }
        
        guard let items = result as? [[String: Any]] else {
            return []
        }
        
        let keys = items.compactMap { item -> String? in
            return item[kSecAttrAccount as String] as? String
        }
        
        return keys
    }
}

extension Data {
    var hexString: String {
        return map { String(format: "%02hhx", $0) }.joined()
    }
    
    init?(hexString: String) {
        let length = hexString.count / 2
        var data = Data(capacity: length)
        
        for i in 0..<length {
            let startIndex = hexString.index(hexString.startIndex, offsetBy: i * 2)
            let endIndex = hexString.index(startIndex, offsetBy: 2)
            let byteString = hexString[startIndex..<endIndex]
            
            guard let byte = UInt8(byteString, radix: 16) else {
                return nil
            }
            
            data.append(byte)
        }
        
        self = data
    }
}
