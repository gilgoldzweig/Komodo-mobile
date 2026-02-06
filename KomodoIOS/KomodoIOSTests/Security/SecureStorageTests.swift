import XCTest
@testable import KomodoIOS

/// Unit tests for SecureStorage iOS Keychain implementation.
///
/// Tests verify:
/// - All CRUD operations (save, read, delete, contains, clear)
/// - Version tracking (get/setVersion)
/// - Error handling for Keychain operations
/// - Edge cases (empty strings, large strings, special characters)
final class SecureStorageTests: XCTestCase {
    
    var storage: SecureStorage!
    
    override func setUp() {
        super.setUp()
        storage = SecureStorage()
        // Clean up any leftover test data
        try? storage.clear()
    }
    
    override func tearDown() {
        try? storage.clear()
        storage = nil
        super.tearDown()
    }
    
    // MARK: - Core Functionality Tests
    
    func testSaveAndRead_success() throws {
        let key = "test_key"
        let value = "test_value"
        
        try storage.save(key: key, value: value)
        let retrieved = try storage.read(key: key)
        
        XCTAssertEqual(retrieved, value, "Retrieved value should match saved value")
    }
    
    func testRead_returnsNilForMissingKey() throws {
        let result = try storage.read(key: "nonexistent_key")
        XCTAssertNil(result, "Reading non-existent key should return nil")
    }
    
    func testDelete_removesKey() throws {
        let key = "test_key"
        try storage.save(key: key, value: "value")
        
        try storage.delete(key: key)
        
        let result = try storage.read(key: key)
        XCTAssertNil(result, "Key should be nil after deletion")
    }
    
    func testDelete_nonExistentKey_doesNotThrow() throws {
        // Deleting non-existent key should succeed silently
        XCTAssertNoThrow(try storage.delete(key: "nonexistent_key"))
    }
    
    func testContains_returnsTrueForExistingKey() throws {
        let key = "test_key"
        try storage.save(key: key, value: "value")
        
        let exists = try storage.contains(key: key)
        XCTAssertTrue(exists, "Contains should return true for existing key")
    }
    
    func testContains_returnsFalseForMissingKey() throws {
        let exists = try storage.contains(key: "nonexistent_key")
        XCTAssertFalse(exists, "Contains should return false for missing key")
    }
    
    func testClear_removesAllEntries() throws {
        try storage.save(key: "key1", value: "value1")
        try storage.save(key: "key2", value: "value2")
        try storage.save(key: "key3", value: "value3")
        
        try storage.clear()
        
        XCTAssertNil(try storage.read(key: "key1"), "key1 should be cleared")
        XCTAssertNil(try storage.read(key: "key2"), "key2 should be cleared")
        XCTAssertNil(try storage.read(key: "key3"), "key3 should be cleared")
    }
    
    // MARK: - Version Tracking Tests
    
    func testGetVersion_defaultsToOne() throws {
        let version = try storage.getVersion()
        XCTAssertEqual(version, 1, "Default version should be 1")
    }
    
    func testSetVersion_updatesVersion() throws {
        let newVersion = 5
        try storage.setVersion(version: newVersion)
        
        let retrieved = try storage.getVersion()
        XCTAssertEqual(retrieved, newVersion, "Version should be updated")
    }
    
    func testSetVersion_persistsAcrossInstances() throws {
        try storage.setVersion(version: 42)
        
        // Create new storage instance (simulates app restart)
        let newStorage = SecureStorage()
        let version = try newStorage.getVersion()
        
        XCTAssertEqual(version, 42, "Version should persist across instances")
        
        try newStorage.clear()
    }
    
    // MARK: - Edge Cases
    
    func testSave_emptyString() throws {
        let key = "empty_key"
        try storage.save(key: key, value: "")
        
        let retrieved = try storage.read(key: key)
        XCTAssertEqual(retrieved, "", "Empty string should be stored and retrieved")
    }
    
