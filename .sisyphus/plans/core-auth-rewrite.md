# Core-Auth Module Rewrite: Native-First Secure Cryptography

## TL;DR

> **Quick Summary**: Complete rewrite of the `core-auth` KMP module using a native-first architecture where KMP orchestrates interfaces/business logic while Swift (iOS) and Kotlin (Android) handle platform-specific hardware cryptography with envelope encryption for Ed25519 keys.
> 
> **Deliverables**:
> - SKIE integration for KMP↔Swift bridge
> - Ed25519 key management with hardware-wrapped envelope encryption
> - Secure storage with versioning and migration support
> - Token repository with lazy TTL expiration
> - Passkeys/WebAuthn client-side credential management
> - SSH public key export in OpenSSH format
> 
> **Estimated Effort**: Large (multi-week)
> **Parallel Execution**: YES - 3 waves after Phase 0
> **Critical Path**: Phase 0 (SKIE) → Phase 1 (Foundation) → Phase 2 (Keys) → Phase 3 (Storage) → Phase 4 (Tokens) → Phase 5 (Passkeys) → Phase 6 (QA)

---

## Context

### Original Request
User requested review and improvement of the existing `core-auth-module.md` plan. After analysis, decided on complete rewrite with native-first architecture due to:
1. Ed25519 hardware limitations (not supported in Keystore/Secure Enclave)
2. Preference for battle-tested native crypto libraries over KMP cinterop
3. Cleaner separation between orchestration (KMP) and execution (native)

### Interview Summary
**Key Discussions**:
- **Algorithm**: Ed25519 as default, P-256 available for hardware-native operations
- **Envelope Encryption**: Hardware AES256_GCM wraps software Ed25519 private keys
- **Architecture**: KMP orchestrates, Swift/Kotlin execute platform crypto
- **Bridge**: SKIE required for clean KMP↔Swift interop
- **Testing**: Both Swift XCTest AND Kotlin tests required
- **Biometric**: Not required for operations
- **TTL**: Lazy enforcement (check on read)
- **Passkeys**: Client-side only, caller provides challenges
- **Existing Code**: Rewrite from scratch, delete old implementations after

**Research Findings**:
- Ed25519: Software-only on both platforms (Tink on Android, CryptoKit on iOS)
- P-256: Hardware-backed on both platforms
- OpenSSH format: `ssh-ed25519 <base64([4-byte-len:type][4-byte-len:32-byte-pubkey])> comment`
- EncryptedSharedPreferences deprecated → DataStore + Tink AEAD

### Metis Review
**Identified Gaps** (addressed in plan):
1. **Threat Model**: Device-bound only, no cross-device sync; resist sandbox access
2. **Key Lifecycle**: Create, sign, verify, persist wrapped, rehydrate, delete, rotate
3. **Envelope Format**: Version + algorithm IDs + nonce/tag; fail-closed on tamper
4. **Concurrency**: Handle parallel operations safely
5. **Keystore Invalidation**: Handle OS updates, policy changes, reinstalls
6. **Attestation**: OUT OF SCOPE (can add later)
7. **Clock Manipulation**: Use system-provided expiration rather than pure clock comparison

---

## Work Objectives

### Core Objective
Implement a high-security cryptography module with native-first architecture where Ed25519 private keys are envelope-encrypted by hardware-backed AES keys, providing strong protection while maintaining portability.

### Concrete Deliverables
- `KomodoIOS/Sources/Security/` - Swift crypto implementations
- `core-auth/androidMain/` - Android crypto implementations  
- `core-auth/commonMain/` - Interfaces and orchestration
- SKIE configuration in build system
- Comprehensive test suites (XCTest + Kotlin)

### Definition of Done
- [ ] All tests pass: `./gradlew :core-auth:test`, `./gradlew :core-auth:connectedAndroidTest`, Xcode tests
- [ ] Ed25519 keys can be created, sign data, and export SSH format
- [ ] Keys survive app restart with envelope encryption
- [ ] Tokens expire correctly with lazy TTL
- [ ] Passkeys can create credentials and get assertions
- [ ] Old implementations deleted

### Must Have
- Ed25519 key generation with envelope encryption
- Hardware-backed AES master key on both platforms
- SSH public key export (OpenSSH format)
- Secure storage with versioning
- Token TTL with lazy expiration
- Passkey credential management (client-side)
- Error handling via sealed class hierarchy
- Migration infrastructure for future changes

### Must NOT Have (Guardrails)
- ❌ **No custom cryptography** in commonMain - only orchestration/interfaces
- ❌ **No server communication** - caller provides challenges for Passkeys
- ❌ **No biometric UI** - explicit success/failure outcomes only
- ❌ **No private key plaintext persistence** - only wrapped keys stored
- ❌ **No cross-device sync** - device-bound keys only
- ❌ **No attestation** - out of scope (future enhancement)
- ❌ **No root/jailbreak detection** - out of scope
- ❌ **No SSH private key export** (except for software-only keys with explicit user opt-in)
- ❌ **No OpenSSH agent protocol** - export format only

---

## Verification Strategy

> **UNIVERSAL RULE: ZERO HUMAN INTERVENTION**
>
> ALL tasks in this plan MUST be verifiable WITHOUT any human action.

### Test Decision
- **Infrastructure exists**: YES (Gradle test tasks, need to add Xcode test target)
- **Automated tests**: YES (TDD)
- **Frameworks**: 
  - Kotlin: Kotlin Test + Mokkery for mocks
  - Swift: XCTest
  - Android Instrumented: AndroidX Test

### TDD Workflow
Each TODO follows RED-GREEN-REFACTOR unless marked "TDD Exempt":
1. **RED**: Write failing test first
2. **GREEN**: Implement minimum code to pass
3. **REFACTOR**: Clean up while keeping green

### Agent-Executed QA Scenarios
Every task includes agent-verifiable acceptance criteria using:
- **Bash**: Gradle commands, test execution
- **Bash**: Swift tests via `xcodebuild test`
- **Bash**: Verification scripts

---

## Execution Strategy

### Parallel Execution Waves

