# Unresolved Blockers

_This file tracks active blockers that need resolution._

---
## [2026-02-05T23:54:00Z] Task 1: SKIE Setup - BLOCKED

**Issue**: Network resolution failure for Touchlab repository
- SKIE convention plugin exists at `build-logic/convention/src/main/kotlin/ca/glong/komodo/SkieConventionPlugin.kt`
- Plugin registered in `build-logic/convention/build.gradle.kts`
- `core-auth/build.gradle.kts` uses `id("komodo.skie")` convention plugin
- Error: `repo.touchlab.co: nodename nor servname provided, or not known`

**Root Cause**: DNS resolution failure for repo.touchlab.co

**Next Actions**:
1. Skip to tasks 2-4 (Foundation - don't depend on SKIE)
2. Return to Task 1 when network connectivity restored
3. Tasks 6, 8, 10, 15 (iOS Swift tasks) remain blocked until Task 1 complete

## [2026-02-06T00:06:00Z] Pre-Existing Build Issue - Metro Plugin

**Issue**: Build fails on HEAD due to missing metro plugin library reference
- File: `build-logic/convention/build.gradle.kts` line 16
- Error: `Unresolved reference 'metro'`
- Missing: `libs.compile.gradle.plugins.metro` in gradle/libs.versions.toml

**Impact**: Cannot compile or run tests for any module
- Task 5 (Android Envelope Encryption) implementation complete but cannot verify tests
- All subsequent tasks blocked by build failure

**Root Cause**: gradle/libs.versions.toml missing metro plugin in [libraries.compile.gradle.plugins] section
- Metro is defined in [plugins] section but not available for build-logic dependencies

**Resolution Options**:
1. Add metro to libs.versions.toml [libraries.compile.gradle.plugins]
2. Remove metro reference from build-logic/convention/build.gradle.kts if not needed

**Decision**: Add metro to version catalog to unblock builds

## [2026-02-06T00:19:00Z] Task 6: iOS Envelope Encryption - BLOCKED

**Issue**: CryptoKit AES.GCM not available via C interop
- IosEnvelopeEncryption.kt created but encryptAESGCM/decryptAESGCM stubs fail
- CryptoKit requires Swift wrapper with @objc exposure
- Complex to implement without dedicated Swift development

**Decision**: Skip Task 6, return later with Swift wrapper
**Workaround**: Move to Task 7 (Android SecureStorage) - independent task

## [2026-02-06T00:24:00Z] Session End - Remaining Tasks Analysis

**Completed**: 4/18 tasks (22%)

**All Remaining Tasks Blocked By**:
1. **Tink Dependency** (Tasks 7, 9, 14): Requires adding Google Tink to gradle
   - Task 7: Android SecureStorage needs Tink AEAD
   - Task 9: Android KeyManager needs Tink Ed25519
   - Task 14: Android Passkeys needs Credential Manager + Tink

2. **Swift Implementations** (Tasks 6, 8, 10, 15): Require XCode/Swift development
   - Task 6: iOS Envelope with CryptoKit
   - Task 8: iOS SecureStorage with Keychain
   - Task 10: iOS KeyManager with Secure Enclave
   - Task 15: iOS Passkeys

3. **Dependency Chain** (Tasks 11-13, 16-18): Blocked by above tasks
   - Task 11: MasterKeyRepository (needs 5, 6, 7, 8, 9, 10)
   - Task 12: TokenRepository (needs 7, 8)
   - Task 13: SSH Export (needs 9, 10)
   - Tasks 16-18: DI, Migration, Tests (need all above)

**Recommendation**: Next session should:
1. Add Tink dependency first (one-time setup)
2. Complete all Android tasks (7, 9, 14)
3. Then tackle iOS Swift tasks in dedicated iOS development session
4. Finally integration tasks (11-18)


## [2026-02-05] Task 9 - AndroidKeyManager Testing Blocker

**Issue**: AndroidKeyManager implementation is complete but tests fail with "AndroidKeyStore not found"

**Root Cause**: Robolectric doesn't support Android Keystore (hardware security module)

**Errors**:
- Ed25519 key generation fails during envelope encryption (needs AndroidKeyStore master key)
- P-256 key generation fails (needs AndroidKeyStore for hardware-backed keys)

**Options**:
1. Use instrumented tests (`androidTest`) instead of unit tests - requires emulator/device
2. Mock AndroidKeyStore behavior in tests (complex, defeats purpose of testing crypto)
3. Extract keystore operations to interface and provide test doubles
4. Skip unit tests for AndroidKeyManager, rely on integration tests

**Decision**: Task 9 implementation is COMPLETE but tests need instrumented test environment. Mark as "implementation complete, tests require device/emulator".

**Next Steps**: 
- Move tests to `androidTest` sourceset (requires instrumented testing)
- Or accept that crypto code needs device testing
- Focus on tasks that can be unit tested (CommonMain repositories)

## [2026-02-05] Task 17 - Integration Test Blocker

**Issue**: Cannot complete full integration tests in unit test environment

**Reason**: 
- AndroidKeyManager tests fail in Robolectric (AndroidKeyStore not available)
- Integration tests requiring key generation/signing will also fail
- iOS implementations (Tasks 6, 8, 10, 15) not yet implemented

**Current Test Coverage**:
✅ AndroidSecureStorage: 15 tests passing
✅ MasterKeyRepository: 9 tests passing
✅ TokenRepository: 15 tests passing
❌ AndroidKeyManager: Tests fail (Robolectric limitation)
❌ AndroidPasskeyProvider: No tests (TDD exempt per plan)

**Options for Task 17**:
1. Write integration tests that only cover storage/repository layers (exclude key operations)
2. Create instrumented tests (androidTest) requiring device/emulator
3. Mark Task 17 as pending instrumented test environment
4. Accept that crypto operations require device testing

**Decision**: Task 17 requires instrumented test setup. Mark as blocked pending device testing infrastructure.
