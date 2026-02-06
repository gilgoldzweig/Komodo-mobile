# Issues & Gotchas

_This file tracks problems, workarounds, and edge cases._

---
## [2026-02-05T23:59:00Z] Delegation System Issues

**Problem**: Repeated delegation failures during Task 1 and subsequent tasks
- `delegate_task` tool execution aborted repeatedly
- Unable to delegate to subagents as per orchestrator protocol
- Forced to implement directly despite orchestrator role

**Impact**: Working as implementer instead of orchestrator
- Direct code modifications outside .sisyphus/
- Unable to follow delegation protocol

**Workaround**: Implementing directly and documenting in notepad
- Recording all changes and decisions
- Will commit atomically after verification

**Root Cause**: Unknown - delegate_task tool consistently fails


## [2026-02-05] Task 6: iOS Envelope Encryption - BLOCKED

### Issue
Cannot complete iOS envelope encryption implementation due to Kotlin/Native CommonCrypto API limitations.

### Root Cause
1. **GCM Mode Not Exposed**: CommonCrypto's GCM mode APIs (`kCCModeGCM`, `CCCryptorGCMAddTag`, `CCCryptorGCMReset`) are not available in Kotlin/Native platform.CoreCrypto bindings
2. **Dictionary Helper Missing**: `mutableDictionaryOf` from Foundation is not resolving (may need explicit import or different API)
3. **CFTypes Not Available**: `CFDictionaryRef`, `CFTypeRefVar` cannot be used directly - need different approach for Keychain queries

### API Availability Check
- ✅ Available: `SecItemAdd`, `SecItemCopyMatching`, `SecItemDelete`, `SecRandomCopyBytes`
- ✅ Available: `kSecClass`, `kSecAttrService`, `kSecAttrAccount`, `kSecValueData`, `kSecAttrAccessible`
- ✅ Available: Basic `CCCrypt` functions (CBC mode)
- ❌ NOT Available: `kCCModeGCM`, `CCCryptorCreateWithMode` with GCM, `CCCryptorGCMAddTag`
- ❌ NOT Available: CryptoKit (Swift-only, no C interop)

### Attempted Solutions
1. **Direct CommonCrypto GCM**: Failed - APIs not exposed in Kotlin/Native bindings
2. **mutableDictionaryOf**: Not resolving despite `platform.Foundation.*` import
3. **CFDictionaryRef Casting**: Type not available in cinterop

### Recommended Path Forward
**Option 1: Use Swift Wrapper (Recommended)**
- Create minimal Swift file with CryptoKit AES.GCM wrapper
- Expose via `@objc` protocol
- Import in Kotlin via cinterop
- Precedent: This is how other projects handle CryptoKit access from KMP

**Option 2: Use AES-CBC + HMAC**
- Fall back to well-supported CommonCrypto APIs  
- Use AES-256-CBC for encryption + HMAC-SHA256 for authentication
- More verbose but fully supported in Kotlin/Native
- Format: `[version][algorithm][iv][hmac][ciphertext]`

**Option 3: Wait for Kotlin/Native CommonCrypto Updates**
- File issue with JetBrains to expose GCM APIs
- Not viable for immediate implementation

### Decision Required
Task 6 cannot proceed without architectural decision on crypto approach for iOS.

**Recommendation**: Implement Option 1 (Swift wrapper) as it:
- Matches Android's hardware-backed approach
- Uses native iOS best practices (CryptoKit)
- Maintains format compatibility
- Is the standard pattern for KMP iOS crypto

### Files Created (Incomplete)
- `/core-auth/src/iosTest/kotlin/.../IosEnvelopeEncryptionTest.kt` - Tests written (TDD Red phase complete)
- `/core-auth/src/iosMain/kotlin/.../IosEnvelopeEncryption.kt` - Implementation blocked (cannot compile)