```
Phase 0 (Sequential - Blocking):
└── Task 1: SKIE Setup (blocks all iOS tasks)

Wave 1 (After Phase 0):
├── Task 2: Error Types (Foundation)
├── Task 3: Key Interfaces (Foundation)  
└── Task 4: Storage Interfaces (Foundation)

Wave 2 (After Wave 1):
├── Task 5: Android Envelope Encryption
├── Task 6: iOS Envelope Encryption (Swift)
├── Task 7: Android SecureStorage
└── Task 8: iOS SecureStorage (Swift)

Wave 3 (After Wave 2):
├── Task 9: Android KeyManager
├── Task 10: iOS KeyManager (Swift)
└── Task 11: MasterKeyRepository

Wave 4 (After Wave 3):
├── Task 12: TokenRepository
└── Task 13: SSH Export

Wave 5 (After Wave 4):
├── Task 14: Android Passkeys
└── Task 15: iOS Passkeys (Swift)

Wave 6 (Final):
├── Task 16: Koin Module Registration
├── Task 17: Integration Tests
└── Task 18: Cleanup Old Code
```

### Dependency Matrix

| Task | Depends On | Blocks | Can Parallelize With |
|------|------------|--------|---------------------|
| 1 (SKIE) | None | 6, 8, 10, 15 | None |
| 2 (Errors) | None | 3, 4 | 3, 4 |
| 3 (Key Interfaces) | 2 | 5, 6, 9, 10 | 4 |
| 4 (Storage Interfaces) | 2 | 7, 8, 11 | 3 |
| 5 (Android Envelope) | 3 | 9 | 6, 7, 8 |
| 6 (iOS Envelope) | 1, 3 | 10 | 5, 7, 8 |
| 7 (Android Storage) | 4 | 11, 12 | 5, 6, 8 |
| 8 (iOS Storage) | 1, 4 | 11, 12 | 5, 6, 7 |
| 9 (Android Keys) | 5 | 12, 13, 14 | 10, 11 |
| 10 (iOS Keys) | 6 | 12, 13, 15 | 9, 11 |
| 11 (MasterKey) | 7, 8 | 12 | 9, 10 |
| 12 (Tokens) | 9, 10, 11 | 17 | 13, 14, 15 |
| 13 (SSH) | 9, 10 | 17 | 12, 14, 15 |
| 14 (Android Passkeys) | 9 | 17 | 12, 13, 15 |
| 15 (iOS Passkeys) | 10 | 17 | 12, 13, 14 |
| 16 (Koin) | 9, 10, 11, 12 | 17 | 14, 15 |
| 17 (Integration) | 16 | 18 | None |
| 18 (Cleanup) | 17 | None | None |

---

## TODOs

### Phase 0: Project Setup

- [ ] 1. **Add SKIE to Project for KMP↔Swift Bridge**

  **What to do**:
  - Create a new Gradle Convention Plugin in `build-logic/` for SKIE following existing patterns
  - Configure SKIE for `core-auth` module to export to `KomodoIOS`
  - Verify Swift can import the KMP framework with SKIE enhancements
  - Document SKIE configuration for future modules

  **Must NOT do**:
  - Don't add SKIE to modules that don't need Swift interop
  - Don't enable unnecessary SKIE features (start minimal)

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Configuration task, well-documented, single module change
  - **Skills**: [`Code Philosophy`]
    - `Code Philosophy`: KMP multi-module architecture guidance

  **Parallelization**:
  - **Can Run In Parallel**: NO
  - **Parallel Group**: Sequential (Phase 0 - blocking)
  - **Blocks**: Tasks 6, 8, 10, 15 (all iOS Swift tasks)
  - **Blocked By**: None (can start immediately)

  **References**:
  - Official docs: https://skie.touchlab.co/intro
  - `build-logic/` - Convention plugins location
  - `KomodoIOS/` - Swift project that will import the framework
  - `core-auth/build.gradle.kts` - Module to configure

  **Acceptance Criteria**:
  - [ ] SKIE plugin added to build system
  - [ ] `./gradlew :core-auth:linkDebugFrameworkIosArm64` succeeds
  - [ ] Swift file in KomodoIOS can `import KomodoCore` (or framework name)
  - [ ] SKIE-enhanced types (sealed classes, suspend functions) accessible from Swift

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: SKIE framework builds successfully
    Tool: Bash
    Preconditions: None
    Steps:
      1. Run: ./gradlew :core-auth:linkDebugFrameworkIosArm64
      2. Assert: Build succeeds (exit code 0)
      3. Assert: Framework exists at expected output path
    Expected Result: iOS framework with SKIE enhancements built
    Evidence: Build output captured

  Scenario: Swift can import SKIE-enhanced framework
    Tool: Bash  
    Preconditions: Framework built from previous scenario
    Steps:
      1. Create test Swift file in KomodoIOS with: import CoreAuth
      2. Run: xcodebuild -project KomodoIOS/KomodoIOS.xcodeproj -scheme KomodoIOS build
      3. Assert: Build succeeds
    Expected Result: Swift project compiles with KMP import
    Evidence: Build output captured
  ```

  **Commit**: YES
  - Message: `build(core-auth): add SKIE for Swift interop`
  - Files: `build-logic/`, `core-auth/build.gradle.kts`, `settings.gradle.kts`
  - Pre-commit: `./gradlew :core-auth:linkDebugFrameworkIosArm64`

---

### Phase 1: Foundation

- [x] 2. **Define AuthError Sealed Class Hierarchy (Replace Existing)**

  **What to do**:
  - **Replace** existing `AuthError.kt` in `core-auth/src/commonMain/.../error/`
  - Current types (`KeyStoreError`, `BioAuthError`, `InvalidKeyFormat`, `StorageError`, `UnknownError`) will be **renamed/restructured**
  - New sealed class with subtypes: `KeyError`, `StorageError`, `TokenError`, `EnvelopeError`, `PasskeyError`
  - Each subtype should have specific error codes and messages
  - Keep all types `@Serializable` for logging/debugging
  - Include factory methods for common error cases
  - **Migration note**: Existing error types map to new ones:
    - `KeyStoreError` → `KeyError.KeystoreUnavailable`
    - `BioAuthError` → Remove (biometric out of scope)
    - `InvalidKeyFormat` → `KeyError.InvalidFormat`
    - `StorageError` → `StorageError` (keep)
    - `UnknownError` → `KeyError.Unknown`, `StorageError.Unknown`, etc.

  **Must NOT do**:
  - Don't include platform-specific error details (wrap them)
  - Don't expose internal exception types

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Data class definitions, no complex logic
  - **Skills**: [`Code Philosophy`]
    - `Code Philosophy`: KMP architecture patterns

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 1 (with Tasks 3, 4)
  - **Blocks**: Tasks 3, 4, 5, 6, 7, 8, 9, 10
  - **Blocked By**: Task 1 (SKIE - for Swift visibility)

  **References**:
  - Existing `core-auth/src/commonMain/kotlin/ca/glong/komodo/core/auth/error/AuthError.kt` - Current implementation to replace
  - Pattern: Use Kotlin sealed classes with `@Serializable`

  **Acceptance Criteria**:
  - [ ] `AuthError` sealed class with 5+ subtypes defined
  - [ ] All types are `@Serializable`
  - [ ] Unit test verifies serialization round-trip
  - [ ] `./gradlew :core-auth:compileKotlinIosArm64` succeeds

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: AuthError compiles for all targets
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:compileKotlinIosArm64 :core-auth:compileKotlinAndroid
      2. Assert: Exit code 0
    Expected Result: Compiles on all platforms
    Evidence: Build output

  Scenario: Error serialization works
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*AuthErrorTest*"
      2. Assert: Tests pass
    Expected Result: Serialization round-trip succeeds
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): define AuthError sealed class hierarchy`
  - Files: `core-auth/src/commonMain/.../error/AuthError.kt`
  - Pre-commit: `./gradlew :core-auth:compileKotlinIosArm64`

