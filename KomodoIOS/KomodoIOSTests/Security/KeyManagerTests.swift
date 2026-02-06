import XCTest
@testable import KomodoIOS

/// Comprehensive test suite for KeyManager implementation
/// Covers Ed25519, P-256, key lifecycle, SSH export, and error handling
final class KeyManagerTests: XCTestCase {
    
    var keyManager: KeyManager!
    var secureStorage: SecureStorage!
    var envelopeEncryption: EnvelopeEncryptionImpl!
    
    override func setUp() {
        super.setUp()
        secureStorage = SecureStorage()
        envelopeEncryption = EnvelopeEncryptionImpl()
        keyManager = KeyManager(
            secureStorage: secureStorage,
            envelopeEncryption: envelopeEncryption
        )
        
        // Clean slate for each test
        try? secureStorage.clear()
    }
    
    override func tearDown() {
        try? secureStorage.clear()
        keyManager = nil
        secureStorage = nil
        envelopeEncryption = nil
        super.tearDown()
    }
    
    // MARK: - Ed25519 Tests
    
    func testEd25519KeyGeneration_succeeds() throws {
        let alias = "test_ed25519_key"
        
        let metadata = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        
        XCTAssertEqual(metadata.alias, alias)
        XCTAssertEqual(metadata.algorithm, .ed25519)
        XCTAssertTrue(metadata.isPrivate)
        XCTAssertEqual(metadata.keySize, 256)
        XCTAssertGreaterThan(metadata.createdAt, 0)
    }
    
    func testEd25519PublicKeyRetrieval_succeeds() throws {
        let alias = "test_ed25519_pubkey"
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let publicKey = try keyManager.getPublicKey(alias: alias)
        
        XCTAssertEqual(publicKey.count, 32, "Ed25519 public key must be 32 bytes")
    }
    
    func testEd25519SignVerifyRoundTrip_succeeds() throws {
        let alias = "test_ed25519_sign"
        let message = "Hello, Ed25519!".data(using: .utf8)!
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let signature = try keyManager.signData(alias: alias, data: message)
        let isValid = try keyManager.verifySignature(alias: alias, data: message, signature: signature)
        
        XCTAssertEqual(signature.count, 64, "Ed25519 signature must be 64 bytes")
        XCTAssertTrue(isValid, "Valid signature must verify successfully")
    }
    
    func testEd25519SignVerify_invalidSignatureFails() throws {
        let alias = "test_ed25519_invalid_sig"
        let message = "Hello, Ed25519!".data(using: .utf8)!
        let fakeSignature = Data(repeating: 0, count: 64)
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let isValid = try keyManager.verifySignature(alias: alias, data: message, signature: fakeSignature)
        
        XCTAssertFalse(isValid, "Invalid signature must fail verification")
    }
    
    func testEd25519KeyPersistence_acrossInstances() throws {
        let alias = "test_ed25519_persistence"
        
        // Generate key with first instance
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let pubKey1 = try keyManager.getPublicKey(alias: alias)
        
        // Create new KeyManager instance (simulates app restart)
        let newKeyManager = KeyManager(
            secureStorage: secureStorage,
            envelopeEncryption: envelopeEncryption
        )
        let pubKey2 = try newKeyManager.getPublicKey(alias: alias)
        
        XCTAssertEqual(pubKey1, pubKey2, "Public key must survive KeyManager recreation")
    }
    
    // MARK: - P-256 Tests
    
    func testP256KeyGeneration_succeeds() throws {
        let alias = "test_p256_key"
        
        let metadata = try keyManager.generateKeyPair(alias: alias, algorithm: .p256)
        
        XCTAssertEqual(metadata.alias, alias)
        XCTAssertEqual(metadata.algorithm, .p256)
        XCTAssertTrue(metadata.isPrivate)
        XCTAssertEqual(metadata.keySize, 256)
        XCTAssertGreaterThan(metadata.createdAt, 0)
    }
    
    func testP256SignVerifyRoundTrip_succeeds() throws {
        let alias = "test_p256_sign"
        let message = "Hello, P-256!".data(using: .utf8)!
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .p256)
        let signature = try keyManager.signData(alias: alias, data: message)
        let isValid = try keyManager.verifySignature(alias: alias, data: message, signature: signature)
        
