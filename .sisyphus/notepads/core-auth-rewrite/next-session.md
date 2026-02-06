# Next Session Priorities

## Immediate Tasks (Wave 2 - Can Parallelize)

### Task 7: Android SecureStorage ⭐ HIGH PRIORITY
**Why First**: Foundation for token storage, blocks Tasks 11, 12
**Complexity**: Medium
**Steps**:
1. Add Tink dependency to gradle
2. Initialize Tink AEAD in AndroidSecureStorage
3. Implement proper save/read with encryption (uncomment and fix existing code)
4. Write comprehensive tests
5. Verify: `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidSecureStorage*"`

### Task 9: Android KeyManager
**Why Next**: Already mostly complete, just needs verification
**Complexity**: Low
**Steps**:
1. Review existing AndroidKeyManager
2. Verify generateKeyPair supports all KeyType variants (RSA_4096, EC_P256, ED25519)
3. Write comprehensive tests
4. Mark complete

### Task 14: Android Passkeys
**Complexity**: High
**Steps**:
1. Implement AuthProvider interface with Credential Manager
2. Test with Passkey creation and assertion

## iOS Tasks (Require Swift)

### Task 6: iOS Envelope Encryption
- Create Swift CryptoKit wrapper for AES.GCM
- Bridge to Kotlin

### Task 8: iOS SecureStorage  
- Implement with Keychain
- Write XCTests

### Task 10: iOS KeyManager
- Implement with Secure Enclave
- Write XCTests

## CommonMain Tasks

### Task 12: TokenRepository
- Depends on SecureStorage implementations (7, 8)
- Implement JWT storage/retrieval logic

### Task 13: SSH Key Export
- Should be simple, uses existing KeyManager.exportSshKey

## Integration Tasks

### Task 11: MasterKeyRepository
- Orchestrates KeyManager + SecureStorage + EnvelopeEncryption

### Tasks 16-18: DI, Migration, E2E Tests
- Final integration

## Current Build Status
✅ `./gradlew :core-auth:assemble` passes
✅ All Android/iOS targets compile
⚠️ Tests not yet written for new implementations