---

- [x] 3. **Define Key Management Interfaces**

  **What to do**:
  - Create `KeyManager.kt` interface in `commonMain`
  - Define `KeyAlgorithm` enum: `ED25519`, `P256`, `RSA_4096`
  - Define `KeyMetadata` data class: `alias`, `algorithm`, `createdAt`, `isHardwareBacked`
  - Interface methods: `generateKeyPair`, `getPublicKey`, `signData`, `verifySignature`, `deleteKey`, `hasKey`, `listKeys`, `getKeyMetadata`
  - Define `EnvelopeEncryption` interface for wrapping/unwrapping keys
  - **Define `AuthProvider` interface for Passkeys** with methods:
    - `createCredential(challenge: ByteArray, rpId: String, userId: String): Result<AttestationResponse>`
    - `getAssertion(challenge: ByteArray, rpId: String): Result<AssertionResponse>`
  - Define `AttestationResponse` and `AssertionResponse` data classes (clientDataJSON, attestationObject/signature, etc.)
  - All methods return `Result<T>` with appropriate `AuthError` subtypes

  **Must NOT do**:
  - Don't include implementation details
  - Don't reference platform-specific types

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Interface definitions, no implementation
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 1 (with Tasks 2, 4)
  - **Blocks**: Tasks 5, 6, 9, 10
  - **Blocked By**: Task 2 (AuthError)

  **References**:
  - Existing `core-auth/src/commonMain/.../keys/KeyManager.kt` - Current interface to replace
  - `kotlin.Result` pattern for error handling

  **Acceptance Criteria**:
  - [ ] `KeyManager` interface defined with all methods
  - [ ] `KeyAlgorithm` enum with 3 types
  - [ ] `KeyMetadata` data class
  - [ ] `EnvelopeEncryption` interface defined
  - [ ] `AuthProvider` interface defined with `createCredential` and `getAssertion`
  - [ ] `AttestationResponse` and `AssertionResponse` data classes defined
  - [ ] All return `Result<T>` types
  - [ ] Compiles on all targets

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Interfaces compile on all platforms
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:compileKotlinIosArm64 :core-auth:compileKotlinAndroid :core-auth:compileKotlinJvm
      2. Assert: Exit code 0
    Expected Result: Clean compilation
    Evidence: Build output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): define KeyManager, EnvelopeEncryption, and AuthProvider interfaces`
  - Files: `core-auth/src/commonMain/.../keys/`, `core-auth/src/commonMain/.../passkeys/`
  - Pre-commit: `./gradlew :core-auth:compileKotlinIosArm64`

---

- [x] 4. **Define Storage Interfaces**

  **What to do**:
  - Create `SecureStorage.kt` interface in `commonMain`
  - Methods: `save(key, value)`, `read(key)`, `delete(key)`, `contains(key)`, `clear()`
  - Add versioning support: `getVersion()`, `setVersion(version)`
  - Define `StorageEntry` with metadata (createdAt, version)
  - Create `MigrationManager` interface for schema migrations
  - All methods return `Result<T>`

  **Must NOT do**:
  - Don't include encryption logic (platform handles it)
  - Don't expose raw byte arrays without type safety

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Interface definitions only
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 1 (with Tasks 2, 3)
  - **Blocks**: Tasks 7, 8, 11
  - **Blocked By**: Task 2 (AuthError)

  **References**:
  - Existing `core-auth/src/commonMain/.../storage/SecureStorage.kt`
  - Migration patterns from Android Room

  **Acceptance Criteria**:
  - [ ] `SecureStorage` interface with all methods
  - [ ] Version tracking methods included
  - [ ] `MigrationManager` interface defined
  - [ ] Compiles on all targets

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Storage interfaces compile
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:compileKotlinIosArm64
      2. Assert: Exit code 0
    Expected Result: Clean compilation
    Evidence: Build output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): define SecureStorage and MigrationManager interfaces`
  - Files: `core-auth/src/commonMain/.../storage/`
  - Pre-commit: `./gradlew :core-auth:compileKotlinIosArm64`

---

### Phase 2: Platform Envelope Encryption