    func testSave_largeString() throws {
        let key = "large_key"
        let largeValue = String(repeating: "A", count: 10_000)
        
        try storage.save(key: key, value: largeValue)
        let retrieved = try storage.read(key: key)
        
        XCTAssertEqual(retrieved, largeValue, "Large string should be stored correctly")
    }
    
    func testSave_unicodeCharacters() throws {
        let key = "unicode_key"
        let unicodeValue = "Hello 世界 🔐 Ñoño"
        
        try storage.save(key: key, value: unicodeValue)
        let retrieved = try storage.read(key: key)
        
        XCTAssertEqual(retrieved, unicodeValue, "Unicode characters should be preserved")
    }
    
    func testSave_overwritesExistingKey() throws {
        let key = "overwrite_key"
        try storage.save(key: key, value: "original")
        try storage.save(key: key, value: "updated")
        
        let retrieved = try storage.read(key: key)
        XCTAssertEqual(retrieved, "updated", "New value should overwrite old value")
    }
    
    func testMultipleKeys_coexist() throws {
        try storage.save(key: "key1", value: "value1")
        try storage.save(key: "key2", value: "value2")
        try storage.save(key: "key3", value: "value3")
        
        XCTAssertEqual(try storage.read(key: "key1"), "value1")
        XCTAssertEqual(try storage.read(key: "key2"), "value2")
        XCTAssertEqual(try storage.read(key: "key3"), "value3")
    }
    
    // MARK: - Error Handling Tests
    
    func testSave_invalidKey_throws() {
        // Empty key should throw
        XCTAssertThrowsError(try storage.save(key: "", value: "value")) { error in
            XCTAssertTrue(error is SecureStorageError, "Should throw SecureStorageError")
        }
    }
    
    func testRead_invalidKey_throws() {
        XCTAssertThrowsError(try storage.read(key: "")) { error in
            XCTAssertTrue(error is SecureStorageError, "Should throw SecureStorageError")
        }
    }
    
    // MARK: - Security Properties Tests
    
    func testClear_doesNotAffectOtherApps() throws {
        // SecureStorage uses service identifier to isolate data
        // This test verifies that clear() only removes items for our service
        
        // Save with our service
        try storage.save(key: "app_key", value: "app_value")
        
        // Manually save with different service (simulating another app)
        let otherServiceQuery: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: "com.other.app",
            kSecAttrAccount as String: "other_key",
            kSecValueData as String: "other_value".data(using: .utf8)!,
            kSecAttrAccessible as String: kSecAttrAccessibleWhenUnlocked
        ]
        SecItemAdd(otherServiceQuery as CFDictionary, nil)
        
        // Clear our storage
        try storage.clear()
        
        // Our key should be gone
        XCTAssertNil(try storage.read(key: "app_key"))
        
        // Other service's key should still exist
        var result: CFTypeRef?
        let lookupQuery: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: "com.other.app",
            kSecAttrAccount as String: "other_key",
            kSecReturnData as String: true
        ]
        let status = SecItemCopyMatching(lookupQuery as CFDictionary, &result)
        XCTAssertEqual(status, errSecSuccess, "Other service's data should still exist")
        
        // Clean up
        SecItemDelete(otherServiceQuery as CFDictionary)
    }
    
    func testAccessibility_usesWhenUnlocked() throws {
        // Save a key and verify it uses kSecAttrAccessibleWhenUnlocked
        let key = "accessibility_test"
        try storage.save(key: key, value: "secure_value")
        
        // Query with return attributes
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: "ca.glong.komodo",
            kSecAttrAccount as String: key,
            kSecReturnAttributes as String: true
        ]
        
        var result: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &result)
        
        XCTAssertEqual(status, errSecSuccess)
        
        if let attributes = result as? [String: Any],
           let accessibility = attributes[kSecAttrAccessible as String] as? String {
            XCTAssertEqual(accessibility, kSecAttrAccessibleWhenUnlocked as String,
                          "Should use kSecAttrAccessibleWhenUnlocked protection")
        } else {
            XCTFail("Could not retrieve accessibility attribute")
        }
    }
}