        XCTAssertGreaterThan(signature.count, 0, "P-256 signature must be non-empty")
        XCTAssertTrue(isValid, "Valid P-256 signature must verify successfully")
    }
    
    // MARK: - Key Lifecycle Tests
    
    func testDeleteKey_removesKey() throws {
        let alias = "test_delete_key"
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        XCTAssertTrue(try keyManager.hasKey(alias: alias), "Key must exist after generation")
        
        try keyManager.deleteKey(alias: alias)
        XCTAssertFalse(try keyManager.hasKey(alias: alias), "Key must not exist after deletion")
    }
    
    func testHasKey_returnsTrueForExistingKey() throws {
        let alias = "test_has_key"
        
        XCTAssertFalse(try keyManager.hasKey(alias: alias), "Key must not exist initially")
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        XCTAssertTrue(try keyManager.hasKey(alias: alias), "Key must exist after generation")
    }
    
    func testListKeys_returnsAllKeys() throws {
        let alias1 = "test_list_key_1"
        let alias2 = "test_list_key_2"
        let alias3 = "test_list_key_3"
        
        _ = try keyManager.generateKeyPair(alias: alias1, algorithm: .ed25519)
        _ = try keyManager.generateKeyPair(alias: alias2, algorithm: .p256)
        _ = try keyManager.generateKeyPair(alias: alias3, algorithm: .ed25519)
        
        let keys = try keyManager.listKeys()
        
        XCTAssertEqual(keys.count, 3, "Must return all 3 keys")
        XCTAssertTrue(keys.contains { $0.alias == alias1 })
        XCTAssertTrue(keys.contains { $0.alias == alias2 })
        XCTAssertTrue(keys.contains { $0.alias == alias3 })
    }
    
    func testGetKeyMetadata_returnsCorrectMetadata() throws {
        let alias = "test_metadata"
        
        let created = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let retrieved = try keyManager.getKeyMetadata(alias: alias)
        
        XCTAssertNotNil(retrieved)
        XCTAssertEqual(retrieved?.alias, created.alias)
        XCTAssertEqual(retrieved?.algorithm, created.algorithm)
        XCTAssertEqual(retrieved?.createdAt, created.createdAt)
        XCTAssertEqual(retrieved?.keySize, created.keySize)
    }
    
    // MARK: - SSH Export Tests
    
    func testExportSshPublicKey_producesValidFormat() throws {
        let alias = "test_ssh_export"
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let sshKey = try keyManager.exportSshPublicKey(alias: alias)
        
        XCTAssertTrue(sshKey.hasPrefix("ssh-ed25519 "), "SSH key must start with 'ssh-ed25519 '")
        XCTAssertTrue(sshKey.hasSuffix(" \(alias)"), "SSH key must end with alias comment")
        
        let components = sshKey.split(separator: " ")
        XCTAssertEqual(components.count, 3, "SSH key must have 3 components: type base64 comment")
        
        // Verify base64 encoding is valid
        let base64Part = String(components[1])
        XCTAssertNotNil(Data(base64Encoded: base64Part), "Middle component must be valid Base64")
    }
    
    func testExportSshPublicKey_matchesOpenSSHSpec() throws {
        let alias = "test_ssh_spec"
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        let sshKey = try keyManager.exportSshPublicKey(alias: alias)
        let base64Part = sshKey.split(separator: " ")[1]
        
        guard let decoded = Data(base64Encoded: String(base64Part)) else {
            XCTFail("Failed to decode Base64")
            return
        }
        
        // OpenSSH format: [4-byte-len:"ssh-ed25519"][4-byte-len:32-byte-pubkey]
        XCTAssertGreaterThanOrEqual(decoded.count, 4 + 11 + 4 + 32, "Decoded data must contain type + key")
        
        // Verify type string length prefix (11 bytes = "ssh-ed25519")
        let typeLength = decoded.withUnsafeBytes { $0.load(as: UInt32.self).bigEndian }
        XCTAssertEqual(typeLength, 11, "Type string length must be 11")
        
        // Verify type string
        let typeString = String(data: decoded.subdata(in: 4..<15), encoding: .utf8)
        XCTAssertEqual(typeString, "ssh-ed25519")
        
        // Verify public key length (32 bytes)
        let keyLength = decoded.subdata(in: 15..<19).withUnsafeBytes { $0.load(as: UInt32.self).bigEndian }
        XCTAssertEqual(keyLength, 32, "Ed25519 public key length must be 32")
    }
    
    // MARK: - Error Handling Tests
    
    func testGenerateKeyPair_duplicateAliasFails() throws {
        let alias = "test_duplicate_alias"
        
        _ = try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)
        
        XCTAssertThrowsError(try keyManager.generateKeyPair(alias: alias, algorithm: .ed25519)) { error in
            XCTAssertTrue(error.localizedDescription.contains("exists"), "Error must indicate duplicate key")
        }
    }
    
    func testGetPublicKey_missingKeyFails() throws {
        let alias = "test_missing_key"
        
        XCTAssertThrowsError(try keyManager.getPublicKey(alias: alias)) { error in
            XCTAssertTrue(error.localizedDescription.contains("not found"), "Error must indicate key not found")
        }
    }
    
    func testSignData_missingKeyFails() throws {
        let alias = "test_sign_missing"
        let message = "test".data(using: .utf8)!
        
        XCTAssertThrowsError(try keyManager.signData(alias: alias, data: message)) { error in
            XCTAssertTrue(error.localizedDescription.contains("not found"), "Error must indicate key not found")
        }
    }
}