- [x] 5. **Implement Android Envelope Encryption**

  **What to do**:
  - **(TDD) Red**: Write tests for `AndroidEnvelopeEncryption`
  - Implement using Android Keystore AES256_GCM
  - Generate hardware-backed master key on first use
  - Wrap/unwrap operations for arbitrary byte arrays
  - Include version byte and algorithm identifier in wrapped output
  - Handle Keystore invalidation gracefully (return error, allow re-generation)
  - **(TDD) Green**: Verify tests pass

  **Must NOT do**:
  - Don't store the master key outside Keystore
  - Don't use deprecated APIs
  - Don't ignore Keystore exceptions

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Security-critical code requiring careful implementation
  - **Skills**: [`android-security-expert`]
    - `android-security-expert`: Keystore management expertise

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 2 (with Tasks 6, 7, 8)
  - **Blocks**: Task 9 (Android KeyManager)
  - **Blocked By**: Task 3 (Key Interfaces)

  **References**:
  - Android Keystore docs: https://developer.android.com/training/articles/keystore
  - `EnvelopeEncryption` interface from Task 3
  - Tink AEAD pattern for reference

  **Acceptance Criteria**:
  - [ ] Test file: `core-auth/src/androidHostTest/.../AndroidEnvelopeEncryptionTest.kt`
  - [ ] Wrap/unwrap round-trip succeeds
  - [ ] Wrapped output includes version byte
  - [ ] Tampered data returns `AuthError.EnvelopeError`
  - [ ] `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidEnvelopeEncryption*"` passes

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Envelope encryption round-trip
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*AndroidEnvelopeEncryptionTest*"
      2. Assert: All tests pass
    Expected Result: Wrap and unwrap work correctly
    Evidence: Test output captured

  Scenario: Tamper detection works
    Tool: Bash
    Steps:
      1. Run test that modifies wrapped bytes then unwraps
      2. Assert: Returns EnvelopeError
    Expected Result: Tampering detected
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement Android envelope encryption with Keystore AES`
  - Files: `core-auth/src/androidMain/.../encryption/`, `core-auth/src/androidHostTest/...`
  - Pre-commit: `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidEnvelopeEncryption*"`

---

- [ ] 6. **Implement iOS Envelope Encryption (Swift)**

  **What to do**:
  - Create `Security/` group in Xcode project under `KomodoIOS/KomodoIOS/`
  - Create `EnvelopeEncryption.swift` in `KomodoIOS/KomodoIOS/Security/`
  - **(TDD) Red**: Write XCTest for envelope operations
  - **Approach**: Use Keychain-protected symmetric key with CryptoKit `AES.GCM`
    - Generate 256-bit AES key using `SymmetricKey(size: .bits256)`
    - Store AES key in Keychain with `kSecAttrAccessibleWhenUnlockedThisDeviceOnly`
    - Use `AES.GCM.seal()` and `AES.GCM.open()` for wrap/unwrap
    - **Note**: Secure Enclave only supports asymmetric P-256, NOT symmetric AES. We use Keychain protection instead, which still provides hardware-backed security via device encryption.
  - Include version byte and algorithm identifier in wrapped output
  - Create KMP-callable wrapper using SKIE exports
  - **(TDD) Green**: Verify XCTests pass

  **Must NOT do**:
  - Don't use Secure Enclave directly for AES (not supported)
  - Don't use `SecKeyCreateEncryptedData` (for asymmetric keys only)
  - Don't ignore Keychain errors

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Security-critical native code
  - **Skills**: [`Code Philosophy`]
    - Note: No iOS security skill available, rely on CryptoKit docs

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 2 (with Tasks 5, 7, 8)
  - **Blocks**: Task 10 (iOS KeyManager)
  - **Blocked By**: Tasks 1 (SKIE), 3 (Key Interfaces)

  **References**:
  - Apple CryptoKit AES.GCM: https://developer.apple.com/documentation/cryptokit/aes/gcm
  - Keychain protection: https://developer.apple.com/documentation/security/keychain_services/keychain_items/restricting_keychain_item_accessibility
  - `KomodoIOS/KomodoIOS/` - Swift source location (create Security/ group)

  **Acceptance Criteria**:
  - [ ] `EnvelopeEncryption.swift` created using CryptoKit `AES.GCM`
  - [ ] AES key stored in Keychain with `kSecAttrAccessibleWhenUnlockedThisDeviceOnly`
  - [ ] XCTest file with wrap/unwrap tests
  - [ ] `xcodebuild test -scheme KomodoIOS -only-testing:KomodoIOSTests/EnvelopeEncryptionTests` passes
  - [ ] KMP can call Swift implementation via SKIE bridge

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: iOS envelope encryption tests pass
    Tool: Bash
    Preconditions: SKIE configured (Task 1 complete)
    Steps:
      1. Run: xcodebuild test -project KomodoIOS/KomodoIOS.xcodeproj -scheme KomodoIOS -destination 'platform=iOS Simulator,name=iPhone 15' -only-testing:KomodoIOSTests/EnvelopeEncryptionTests
      2. Assert: All tests pass
    Expected Result: Swift envelope encryption works
    Evidence: Test output captured
  ```

  **Commit**: YES
  - Message: `feat(KomodoIOS): implement envelope encryption with Keychain AES and CryptoKit`
  - Files: `KomodoIOS/KomodoIOS/Security/EnvelopeEncryption.swift`, `KomodoIOS/Tests/...`
  - Pre-commit: `xcodebuild test ...`

---

- [x] 7. **Implement Android SecureStorage with DataStore + Tink**

  **What to do**:
  - **(TDD) Red**: Write tests for `AndroidSecureStorage`
  - Implement using Jetpack DataStore with Tink AEAD encryption
  - Add version tracking in stored metadata
  - Implement `MigrationManager` for future schema changes
  - Handle data corruption gracefully (return error, don't crash)
  - **(TDD) Green**: Verify tests pass

  **Must NOT do**:
  - Don't use deprecated EncryptedSharedPreferences
  - Don't store plaintext alongside encrypted data

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Storage layer with encryption, needs careful handling
  - **Skills**: [`android-security-expert`]
    - `android-security-expert`: DataStore and encryption patterns

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 2 (with Tasks 5, 6, 8)
  - **Blocks**: Tasks 11 (MasterKey), 12 (Tokens)
  - **Blocked By**: Task 4 (Storage Interfaces)

  **References**:
  - Tink docs: https://developers.google.com/tink
  - DataStore docs: https://developer.android.com/topic/libraries/architecture/datastore
  - `SecureStorage` interface from Task 4

  **Acceptance Criteria**:
  - [ ] `AndroidSecureStorage` implements `SecureStorage` interface
  - [ ] Uses DataStore + Tink AEAD
  - [ ] Version tracking works
  - [ ] `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidSecureStorage*"` passes

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Storage CRUD operations work
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*AndroidSecureStorageTest*"
      2. Assert: All tests pass
    Expected Result: Save, read, delete, contains all work
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement Android secure storage with DataStore + Tink`
  - Files: `core-auth/src/androidMain/.../storage/`
  - Pre-commit: `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidSecureStorage*"`

