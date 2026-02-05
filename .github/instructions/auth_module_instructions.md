---
title: 'Implementation Plan: High-Security KMP Auth & Cryptography Module'
applyTo: '**'
---

## Goal & Methodology
The goal of this plan is to implement a robust, industry-standard Kotlin Multiplatform (KMP) module for authentication and cryptography. This module will handle master-key storage, Passkeys/WebAuthn, SSH key management, and encrypted token storage across Android and iOS.

**Strict Test-Driven Development (TDD) Requirement:**
To ensure 100% reliability and coverage, this implementation follows a strict TDD cycle. You must adhere to the following workflow for every logic task:
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


### Phase 1: Project Initialization & Architecture Design
*Goal: Set up the KMP module using Komodo convention plugins and define core contracts.*

- [ ] **Initialize KMP Module**
    - [ ] Create a new module (e.g., `:core:auth`).
    - [ ] Configure `build.gradle.kts` to apply Komodo convention plugins:
        - [ ] Apply `id("komodo.multiplatform")` (Sets up KMP, Serialization, Mokkery, iOS targets).
        - [ ] Apply `id("komodo.android.library")` (Sets up Android target, SDK versions, Namespace).
- [ ] **Define Failure Domains**
    - [ ] Create a sealed hierarchy `AuthError` in `commonMain` to handle specific failures (e.g., `KeyStoreError`, `BioAuthError`, `InvalidKeyFormat`).
- [ ] **Setup Test Coverage Infrastructure**
    - [ ] Verify `Mokkery` is active (applied automatically by `komodo.multiplatform`).

### Phase 2: Secure Storage Abstraction (The Foundation)
*Goal: Abstract platform-specific secure storage (KeyStore vs Keychain) for use in common code.*

- [ ] **Define Storage Interface (`commonMain`)**
    - [ ] Create `interface SecureStorage` with methods: `save(key, value)`, `read(key)`, `delete(key)`, `contains(key)`.
- [ ] **Implement Android Storage (`androidMain`)**
    - [ ] **(TDD) Red:** Write a test mocking DataStore that expects a value save/read.
    - [ ] Implement `SecureStorage` using `EncryptedDataStore`.
    - [ ] Ensure the Master Key for DataStore is generated/retrieved using the Android Keystore System (`MasterKey.Builder`).
    - [ ] **(TDD) Green:** Verify tests pass.
- [ ] **Implement iOS Storage (`iosMain`)**
    - [ ] **(TDD) Red:** Write a test expecting Keychain interactions (mocked).
    - [ ] Implement `SecureStorage` using iOS Keychain Services (`SecItemAdd`, `SecItemCopyMatching`).
    - [ ] **(TDD) Green:** Verify tests pass.
- [ ] **Integration Testing**
    - [ ] Write `commonTest` suites that verify the `SecureStorage` contract (read after write, delete, etc.).

### Phase 3: Key Management (SSH & Master Keys)
*Goal: Handle Public/Private key pairs for future SSH use and internal Master Keys.*

- [ ] **Define Key Manager Interface (`commonMain`)**
    - [ ] Create `expect class KeyManager`.
    - [ ] Define methods: `generateKeyPair(alias, type)`, `importPrivateKey(pem)`, `getPublicKey(alias)`, `signData(alias, data)`.
    - [ ] Support Key Types: RSA-2048/4096 and ECDSA (Ed25519/P-256).
- [ ] **Android Key Implementation (`androidMain`)**
    - [ ] **(TDD) Red:** Write tests asserting correct `KeyPairGenerator` parameter specs (e.g., bit depth, algorithm).
    - [ ] Implement logic using `java.security.KeyPairGenerator` backed by the `AndroidKeyStore` provider.
    - [ ] Ensure private keys are flagged as non-exportable (where applicable for internal master keys) or exportable (for SSH identity files).
    - [ ] **(TDD) Green:** Verify tests pass.
- [ ] **iOS Key Implementation (`iosMain`)**
    - [ ] **(TDD) Red:** Write tests asserting correct `SecKeyCreateRandomKey` attributes.
    - [ ] Implement logic using `SecKeyCreateRandomKey` and the Secure Enclave (where supported).
    - [ ] Implement PEM parsing/formatting for key import/export.
    - [ ] **(TDD) Green:** Verify tests pass.
- [ ] **Master Key Storage**
    - [ ] Implement a specific `MasterKeyRepository` that utilizes `SecureStorage` to hold the application-wide encryption secret.
- [ ] **Unit Tests (100% Coverage)**
    - [ ] Use **Mokkery** to mock the underlying security providers where possible.
    - [ ] Test importing invalid PEM strings triggers correct `AuthError`.

### Phase 4: Token Management with TTL
*Goal: Securely store session tokens that expire automatically.*

- [ ] **Define Token Data Model**
    - [ ] Create data class `SecureToken(id: String, value: String, createdAt: Instant, ttlSeconds: Long)`.
- [ ] **Implement Token Repository (`commonMain`)**
    - [ ] Create `TokenRepository` dependent on `SecureStorage`.
    - [ ] **(TDD) Red:** Write a test for `storeToken` and `getToken` where `getToken` returns `null` if time has passed TTL.
    - [ ] Implement `storeToken(id, value, ttl)`.
    - [ ] Implement `getToken(id)`:
        - [ ] Retrieve data from storage.
        - [ ] Decrypt (handled by storage layer).
        - [ ] Check `Clock.System.now()` against `createdAt + ttl`.
        - [ ] Return null and delete data if expired.
    - [ ] **(TDD) Green:** Verify tests pass.
- [ ] **Unit Tests (100% Coverage)**
    - [ ] Use **Mokkery** to mock `SecureStorage`.
    - [ ] `every { storage.read(...) } returns ...` (Mokkery syntax).
    - [ ] Test retrieval of valid vs. expired tokens.

### Phase 5: Passkeys / WebAuthn Implementation
*Goal: Support FIDO2 registration and authentication flows.*

- [ ] **Define WebAuthn Interface (`commonMain`)**
    - [ ] `expect class PasskeyAuthenticator`.
    - [ ] Define methods: `register(challenge, rpId, userId)`, `authenticate(challenge, rpId)`.
- [ ] **Android Implementation (`androidMain`)**
    - [ ] Integrate **Credential Manager API** (replacing FIDO2 API).
    - [ ] Map `CreatePublicKeyCredentialRequest` to the common interface.
- [ ] **iOS Implementation (`iosMain`)**
    - [ ] Integrate `ASAuthorizationController` (Authentication Services).
    - [ ] Implement `ASAuthorizationPlatformPublicKeyCredentialProvider` logic.
- [ ] **Unit Tests**
    - [ ] Use **Mokkery** to mock platform responses where possible, ensuring FIDO2 JSON payloads are correctly serialized.

### Phase 6: Quality Assurance & Final Polish
*Goal: Ensure the "All tests must pass" and "100% coverage" requirements are met.*

- [ ] **Static Analysis**
    - [ ] Run `ktlint` or `detekt` (ensure compliance with Komodo style).
- [ ] **Unit Test Audit**
    - [ ] Verify every `if/else` branch in `TokenRepository` is hit.
    - [ ] Verify every exception in `KeyManager` is triggered in a test case.
    - [ ] Ensure Mokkery mocks are strict (fail on unexpected calls).
- [ ] **Platform Test Execution**
    - [ ] Run `./gradlew connectedAndroidTest`.
    - [ ] Run `./gradlew iosSimulatorArm64Test`.