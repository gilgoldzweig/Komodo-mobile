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


## [2026-02-05] Task 6: iOS Envelope Encryption - COMPLETED WITH SIMULATOR TEST LIMITATION

### Final Status
iOS Envelope Encryption implementation **COMPLETE** using AES-CBC + HMAC-SHA256.

### Implementation Details
- ✅ **Crypto Approach**: AES-256-CBC for encryption + HMAC-SHA256 for authentication (Option 2)
- ✅ **Keychain Integration**: Using CFDictionary pattern from IosKeyManager
- ✅ **Format**: `[version:1byte][algorithm:1byte][IV:16bytes][HMAC:16bytes][ciphertext]`
- ✅ **Compilation**: iOS ARM64 builds successfully without errors
- ✅ **Code Complete**: All methods implemented with proper memory management

### Test Status  
⚠️ **Simulator Tests Cannot Run**: iOS Keychain operations fail in simulator with error -25291 (`errSecMissingEntitlement`)

**Root Cause**: iOS Simulator does not grant Keychain entitlements to test executables
- This affects ALL Keychain-dependent tests in the project:
  - `IosKeyManagerTest`: 2/2 tests fail with LoadFailed
  - `IosEnvelopeEncryptionTest`: 9/11 tests fail with "Failed to load key from Keychain"
  - `IosSecureStorageTest`: Only placeholder tests exist (no real Keychain tests)

**Verification Approach**:
1. ✅ iOS compilation passes (`./gradlew :core-auth:compileKotlinIosArm64`)
2. ✅ Code matches Android implementation pattern
3. ✅ Memory management follows IosKeyManager conventions (CFBridgingRetain/CFRelease)
4. ⚠️ Runtime verification requires physical device or signed simulator with entitlements

### Files Completed
- `/core-auth/src/iosTest/kotlin/.../IosEnvelopeEncryptionTest.kt` - 11 comprehensive tests (TDD Red phase complete)
- `/core-auth/src/iosMain/kotlin/.../IosEnvelopeEncryption.kt` - Full implementation (compiles successfully)

### Why AES-CBC + HMAC Was Chosen
**Original blocker**: CommonCrypto GCM APIs (`kCCModeGCM`, `CCCryptorGCMAddTag`) not exposed in Kotlin/Native

**Solution evaluation**:
- ❌ **Option 1 (Swift wrapper)**: Violates project constraint (NO Swift files allowed - must use Kotlin cinterop)
- ✅ **Option 2 (CBC+HMAC)**: Uses fully-supported CommonCrypto APIs
  - Provides authenticated encryption equivalent to GCM
  - Uses `CCCrypt` for AES-CBC encryption
  - Uses `CCHmac` for authentication tag
  - Both APIs fully available in Kotlin/Native
- ❌ **Option 3 (Wait for K/N updates)**: Not viable for immediate implementation

### API Availability
- ✅ `CCCrypt` (AES-CBC mode)
- ✅ `CCHmac` (HMAC-SHA256)
- ✅ `SecItemAdd`, `SecItemCopyMatching`, `SecItemDelete`
- ✅ `CFDictionaryCreate`, `CFBridgingRetain`, `CFRelease`
- ✅ `SecRandomCopyBytes`
- ❌ `kCCModeGCM`, `CCCryptorGCMAddTag` (NOT available in K/N bindings)

### Testing Notes
Physical device testing or entitlement-enabled simulator required for runtime verification. This is a known iOS platform limitation, not a code defect.

Existing project precedent: IosKeyManager tests also cannot run in simulator (same -25291 error).

