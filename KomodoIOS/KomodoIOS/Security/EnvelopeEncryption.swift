import Foundation
import CryptoKit
import Security

/// iOS implementation of envelope encryption using AES-256-GCM with Keychain-protected keys.
///
/// Wraps Ed25519 private keys (or other sensitive data) with a hardware-backed AES key.
/// The master key never leaves the iOS Keychain.
///
/// Wrapped format: [version:1byte][algorithm:1byte][nonce:12bytes][ciphertext][tag:16bytes]
@objc public class EnvelopeEncryptionImpl: NSObject {
    
    private static let versionV1: UInt8 = 0x01
    private static let algorithmAesGcm: UInt8 = 0x01
    private static let nonceLength = 12
    
    /// Generates and stores a new AES-256 master key in the Keychain.
    ///
    /// - Parameter keyAlias: The alias/tag to store the key under
    /// - Throws: EnvelopeError if key generation or storage fails
    @objc public func generateMasterKey(keyAlias: String) throws {
        // Generate a new AES-256 key
        let key = SymmetricKey(size: .bits256)
        let keyData = key.withUnsafeBytes { Data($0) }
        
        // Store in Keychain with device-only accessibility
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: keyAlias,
            kSecAttrService as String: "ca.glong.komodo.envelope",
            kSecValueData as String: keyData,
            kSecAttrAccessible as String: kSecAttrAccessibleWhenUnlockedThisDeviceOnly
        ]
        
        SecItemDelete(query as CFDictionary)
        
        let status = SecItemAdd(query as CFDictionary, nil)
        guard status == errSecSuccess else {
            throw NSError(
                domain: "EnvelopeEncryption",
                code: Int(status),
                userInfo: [NSLocalizedDescriptionKey: "Failed to store master key in Keychain: \(status)"]
            )
        }
    }
    
    /// Encrypts data using AES-256-GCM with a Keychain-protected master key.
    ///
    /// - Parameters:
    ///   - data: The plaintext data to encrypt
    ///   - keyAlias: The alias of the master key in Keychain
    /// - Returns: Encrypted data in wrapped format
    /// - Throws: EnvelopeError if encryption fails
    @objc public func wrap(data: Data, keyAlias: String) throws -> Data {
        let masterKey = try retrieveOrCreateMasterKey(keyAlias: keyAlias)
        
        // Generate a random nonce
        var nonceBytes = [UInt8](repeating: 0, count: Self.nonceLength)
        let nonceStatus = SecRandomCopyBytes(kSecRandomDefault, Self.nonceLength, &nonceBytes)
        guard nonceStatus == errSecSuccess else {
            throw NSError(
                domain: "EnvelopeEncryption",
                code: Int(nonceStatus),
                userInfo: [NSLocalizedDescriptionKey: "Failed to generate random nonce"]
            )
        }
        
        let nonce = try AES.GCM.Nonce(data: Data(nonceBytes))
        
        // Encrypt using AES-GCM
        let sealedBox = try AES.GCM.seal(data, using: masterKey, nonce: nonce)
        
        // Build wrapped format: version + algorithm + nonce + ciphertext + tag
        var wrapped = Data()
        wrapped.append(Self.versionV1)
        wrapped.append(Self.algorithmAesGcm)
        wrapped.append(Data(nonceBytes))
        wrapped.append(sealedBox.ciphertext)
        wrapped.append(sealedBox.tag)
        
        return wrapped
    }
    
    /// Decrypts data that was encrypted with wrap().
    ///
    /// - Parameters:
    ///   - wrappedData: The encrypted data in wrapped format
    ///   - keyAlias: The alias of the master key in Keychain
    /// - Returns: The original plaintext data
    /// - Throws: EnvelopeError if decryption fails or format is invalid
    @objc public func unwrap(wrappedData: Data, keyAlias: String) throws -> Data {
        // Minimum size: version(1) + algorithm(1) + nonce(12) + tag(16) = 30 bytes
        guard wrappedData.count >= 30 else {
            throw NSError(
                domain: "EnvelopeEncryption",
                code: -1,
                userInfo: [NSLocalizedDescriptionKey: "Wrapped data too short"]
            )
        }
        
        // Parse header
        let version = wrappedData[0]
        let algorithm = wrappedData[1]
        
        guard version == Self.versionV1 else {
            throw NSError(
                domain: "EnvelopeEncryption",
                code: -1,
                userInfo: [NSLocalizedDescriptionKey: "Unsupported version: \(version)"]
            )
        }
        
        guard algorithm == Self.algorithmAesGcm else {
            throw NSError(
                domain: "EnvelopeEncryption",
                code: -1,
                userInfo: [NSLocalizedDescriptionKey: "Unsupported algorithm: \(algorithm)"]
            )
        }
        
        // Extract components
        let nonceData = wrappedData.subdata(in: 2..<(2 + Self.nonceLength))
        let ciphertextAndTag = wrappedData.subdata(in: (2 + Self.nonceLength)..<wrappedData.count)
        
        let masterKey = try retrieveOrCreateMasterKey(keyAlias: keyAlias)
        let nonce = try AES.GCM.Nonce(data: nonceData)
        
        // Decrypt using AES-GCM (SealedBox handles ciphertext + tag)
        let sealedBox = try AES.GCM.SealedBox(nonce: nonce, ciphertext: ciphertextAndTag.dropLast(16), tag: ciphertextAndTag.suffix(16))
        let plaintext = try AES.GCM.open(sealedBox, using: masterKey)
        
        return plaintext
    }
    
    // MARK: - Private Helpers
    
    /// Retrieves an existing master key from Keychain or generates a new one.
    private func retrieveOrCreateMasterKey(keyAlias: String) throws -> SymmetricKey {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: keyAlias,
            kSecAttrService as String: "ca.glong.komodo.envelope",
            kSecReturnData as String: true
        ]
        
        var item: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &item)
        
        if status == errSecSuccess, let keyData = item as? Data {
            return SymmetricKey(data: keyData)
        } else if status == errSecItemNotFound {
            // Key doesn't exist, generate and store it
            try generateMasterKey(keyAlias: keyAlias)
            return try retrieveOrCreateMasterKey(keyAlias: keyAlias)
        } else {
            throw NSError(
                domain: "EnvelopeEncryption",
                code: Int(status),
                userInfo: [NSLocalizedDescriptionKey: "Failed to retrieve master key: \(status)"]
            )
        }
    }
}