---

- [ ] 8. **Implement iOS SecureStorage (Swift)**

  **What to do**:
  - Create `SecureStorage.swift` in `KomodoIOS/KomodoIOS/Security/`
  - **(TDD) Red**: Write XCTest for storage operations
  - Use Keychain (`SecItemAdd`, `SecItemCopyMatching`, `SecItemDelete`)
  - Store with `kSecAttrAccessibleWhenUnlocked`
  - Add version attribute to stored items
  - Create KMP-callable wrapper using SKIE
  - **(TDD) Green**: Verify XCTests pass

  **Must NOT do**:
  - Don't use `kSecAttrAccessibleAlways` (insecure)
  - Don't ignore Keychain status codes

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Security-critical Keychain operations
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 2 (with Tasks 5, 6, 7)
  - **Blocks**: Tasks 11 (MasterKey), 12 (Tokens)
  - **Blocked By**: Tasks 1 (SKIE), 4 (Storage Interfaces)

  **References**:
  - Keychain docs: https://developer.apple.com/documentation/security/keychain_services
  - `SecureStorage` interface from Task 4
  - Existing `core-auth/src/iosMain/.../IosSecureStorage.kt` for patterns (to be replaced)

  **Acceptance Criteria**:
  - [ ] `SecureStorage.swift` created
  - [ ] XCTest file with CRUD tests
  - [ ] Keychain operations use proper accessibility
  - [ ] Version tracking works
  - [ ] XCTests pass

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: iOS storage tests pass
    Tool: Bash
    Steps:
      1. Run: xcodebuild test -project KomodoIOS/KomodoIOS.xcodeproj -scheme KomodoIOS -destination 'platform=iOS Simulator,name=iPhone 15' -only-testing:KomodoIOSTests/SecureStorageTests
      2. Assert: All tests pass
    Expected Result: Keychain storage works
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(KomodoIOS): implement secure storage with Keychain`
  - Files: `KomodoIOS/Sources/Security/SecureStorage.swift`
  - Pre-commit: `xcodebuild test ...`

---

### Phase 3: Key Management Implementation

- [ ] 9. **Implement Android KeyManager**

  **What to do**:
  - **(TDD) Red**: Write comprehensive tests for `AndroidKeyManager`
  - Implement Ed25519 key generation using Tink (software)
  - Wrap Ed25519 private key using `AndroidEnvelopeEncryption`
  - Store wrapped key in `AndroidSecureStorage`
  - Implement P-256 as hardware-backed alternative (Keystore native)
  - Implement signing, verification, key listing, metadata
  - **(TDD) Green**: Verify tests pass

  **Must NOT do**:
  - Don't persist Ed25519 private key in plaintext
  - Don't skip envelope encryption step

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Core security functionality with complex state management
  - **Skills**: [`android-security-expert`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 3 (with Tasks 10, 11)
  - **Blocks**: Tasks 12 (Tokens), 13 (SSH), 14 (Passkeys)
  - **Blocked By**: Task 5 (Android Envelope)

  **References**:
  - `KeyManager` interface from Task 3
  - `AndroidEnvelopeEncryption` from Task 5
  - `AndroidSecureStorage` from Task 7
  - Tink Ed25519: https://developers.google.com/tink/generate-digital-signatures

  **Acceptance Criteria**:
  - [ ] Ed25519 key generation works
  - [ ] Private key is envelope-encrypted before storage
  - [ ] Sign/verify round-trip works
  - [ ] P-256 hardware-backed keys work
  - [ ] Key listing and metadata work
  - [ ] `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidKeyManager*"` passes

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Ed25519 key lifecycle
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*AndroidKeyManagerTest*Ed25519*"
      2. Assert: All tests pass (create, sign, verify, delete)
    Expected Result: Full key lifecycle works
    Evidence: Test output

  Scenario: Key survives app restart
    Tool: Bash
    Steps:
      1. Run test that creates key, simulates restart, retrieves key
      2. Assert: Key is retrievable and can sign
    Expected Result: Persistence works
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement Android KeyManager with envelope encryption`
  - Files: `core-auth/src/androidMain/.../keys/`
  - Pre-commit: `./gradlew :core-auth:testDebugUnitTest --tests "*AndroidKeyManager*"`

---

- [ ] 10. **Implement iOS KeyManager (Swift)**

  **What to do**:
  - Create `KeyManager.swift` in `KomodoIOS/KomodoIOS/Security/`
  - **(TDD) Red**: Write XCTests for key operations
  - Implement Ed25519 using CryptoKit `Curve25519.Signing`
  - Wrap Ed25519 private key using Swift `EnvelopeEncryption`
  - Store wrapped key using Swift `SecureStorage`
  - Implement P-256 as Secure Enclave-backed alternative
  - Create KMP-callable wrapper using SKIE
  - **(TDD) Green**: Verify XCTests pass

  **Must NOT do**:
  - Don't persist Ed25519 private key in plaintext
  - Don't bypass envelope encryption

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Core security functionality in Swift
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 3 (with Tasks 9, 11)
  - **Blocks**: Tasks 12 (Tokens), 13 (SSH), 15 (Passkeys)
  - **Blocked By**: Task 6 (iOS Envelope)

  **References**:
  - CryptoKit Curve25519: https://developer.apple.com/documentation/cryptokit/curve25519
  - `EnvelopeEncryption.swift` from Task 6
  - `SecureStorage.swift` from Task 8

  **Acceptance Criteria**:
  - [ ] `KeyManager.swift` created
  - [ ] XCTests for Ed25519 and P-256 keys
  - [ ] Envelope encryption used for Ed25519
  - [ ] KMP can call Swift KeyManager via SKIE
  - [ ] XCTests pass

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: iOS key operations work
    Tool: Bash
    Steps:
      1. Run: xcodebuild test -only-testing:KomodoIOSTests/KeyManagerTests
      2. Assert: All tests pass
    Expected Result: Key lifecycle works on iOS
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(KomodoIOS): implement KeyManager with CryptoKit and envelope encryption`
  - Files: `KomodoIOS/Sources/Security/KeyManager.swift`
  - Pre-commit: `xcodebuild test ...`

