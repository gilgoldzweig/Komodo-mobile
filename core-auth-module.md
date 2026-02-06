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
- **iOS Implementation**: Prefer Kotlin/Native implementation using `platform.Security`, `platform.LocalAuthentication`, and `platform.AuthenticationServices` over Swift implementation where possible to keep logic shared.

---

Please implement the code in `:core-auth` module according to the following phased plan:

### Phase 1: Key Management (The Backbone)

*Goal: Handle Public/Private key pairs (SSH/Identity) and provide cryptographic primitives.*

* [ ] **Common-code (`commonMain`)**
  * [ ] **Define Key Interface (`commonMain`)**
  * [ ] Create/Update `interface KeyManager`.
  * [ ] Methods: `generateKeyPair(alias)`, `getPublicKey(alias)`, `signData(alias, data)`, `deleteKey(alias)`, `hasKey(alias)`.
  * [ ] Add support for `exportSshKey(alias): String` (SSH public key format).
  * [ ] Support RSA-4096 (optional) and Ed25519/NIST P-256 (for libssh compatibility).

* [ ] **Android Implementation (`androidMain`)**
  * [ ] **(TDD) Red:** Write Android tests (Robolectric/Instrumented) for `AndroidKeyManager`.
  * [ ] **Implementation:** Implement `AndroidKeyManager` using `AndroidKeyStore`. Ensure keys are Hardware-backed (TEE/StrongBox) where available.
  * [ ] Implement SSH key formatting logic (OpenSSH format).
  * [ ] **(TDD) Green:** Verify Android tests pass.

* [ ] **iOS Implementation (`iosMain`)**
  * [ ] **(TDD) Red:** Write Kotlin/Native tests (`iosTest`) for `IosKeyManager`.
  * [ ] **Implementation:** Implement `IosKeyManager` using `platform.Security` (`SecKey`, `SecItem`).
  * [ ] Implement SSH key formatting logic (OpenSSH format) in Kotlin.
  * [ ] **(TDD) Green:** Verify iOS tests pass.

* [ ] **Koin Integration**
  * [ ] Bind the platform-specific providers to `KeyManager` module.

---

### Phase 2: Secure Storage & Master Key

*Goal: specific secure storage for sensitive data (tokens) and a Master Key for application-level encryption.*

* [ ] **Define Storage Interfaces (`commonMain`)**
  * [ ] `interface SecureStorage` with `save(key, value)`, `read(key)`, `delete(key)`, `contains(key)`.

* [ ] **Master Key Repository (`commonMain`)**
  * [ ] **(TDD) Red:** Write test for `MasterKeyRepository`.
  * [ ] Implement `MasterKeyRepository` that retrieves a stable Master Key.
    *   If key exists in `SecureStorage`, return it.
    *   If not, generate a robust random key, save it to `SecureStorage`, and return it.
  * [ ] **(TDD) Green:** Verify tests pass.

* [ ] **Android Implementation (`androidMain`)**
  * [ ] **(TDD) Red:** Test `AndroidSecureStorage`.
  * [ ] Implement `AndroidSecureStorage` using `EncryptedSharedPreferences` (easiest) or `DataStore` with `Aead` (more modern).
  * [ ] **(TDD) Green:** Verify tests pass.

* [ ] **iOS Implementation (`iosMain`)**
  * [ ] **(TDD) Red:** Test `IosSecureStorage`.
  * [ ] Implement `IosSecureStorage` using `Keychain` (`SecItemAdd`, `SecItemCopyMatching`) via Kotlin/Native cinterop.
  * [ ] Ensure items are stored with `kSecAttrAccessibleWhenUnlocked`.
  * [ ] **(TDD) Green:** Verify tests pass.

---

### Phase 3: Token Management with TTL

*Goal: Securely store session tokens that expire automatically.*

* [ ] **Implement Token Repository (`commonMain`)**
  * [ ] **(TDD) Red:** Write Kotlin test for `TokenRepository` (e.g., `getToken` returns null after TTL).
  * [ ] Use `SecureStorage` (encrypted by platform) to store the token.
  * [ ] Implement `storeToken(token, expiresIn)` and `getToken()` with `Clock.System.now()` validation.
  * [ ] (Optional) Add an encryption layer using `MasterKeyRepository` if platform storage is deemed insufficient (double encryption).

* [ ] **(TDD) Green:** Verify common tests pass using Mokkery/Test Doubles to mock `SecureStorage`.

---

### Phase 4: Passkeys / WebAuthn

*Goal: Support FIDO2 registration and authentication.*

* [ ] **Define Provider Interfaces**
  * [ ] `interface AuthProvider` for `register`(start, finish), `authenticate`(start, finish).

* [ ] **Android Implementation (`androidMain`)**
  * [ ] **(TDD) Exempt:** UI/Integration tests difficult without device.
  * [ ] Integrate **Credential Manager API** (androidx.credentials).

* [ ] **iOS Implementation (`iosMain`)**
  * [ ] **(TDD) Exempt:** UI/Integration tests difficult without device.
  * [ ] Implement `IosAuthProvider` using `platform.AuthenticationServices` (`ASAuthorizationController`).
  * [ ] Handle `ASAuthorizationControllerDelegate` and `ASAuthorizationControllerPresentationContextProviding` in Kotlin/Native.
  * [ ] Inject via Koin into the KMP module.

---

### Phase 5: Quality Assurance & Integration
Go over all implementations and ensure working order, including cross-platform consistency and best practices.

* [ ] **Test Suite Execution**
  * [ ] Run `./gradlew :core-auth:test` (Common).
  * [ ] Run `./gradlew :core-auth:connectedAndroidTest` (Android).
  * [ ] Run `./gradlew :core-auth:iosSimulatorArm64Test` (iOS).

* [ ] **SSH Auth Verification**
  * [ ] Verify that the generated SSH keys from Phase 1 are correctly accepted by a mock `libssh` session or unit test with OpenSSH key parsers.
