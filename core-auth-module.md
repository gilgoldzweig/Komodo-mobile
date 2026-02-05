---
title: 'Implementation Plan: High-Security KMP Auth & Cryptography Module'
applyTo: '**'
---

## Goal & Methodology
The goal of this plan is to implement a robust, industry-standard Kotlin Multiplatform (KMP) module for authentication and cryptography. This module will handle master-key storage, Passkeys/WebAuthn, SSH key management, and encrypted token storage across Android and iOS.

**Strict Test-Driven Development (TDD) Requirement:**
To ensure 100% reliability and coverage, this implementation follows a strict TDD cycle. You must adhere to the following workflow for every logic task unless otherwise specified with the "TDD Exempt" at the end of the task description:
1.  **Red:** Write the unit test first. It must fail (either due to missing code or assertion failure).
2.  **Green:** Write the minimum necessary implementation code to make the test pass.
3.  **Refactor:** Optimize the code while ensuring tests remain green.

**Crucial:**
- A checkbox in this plan can **only** be marked as complete after the specific tests for that task have been written, failed, corrected, and successfully passed.
- Use `expect/actual` where platform-specific implementations are required.
- Use `kotlin.Result` for operations that may fail, returning specific error types.
- Use `coroutines` for asynchronous operations and make sure IOS implementations are non-blocking.
- Use Koin & Koin annotations for dependency injection. 
---

Please implement the code in :core-auth module according to the following phased plan:

### Phase 1: Key Management (The Backbone)

*Goal: Handle Public/Private key pairs and the Master Key used to encrypt all subsequent storage.*

* [ ] **Common-code(`commonMain`)**
  * [ ] **Define Key Interfaces (`commonMain`)**
  * [ ] Create `interface NativeKeyProvider`.
  * [ ] Methods: `generateKeyPair(alias, type)`, `signData(alias, data)`, `getMasterKey(): ByteArray`, `exportSshKey(alias): String`.
  * [ ] Support RSA-4096 and Ed25519 (for libssh compatibility).


* [ ] **Android Implementation (`androidMain`)**
  * [ ] Implement using `AndroidKeyStore`. Ensure Master Key is Hardware-backed (TEE/StrongBox). TDD-Exempt (should be ignored as they require device features).
  * [ ] Implement SSH envelope logic (signing/formatting for `libssh`).


* [ ] **iOS Provider Interface (`iosMain`)**
  * [ ] Define `NativeKeyProvider` interface in Kotlin.
  * [ ] **(TDD) Red:** In `KomodoIOS`, write Swift XCTests for a `SwiftKeyProvider` class.
  * [ ] **Swift Implementation:** Implement using `SecKey` (Secure Enclave) and `LocalAuthentication`.
  * [ ] Implement SSH key wrapping/signing logic in Swift.
  * [ ] **(TDD) Green:** Verify Swift tests pass.

* [ ] **Koin Integration**
  * [ ] Bind the platform-specific providers to `NativeKeyProvider`.

---

### Phase 2: Secure Storage with Master Key Encryption

*Goal: Implement storage that uses the Master Key from Phase 1 to encrypt data before persistence.*

* [ ] **Define Storage Interface (`commonMain`)**
* [ ] `interface SecureStorage` with encrypted `save/read/delete`.

* [ ] **Android Implementation (`androidMain`)**
* [ ] **(TDD) Red:** Test that data is unreadable if the Master Key is missing.
* [ ] Use `DataStore` with the Master Key from Phase 1.

* [ ] **iOS Provider Interface (`iosMain`)**
* [ ] Define `NativeStorageProvider` interface.
* [ ] **(TDD) Red:** In `KomodoIOS`, write Swift XCTests for `SwiftStorageProvider`.
* [ ] **Swift Implementation:** Implement using Keychain Services (`kSecClassGenericPassword`).
* [ ] **(TDD) Green:** Verify Swift tests pass.

* [ ] **Encryption Wrapper (`commonMain`)**
* [ ] Implement logic that takes the `NativeKeyProvider.getMasterKey()`, uses it to initialize an AES-GCM cipher, and encrypts payloads before passing them to the `NativeStorageProvider`.
---

### Phase 3: Token Management with TTL

*Goal: Securely store session tokens that expire automatically.*

* [ ] **Implement Token Repository (`commonMain`)**
* [ ] **(TDD) Red:** Write Kotlin test for `getToken` returning null after TTL.
* [ ] Use `SecureStorage` (which is now encrypted via Phase 1 & 2).
* [ ] Implement `storeToken` and `getToken` with `Clock.System.now()` validation.


* [ ] **(TDD) Green:** Verify common tests pass using Mokkery to mock the storage.

---

### Phase 4: Passkeys / WebAuthn

*Goal: Support FIDO2 registration and authentication.*

* [ ] **Define Provider Interfaces**
  * [ ] `interface NativeAuthProvider` for `register`(start, finish), `authenticate`(start, finish), add.


* [ ] **Android Implementation**
  * [ ] Integrate **Credential Manager API**.


* [ ] **iOS Implementation (Swift)**
* [ ] **(TDD) Red:** Swift tests for `ASAuthorizationController` flows.
* [ ] **Swift Implementation:** Implement `ASAuthorizationPlatformPublicKeyCredentialProvider`.
* [ ] Inject via Koin into the KMP module.

---

### Phase 5: Quality Assurance & Integration
Go over all implementations and ensure working order, including cross-platform consistency and best practices.
Make sure code complies with security standards.
Make sure the the code compiles without errors.

* [ ] **Test Suite Execution**
* [ ] Run `./gradlew test` (Android/Common).
* [ ] Execute XCTests in Xcode for all `Native{X}Provider` implementations.


* [ ] **SSH Auth Verification**
* [ ] Verify that the generated SSH keys from Phase 1 are correctly accepted by a mock `libssh` session.
