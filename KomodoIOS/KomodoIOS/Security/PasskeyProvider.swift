import Foundation
import AuthenticationServices

/// iOS implementation of WebAuthn passkey operations using AuthenticationServices.
///
/// This provider handles passkey registration and authentication flows using
/// iOS platform passkey APIs. The implementation wraps asynchronous ASAuthorizationController
/// operations in synchronous methods for compatibility with the KMP bridge.
///
/// **WebAuthn Compliance:**
/// - Registration: Creates platform authenticator credentials with user verification
/// - Authentication: Retrieves assertions from stored credentials
/// - Response Format: Matches WebAuthn Level 2 specification
@objc public class PasskeyProvider: NSObject {
    
    // MARK: - Response Types
    
    /// Attestation response returned after successful credential creation.
    @objc public class AttestationResponse: NSObject {
        @objc public let id: String
        @objc public let rawId: Data
        @objc public let clientDataJSON: Data
        @objc public let attestationObject: Data
        @objc public let type: String
        
        public init(id: String, rawId: Data, clientDataJSON: Data, attestationObject: Data, type: String = "public-key") {
            self.id = id
            self.rawId = rawId
            self.clientDataJSON = clientDataJSON
            self.attestationObject = attestationObject
            self.type = type
            super.init()
        }
    }
    
    /// Assertion response returned after successful authentication.
    @objc public class AssertionResponse: NSObject {
        @objc public let id: String
        @objc public let rawId: Data
        @objc public let clientDataJSON: Data
        @objc public let authenticatorData: Data
        @objc public let signature: Data
        @objc public let userHandle: Data?
        @objc public let type: String
        
        public init(id: String, rawId: Data, clientDataJSON: Data, authenticatorData: Data, signature: Data, userHandle: Data?, type: String = "public-key") {
            self.id = id
            self.rawId = rawId
            self.clientDataJSON = clientDataJSON
            self.authenticatorData = authenticatorData
            self.signature = signature
            self.userHandle = userHandle
            self.type = type
            super.init()
        }
    }
    
    // MARK: - Error Types
    
    enum PasskeyProviderError: Error {
        case userCancelled
        case noCredentials
        case operationFailed(String)
        case presentationContextUnavailable
        case invalidResponse
    }
    
    // MARK: - Private State
    
    private var currentCompletion: ((Result<Any, Error>) -> Void)?
    private var presentationContext: ASPresentationAnchor?
    
    // MARK: - Public API
    
    /// Creates a new passkey credential for the user.
    ///
    /// This initiates a WebAuthn registration flow using iOS platform authenticator.
    /// The user will be prompted for biometric authentication.
    ///
    /// - Parameters:
    ///   - challenge: Server-generated challenge bytes
    ///   - rpId: Relying party identifier (domain)
    ///   - userId: User identifier string
    /// - Returns: AttestationResponse containing credential details
    /// - Throws: PasskeyProviderError on failure or cancellation
    @objc public func createCredential(challenge: Data, rpId: String, userId: String) throws -> AttestationResponse {
        let semaphore = DispatchSemaphore(value: 0)
        var result: Result<AttestationResponse, Error>?
        
        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                result = .failure(PasskeyProviderError.operationFailed("Provider deallocated"))
                semaphore.signal()
                return
            }
            