---

- [ ] 11. **Implement MasterKeyRepository in CommonMain**

  **What to do**:
  - **(TDD) Red**: Write tests for `MasterKeyRepository`
  - Implement orchestration logic that delegates to platform `SecureStorage`
  - On first call: generate master key via platform, store encrypted
  - On subsequent calls: retrieve from storage
  - Handle storage errors and key invalidation
  - **(TDD) Green**: Verify tests pass with mocked storage

  **Must NOT do**:
  - Don't implement crypto in commonMain
  - Don't expose raw key bytes to callers

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Orchestration logic, delegates to platform
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 3 (with Tasks 9, 10)
  - **Blocks**: Task 12 (Tokens)
  - **Blocked By**: Tasks 7 (Android Storage), 8 (iOS Storage)

  **References**:
  - Existing `core-auth/src/commonMain/.../keys/MasterKeyRepository.kt`
  - `SecureStorage` interface from Task 4

  **Acceptance Criteria**:
  - [ ] `MasterKeyRepository` uses platform storage via DI
  - [ ] Tests pass with mocked storage
  - [ ] Error handling returns appropriate `AuthError`
  - [ ] `./gradlew :core-auth:test` passes

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: MasterKeyRepository tests pass
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*MasterKeyRepository*"
      2. Assert: All tests pass
    Expected Result: Orchestration logic works
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement MasterKeyRepository orchestration`
  - Files: `core-auth/src/commonMain/.../keys/MasterKeyRepository.kt`
  - Pre-commit: `./gradlew :core-auth:testDebugUnitTest --tests "*MasterKeyRepository*"`

---

### Phase 4: Token & SSH Management

- [ ] 12. **Implement TokenRepository with Lazy TTL**

  **What to do**:
  - **(TDD) Red**: Write tests for `TokenRepository`
  - Implement in `commonMain` using `SecureStorage`
  - `storeToken(token, expiresAt)` - stores with expiration timestamp
  - `getToken()` - returns null if expired (lazy check)
  - `clearToken()` - explicit deletion
  - Use `Clock.System.now()` for time comparison
  - Handle clock manipulation by comparing with server-provided expiration
  - **(TDD) Green**: Verify tests pass

  **Must NOT do**:
  - Don't implement background cleanup (lazy only)
  - Don't trust client clock for security decisions (use server expiration)

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Simple business logic with storage delegation
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 4 (with Task 13)
  - **Blocks**: Task 17 (Integration)
  - **Blocked By**: Tasks 9 (Android Keys), 10 (iOS Keys), 11 (MasterKey)

  **References**:
  - `SecureStorage` interface from Task 4
  - `kotlinx-datetime` for `Clock.System`

  **Acceptance Criteria**:
  - [ ] `storeToken`, `getToken`, `clearToken` implemented
  - [ ] Expired token returns null on `getToken`
  - [ ] Token survives app restart
  - [ ] `./gradlew :core-auth:test --tests "*TokenRepository*"` passes

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Token TTL enforcement
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*TokenRepositoryTest*"
      2. Assert: Tests pass including TTL expiration test
    Expected Result: Lazy TTL works correctly
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement TokenRepository with lazy TTL`
  - Files: `core-auth/src/commonMain/.../token/TokenRepository.kt`
  - Pre-commit: `./gradlew :core-auth:testDebugUnitTest --tests "*TokenRepository*"`

---

- [ ] 13. **Implement SSH Public Key Export**

  **What to do**:
  - **(TDD) Red**: Write tests for SSH key formatting
  - Add `exportSshPublicKey(alias): Result<String>` to KeyManager implementations
  - Format Ed25519: `ssh-ed25519 <base64([4-byte-len:"ssh-ed25519"][4-byte-len:32-byte-pubkey])> alias`
  - Format P-256: `ecdsa-sha2-nistp256 <base64(...)> alias`
  - Ensure deterministic output for same key
  - **(TDD) Green**: Verify tests pass

  **Must NOT do**:
  - Don't export private keys (except software-only with explicit method)
  - Don't implement OpenSSH agent protocol

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Well-defined format, straightforward encoding
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 4 (with Task 12)
  - **Blocks**: Task 17 (Integration)
  - **Blocked By**: Tasks 9 (Android Keys), 10 (iOS Keys)

  **References**:
  - OpenSSH key format: RFC 4253, RFC 8709
  - `KeyManager` interface from Task 3

  **Acceptance Criteria**:
  - [ ] Ed25519 SSH format output matches specification
  - [ ] Deterministic: same key = same output
  - [ ] Format is parseable by `ssh-keygen -l -f`
  - [ ] Tests pass on both platforms

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: SSH key format is valid
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*SshKeyFormat*"
      2. Assert: Tests pass
    Expected Result: SSH format correct
    Evidence: Test output

  Scenario: Generated key accepted by ssh-keygen
    Tool: Bash
    Steps:
      1. Run test that outputs SSH key string
      2. Echo to temp file
      3. Run: ssh-keygen -l -f tempfile
      4. Assert: Exit code 0 (valid format)
    Expected Result: ssh-keygen accepts the format
    Evidence: Command output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement SSH public key export in OpenSSH format`
  - Files: `core-auth/src/commonMain/.../keys/SshKeyFormatter.kt`, platform impls
  - Pre-commit: `./gradlew :core-auth:test --tests "*SshKey*"`

---

### Phase 5: Passkeys

- [ ] 14. **Implement Android Passkeys (Credential Manager)**

  **What to do**:
  - **(TDD Exempt)**: Device/UI required for full testing
  - Implement `AndroidPasskeyProvider` using `androidx.credentials.CredentialManager`
  - `createCredential(challenge, rpId, userId)` - registration flow
  - `getAssertion(challenge, rpId)` - authentication flow
  - Return structured results (attestation, assertion) for caller to send to server
  - Define clear error types for user cancellation, no credentials, etc.

  **Must NOT do**:
  - Don't communicate with server (caller handles)
  - Don't store credentials manually (Credential Manager handles)

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Complex Android API integration
  - **Skills**: [`android-security-expert`]
    - `android-security-expert`: Credential Manager expertise

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 5 (with Task 15)
  - **Blocks**: Task 17 (Integration)
  - **Blocked By**: Task 9 (Android KeyManager)

  **References**:
  - Credential Manager: https://developer.android.com/training/sign-in/passkeys
  - WebAuthn spec for response formats

  **Acceptance Criteria**:
  - [ ] `AndroidPasskeyProvider` implements `AuthProvider` interface
  - [ ] Registration returns attestation object
  - [ ] Authentication returns assertion object
  - [ ] Error handling covers common cases
  - [ ] Compiles without errors

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Passkey code compiles
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:compileDebugKotlinAndroid
      2. Assert: Exit code 0
    Expected Result: Clean compilation
    Evidence: Build output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): implement Android Passkeys with Credential Manager`
  - Files: `core-auth/src/androidMain/.../passkeys/`
  - Pre-commit: `./gradlew :core-auth:compileDebugKotlinAndroid`

