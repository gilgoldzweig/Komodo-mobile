# CORE-AUTH KNOWLEDGE BASE

## OVERVIEW
Auth domain and secure storage. `iosMain` contains manual CoreFoundation/Security API usage for Keychain and Secure Enclave interop.

## STRUCTURE
- **commonMain**: Pure-Kotlin interfaces (`SecureStorage`, `KeyManager`) and `MasterKeyRepository`.
- **iosMain**: Platform implementations with manual memory management (SecItem, SecKey).
- **androidMain**: Android-specific security implementations for parity.

## WHERE TO LOOK
- **Memory Hotspots (iOS)**: `core-auth/src/iosMain/kotlin/.../storage/IosSecureStorage.kt` (CFRelease checks).
- **Crypto Logic**: `core-auth/src/iosMain/kotlin/.../keys/IosKeyManager.kt`.
- **Auth Orchestration**: `core-auth/src/commonMain/kotlin/.../keys/MasterKeyRepository.kt`.

## CONVENTIONS
- **Native Interop**: Use `memScoped` and match `CFBridgingRetain` with `CFRelease`.
- **Isolate Interop**: Keep all C-interop logic strictly within `iosMain`.
- **Error Handling**: Use `runCatching` with domain `AuthError` models.

## ANTI-PATTERNS
- **Memory Leaks**: Failing to `CFRelease` after `SecItemCopyMatching` or `SecKey` operations.
- **Leaked Pointers**: Escaping raw CF pointers outside of `memScoped` blocks.
- **Raw Types**: Exposing `SecKeyRef` to common code; convert to `ByteArray` first.