            do {
                try self.performCreateCredential(challenge: challenge, rpId: rpId, userId: userId) { response in
                    result = response
                    semaphore.signal()
                }
            } catch {
                result = .failure(error)
                semaphore.signal()
            }
        }
        
        semaphore.wait()
        
        switch result {
        case .success(let response):
            return response
        case .failure(let error):
            throw error
        case .none:
            throw PasskeyProviderError.operationFailed("No result received")
        }
    }
    
    /// Authenticates using an existing passkey credential.
    ///
    /// This initiates a WebAuthn authentication flow. The user will be prompted
    /// to select and authenticate with a stored credential.
    ///
    /// - Parameters:
    ///   - challenge: Server-generated challenge bytes
    ///   - rpId: Relying party identifier (domain)
    /// - Returns: AssertionResponse containing signature and authenticator data
    /// - Throws: PasskeyProviderError on failure or cancellation
    @objc public func getAssertion(challenge: Data, rpId: String) throws -> AssertionResponse {
        let semaphore = DispatchSemaphore(value: 0)
        var result: Result<AssertionResponse, Error>?
        
        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                result = .failure(PasskeyProviderError.operationFailed("Provider deallocated"))
                semaphore.signal()
                return
            }
            
            do {
                try self.performGetAssertion(challenge: challenge, rpId: rpId) { response in
                    result = response
                    semaphore.signal()
                }
            } catch {
                result = .failure(error)
                semaphore.signal()
            }
        }
        
        semaphore.wait()
        
        switch result {
        case .success(let response):
            return response
        case .failure(let error):
            throw error
        case .none:
            throw PasskeyProviderError.operationFailed("No result received")
        }
    }
    
    // MARK: - Private Implementation
    
    private func performCreateCredential(
        challenge: Data,
        rpId: String,
        userId: String,
        completion: @escaping (Result<AttestationResponse, Error>) -> Void
    ) throws {
        let provider = ASAuthorizationPlatformPublicKeyCredentialProvider(relyingPartyIdentifier: rpId)
        
        let userIdData = userId.data(using: .utf8) ?? Data()
        let registrationRequest = provider.createCredentialRegistrationRequest(
            challenge: challenge,
            name: userId,
            userID: userIdData
        )
        
        let controller = ASAuthorizationController(authorizationRequests: [registrationRequest])
        controller.delegate = self
        controller.presentationContextProvider = self
        
        self.currentCompletion = { result in
            switch result {
            case .success(let value):
                if let response = value as? AttestationResponse {
                    completion(.success(response))
                } else {
                    completion(.failure(PasskeyProviderError.invalidResponse))
                }
            case .failure(let error):
                completion(.failure(error))
            }
        }
        
        controller.performRequests()
    }
    
    private func performGetAssertion(
        challenge: Data,
        rpId: String,
        completion: @escaping (Result<AssertionResponse, Error>) -> Void
    ) throws {
        let provider = ASAuthorizationPlatformPublicKeyCredentialProvider(relyingPartyIdentifier: rpId)
        
        let assertionRequest = provider.createCredentialAssertionRequest(challenge: challenge)
        
        let controller = ASAuthorizationController(authorizationRequests: [assertionRequest])
        controller.delegate = self
        controller.presentationContextProvider = self
        
        self.currentCompletion = { result in
            switch result {
            case .success(let value):
                if let response = value as? AssertionResponse {
                    completion(.success(response))
                } else {
                    completion(.failure(PasskeyProviderError.invalidResponse))
                }
            case .failure(let error):
                completion(.failure(error))
            }
        }
        
        controller.performRequests()
    }
    
    private func parseRegistrationResponse(_ registration: ASAuthorizationPlatformPublicKeyCredentialRegistration) throws -> AttestationResponse {
        let credentialId = registration.credentialID
        let rawClientDataJSON = registration.rawClientDataJSON
        let rawAttestationObject = registration.rawAttestationObject ?? Data()
        
        // Convert credentialID to Base64URL string (WebAuthn id field)
        let idString = credentialId.base64URLEncodedString()
        
        return AttestationResponse(
            id: idString,
            rawId: credentialId,
            clientDataJSON: rawClientDataJSON,
            attestationObject: rawAttestationObject,
            type: "public-key"
        )
    }
    
    private func parseAssertionResponse(_ assertion: ASAuthorizationPlatformPublicKeyCredentialAssertion) throws -> AssertionResponse {
        let credentialId = assertion.credentialID
        let rawClientDataJSON = assertion.rawClientDataJSON
        
        guard let rawAuthenticatorData = assertion.rawAuthenticatorData,
              let signature = assertion.signature else {
            throw PasskeyProviderError.invalidResponse
        }
        
        let userHandle = assertion.userID
        
        // Convert credentialID to Base64URL string (WebAuthn id field)
        let idString = credentialId.base64URLEncodedString()
        
        // Handle optional userHandle - only include if present and non-empty
        let finalUserHandle: Data? = if let handle = userHandle, !handle.isEmpty {
            handle
        } else {
            nil
        }
        
        return AssertionResponse(
            id: idString,
            rawId: credentialId,
            clientDataJSON: rawClientDataJSON,
            authenticatorData: rawAuthenticatorData,
            signature: signature,
            userHandle: finalUserHandle,
            type: "public-key"
        )
    }
}

// MARK: - ASAuthorizationControllerDelegate

extension PasskeyProvider: ASAuthorizationControllerDelegate {
    public func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        do {
            let response: Any
            
            if let registration = authorization.credential as? ASAuthorizationPlatformPublicKeyCredentialRegistration {
                response = try parseRegistrationResponse(registration)
            } else if let assertion = authorization.credential as? ASAuthorizationPlatformPublicKeyCredentialAssertion {
                response = try parseAssertionResponse(assertion)
            } else {
                currentCompletion?(.failure(PasskeyProviderError.invalidResponse))
                currentCompletion = nil
                return
            }
            
            currentCompletion?(.success(response))
            currentCompletion = nil
        } catch {
            currentCompletion?(.failure(error))
            currentCompletion = nil
        }
    }
    
    public func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        let mappedError = mapAuthorizationError(error)
        currentCompletion?(.failure(mappedError))
        currentCompletion = nil
    }
    
    private func mapAuthorizationError(_ error: Error) -> PasskeyProviderError {
        let asError = error as NSError
        
        // Map ASAuthorizationError codes
        switch asError.code {
        case ASAuthorizationError.canceled.rawValue:
            return .userCancelled
        case ASAuthorizationError.failed.rawValue:
            return .operationFailed(error.localizedDescription)
        case ASAuthorizationError.notHandled.rawValue:
            return .noCredentials
        default:
            return .operationFailed(error.localizedDescription)
        }
    }
}

// MARK: - ASAuthorizationControllerPresentationContextProviding

extension PasskeyProvider: ASAuthorizationControllerPresentationContextProviding {
    public func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        if let window = presentationContext {
            return window
        }
        
        #if os(iOS)
        return ASPresentationAnchor()
        #else
        fatalError("PasskeyProvider requires a presentation context to be set")
        #endif
    }
    
    @objc public func setPresentationContext(_ window: ASPresentationAnchor) {
        self.presentationContext = window
    }
}

// MARK: - Base64URL Encoding Helper

extension Data {
    /// Encodes data as Base64URL (no padding, URL-safe characters).
    /// WebAuthn specification requires Base64URL encoding for credential IDs.
    func base64URLEncodedString() -> String {
        let base64 = self.base64EncodedString()
        return base64
            .replacingOccurrences(of: "+", with: "-")
            .replacingOccurrences(of: "/", with: "_")
            .replacingOccurrences(of: "=", with: "")
    }
}