---

- [ ] 15. **Implement iOS Passkeys (Swift)**

  **What to do**:
  - Create `PasskeyProvider.swift` in `KomodoIOS/KomodoIOS/Security/`
  - **(TDD Exempt)**: Device/UI required for full testing
  - Use `AuthenticationServices` (`ASAuthorizationController`)
  - Handle `ASAuthorizationControllerDelegate` callbacks
  - Return structured results for caller
  - Create KMP-callable wrapper using SKIE

  **Must NOT do**:
  - Don't communicate with server
  - Don't persist passkey data manually

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Complex iOS API with delegate patterns
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 5 (with Task 14)
  - **Blocks**: Task 17 (Integration)
  - **Blocked By**: Task 10 (iOS KeyManager)

  **References**:
  - Apple Passkeys: https://developer.apple.com/documentation/authenticationservices/public-private_key_authentication
  - ASAuthorizationController docs

  **Acceptance Criteria**:
  - [ ] `PasskeyProvider.swift` created
  - [ ] Delegate handling implemented
  - [ ] KMP can call via SKIE
  - [ ] Compiles without errors

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: iOS Passkey code compiles
    Tool: Bash
    Steps:
      1. Run: xcodebuild -project KomodoIOS/KomodoIOS.xcodeproj -scheme KomodoIOS build
      2. Assert: Exit code 0
    Expected Result: Clean compilation
    Evidence: Build output
  ```

  **Commit**: YES
  - Message: `feat(KomodoIOS): implement Passkeys with AuthenticationServices`
  - Files: `KomodoIOS/Sources/Security/PasskeyProvider.swift`
  - Pre-commit: `xcodebuild build ...`

---

### Phase 6: Integration & Cleanup

- [ ] 16. **Register All Components in Koin Module**

  **What to do**:
  - Create new `CoreAuthModule.kt` in `core-auth/src/commonMain/kotlin/ca/glong/komodo/core/auth/di/`
  - Register all platform-specific implementations bound to common interfaces
  - Use `expect/actual` for platform factory functions
  - Update `feature-auth-impl/.../AuthModule.kt` to include `CoreAuthModule`
  - Verify all dependencies resolve correctly

  **Must NOT do**:
  - Don't create circular dependencies
  - Don't expose implementation classes directly

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: Standard DI wiring
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 6 (with Tasks 17, 18 after deps)
  - **Blocks**: Task 17 (Integration)
  - **Blocked By**: Tasks 9, 10, 11, 12

  **References**:
  - `feature-auth-impl/src/commonMain/kotlin/ca/glong/komodo/feature/auth/AuthModule.kt` - Empty module to update
  - Other `*Module.kt` files in feature-*-impl modules for patterns
  - Create new file at `core-auth/src/commonMain/.../di/CoreAuthModule.kt`

  **Acceptance Criteria**:
  - [ ] `CoreAuthModule.kt` created in `core-auth/src/commonMain/.../di/`
  - [ ] All interfaces bound to implementations
  - [ ] `feature-auth-impl/AuthModule.kt` updated to include CoreAuthModule
  - [ ] `./gradlew :core-auth:testDebugUnitTest` passes (DI verification)
  - [ ] No unresolved dependencies at runtime

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: DI graph resolves
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:testDebugUnitTest --tests "*KoinModuleTest*"
      2. Assert: All bindings resolve
    Expected Result: DI works
    Evidence: Test output
  ```

  **Commit**: YES
  - Message: `feat(core-auth): create CoreAuthModule and register all DI bindings`
  - Files: `core-auth/src/commonMain/.../di/CoreAuthModule.kt`, `feature-auth-impl/.../AuthModule.kt`
  - Pre-commit: `./gradlew :core-auth:testDebugUnitTest`

---

- [ ] 17. **Run Full Integration Tests**

  **What to do**:
  - Write integration tests that exercise full flows:
    - Key generation → signing → SSH export
    - Token storage → retrieval → expiration
    - Envelope encryption → persistence → recovery
  - Test concurrency scenarios (parallel sign + delete)
  - Test error recovery scenarios
  - Run all platform test suites

  **Must NOT do**:
  - Don't skip any platform
  - Don't ignore flaky tests (fix them)

  **Recommended Agent Profile**:
  - **Category**: `unspecified-high`
    - Reason: Complex multi-platform testing
  - **Skills**: [`Code Philosophy`]

  **Parallelization**:
  - **Can Run In Parallel**: NO
  - **Parallel Group**: Sequential (after all implementation)
  - **Blocks**: Task 18 (Cleanup)
  - **Blocked By**: Task 16 (Koin)

  **References**:
  - All previous tasks
  - Test patterns in project

  **Acceptance Criteria**:
  - [ ] `./gradlew :core-auth:test` passes
  - [ ] `./gradlew :core-auth:connectedAndroidTest` passes (if CI available)
  - [ ] `xcodebuild test ...` passes all KomodoIOSTests
  - [ ] No flaky tests

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: All tests pass
    Tool: Bash
    Steps:
      1. Run: ./gradlew :core-auth:test
      2. Assert: Exit code 0
      3. Run: xcodebuild test -scheme KomodoIOS -destination 'platform=iOS Simulator,name=iPhone 15'
      4. Assert: Exit code 0
    Expected Result: Full test suite passes
    Evidence: Test reports
  ```

  **Commit**: YES
  - Message: `test(core-auth): add integration tests for full auth flows`
  - Files: `core-auth/src/*/test/...`
  - Pre-commit: `./gradlew :core-auth:test`

---

- [ ] 18. **Cleanup: Delete Old Implementations**

  **What to do**:
  - Delete old files after verifying new implementations work:
    - `core-auth/src/iosMain/.../IosKeyManager.kt`
    - `core-auth/src/iosMain/.../IosSecureStorage.kt`
    - `core-auth/src/androidMain/.../AndroidKeyManager.kt` (old version)
    - `core-auth/src/androidMain/.../AndroidSecureStorage.kt` (old version)
  - Update imports if any external code referenced old classes
  - Verify build still passes after deletion

  **Must NOT do**:
  - Don't delete until all tests pass
  - Don't break external references

  **Recommended Agent Profile**:
  - **Category**: `quick`
    - Reason: File deletion, straightforward
  - **Skills**: [`git-master`]
    - `git-master`: Clean commit for deletions

  **Parallelization**:
  - **Can Run In Parallel**: NO
  - **Parallel Group**: Sequential (final task)
  - **Blocks**: None
  - **Blocked By**: Task 17 (Integration)

  **References**:
  - Files listed in existing `core-auth/src/` structure

  **Acceptance Criteria**:
  - [ ] Old implementation files deleted
  - [ ] `./gradlew :core-auth:test` still passes
  - [ ] No broken imports
  - [ ] Git history preserved (no force push)

  **Agent-Executed QA Scenarios**:

  ```
  Scenario: Build passes after cleanup
    Tool: Bash
    Steps:
      1. Delete old files
      2. Run: ./gradlew :core-auth:test
      3. Assert: Exit code 0
    Expected Result: Clean build
    Evidence: Build output
  ```

  **Commit**: YES
  - Message: `chore(core-auth): remove legacy implementations replaced by native-first architecture`
  - Files: Deleted files
  - Pre-commit: `./gradlew :core-auth:test`

---

## Commit Strategy

| After Task | Message | Key Files |
|------------|---------|-----------|
| 1 | `build(core-auth): add SKIE for Swift interop` | build-logic/, core-auth/build.gradle.kts |
| 2 | `feat(core-auth): define AuthError sealed class hierarchy` | error/AuthError.kt |
| 3 | `feat(core-auth): define KeyManager, EnvelopeEncryption, and AuthProvider interfaces` | keys/*.kt, passkeys/AuthProvider.kt |
| 4 | `feat(core-auth): define SecureStorage and MigrationManager interfaces` | storage/*.kt |
| 5 | `feat(core-auth): implement Android envelope encryption with Keystore AES` | androidMain/encryption/ |
| 6 | `feat(KomodoIOS): implement envelope encryption with Secure Enclave` | KomodoIOS/KomodoIOS/Security/ |
| 7 | `feat(core-auth): implement Android secure storage with DataStore + Tink` | androidMain/storage/ |
| 8 | `feat(KomodoIOS): implement secure storage with Keychain` | KomodoIOS/KomodoIOS/Security/ |
| 9 | `feat(core-auth): implement Android KeyManager with envelope encryption` | androidMain/keys/ |
| 10 | `feat(KomodoIOS): implement KeyManager with CryptoKit and envelope encryption` | KomodoIOS/KomodoIOS/Security/ |
| 11 | `feat(core-auth): implement MasterKeyRepository orchestration` | commonMain/keys/ |
| 12 | `feat(core-auth): implement TokenRepository with lazy TTL` | commonMain/token/ |
| 13 | `feat(core-auth): implement SSH public key export in OpenSSH format` | commonMain/keys/SshKeyFormatter.kt |
| 14 | `feat(core-auth): implement Android Passkeys with Credential Manager` | androidMain/passkeys/ |
| 15 | `feat(KomodoIOS): implement Passkeys with AuthenticationServices` | KomodoIOS/KomodoIOS/Security/ |
| 16 | `feat(core-auth): create CoreAuthModule and register all DI bindings` | core-auth/.../di/CoreAuthModule.kt, feature-auth-impl/.../AuthModule.kt |
| 17 | `test(core-auth): add integration tests for full auth flows` | test files |
| 18 | `chore(core-auth): remove legacy implementations` | deleted files |

---

## Success Criteria

### Verification Commands
```bash
# All common tests
./gradlew :core-auth:test

# Android unit tests
./gradlew :core-auth:testDebugUnitTest

# iOS tests
xcodebuild test -project KomodoIOS/KomodoIOS.xcodeproj -scheme KomodoIOS -destination 'platform=iOS Simulator,name=iPhone 15'

# Full build
./gradlew assembleDebug
```

### Final Checklist
- [ ] SKIE configured and Swift can import KMP framework
- [ ] Ed25519 keys work with envelope encryption on both platforms
- [ ] P-256 keys work with hardware backing on both platforms
- [ ] SSH public key export produces valid OpenSSH format
- [ ] Tokens expire correctly with lazy TTL
- [ ] Passkey flows compile (manual testing required for full verification)
- [ ] All old implementations deleted
- [ ] All tests pass on all platforms
- [ ] No security-sensitive data logged
- [ ] Error handling returns appropriate AuthError types
