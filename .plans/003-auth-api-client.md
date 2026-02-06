# Plan 003: Auth API Client

## Overview
Implement the authentication API client covering all auth-related RPC operations. This is the first domain-specific API client, serving as the template for subsequent API clients.

## Prerequisites
- Plan 001 (Networking Infrastructure) - KomodoRpcClient, ApiError
- Plan 002 (Core Domain DTOs) - Auth types

## Depends On
- `core-network` module with KomodoRpcClient
- `core-domain` module with auth DTOs

## Deliverables

### 1. Auth API Client Interface

**File: `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/api/AuthApiClient.kt`**

```kotlin
interface AuthApiClient {
    // Login Options
    suspend fun getLoginOptions(): Result<GetLoginOptionsResponse>
    
    // Local Authentication
    suspend fun loginLocalUser(username: String, password: String): Result<JwtOrTwoFactor>
    suspend fun signUpLocalUser(username: String, password: String): Result<JwtOrTwoFactor>
    suspend fun loginWithSecret(username: String, secret: String): Result<JwtResponse>
    
    // Two-Factor Completion
    suspend fun completeTotpLogin(userId: String, otp: String): Result<JwtResponse>
    suspend fun completePasskeyLogin(userId: String, credential: PublicKeyCredential): Result<JwtResponse>
    
    // Third-Party OAuth Exchange
    suspend fun exchangeForJwt(token: String): Result<JwtResponse>
    
    // TOTP Management
    suspend fun startTotpEnrollment(): Result<TotpEnrollmentResponse>
    suspend fun confirmTotpEnrollment(code: String): Result<ConfirmTotpEnrollmentResponse>
    suspend fun disableTotp(): Result<NoData>
    
    // Passkey Management
    suspend fun startPasskeyRegistration(): Result<PasskeyRegistrationChallenge>
    suspend fun finishPasskeyRegistration(credential: PasskeyRegistrationCredential): Result<NoData>
    suspend fun deletePasskey(credentialId: String): Result<NoData>
    suspend fun listPasskeys(): Result<List<PasskeyInfo>>
    
    // API Key Management
    suspend fun createApiKey(name: String, expires: Long? = null): Result<CreateApiKeyResponse>
    suspend fun createApiKeyV2(name: String, expires: Long? = null, publicKey: String? = null): Result<CreateApiKeyV2Response>
    suspend fun deleteApiKey(key: String): Result<NoData>
    suspend fun listApiKeys(): Result<List<ApiKey>>
    
    // User Info
    suspend fun getUser(): Result<User>
    suspend fun getUsers(): Result<List<User>>
}
```

### 2. Auth API Client Implementation

**File: `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/api/AuthApiClientImpl.kt`**

```kotlin
@Single(binds = [AuthApiClient::class])
class AuthApiClientImpl(
    private val rpcClient: KomodoRpcClient
) : AuthApiClient {

    override suspend fun getLoginOptions(): Result<GetLoginOptionsResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "GetLoginOptions",
            params = EmptyParams(),
            paramsSerializer = EmptyParams.serializer(),
            responseSerializer = GetLoginOptionsResponse.serializer()
        )

    override suspend fun loginLocalUser(
        username: String, 
        password: String
    ): Result<JwtOrTwoFactor> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "LoginLocalUser",
            params = LoginLocalUserParams(username, password),
            paramsSerializer = LoginLocalUserParams.serializer(),
            responseSerializer = JwtOrTwoFactor.serializer()
        )

    override suspend fun signUpLocalUser(
        username: String, 
        password: String
    ): Result<JwtOrTwoFactor> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "SignUpLocalUser",
            params = SignUpLocalUserParams(username, password),
            paramsSerializer = SignUpLocalUserParams.serializer(),
            responseSerializer = JwtOrTwoFactor.serializer()
        )

    override suspend fun loginWithSecret(
        username: String, 
        secret: String
    ): Result<JwtResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "LoginWithSecret",
            params = LoginWithSecretParams(username, secret),
            paramsSerializer = LoginWithSecretParams.serializer(),
            responseSerializer = JwtResponse.serializer()
        )

    override suspend fun completeTotpLogin(
        userId: String, 
        otp: String
    ): Result<JwtResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "CompleteTotpLogin",
            params = CompleteTotpLoginParams(userId, otp),
            paramsSerializer = CompleteTotpLoginParams.serializer(),
            responseSerializer = JwtResponse.serializer()
        )

    override suspend fun startTotpEnrollment(): Result<TotpEnrollmentResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "StartTotpEnrollment",
            params = EmptyParams(),
            paramsSerializer = EmptyParams.serializer(),
            responseSerializer = TotpEnrollmentResponse.serializer()
        )

    override suspend fun confirmTotpEnrollment(code: String): Result<ConfirmTotpEnrollmentResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "ConfirmTotpEnrollment",
            params = ConfirmTotpEnrollmentParams(code),
            paramsSerializer = ConfirmTotpEnrollmentParams.serializer(),
            responseSerializer = ConfirmTotpEnrollmentResponse.serializer()
        )

    override suspend fun createApiKey(
        name: String, 
        expires: Long?
    ): Result<CreateApiKeyResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "CreateApiKey",
            params = CreateApiKeyParams(name, expires),
            paramsSerializer = CreateApiKeyParams.serializer(),
            responseSerializer = CreateApiKeyResponse.serializer()
        )

    override suspend fun deleteApiKey(key: String): Result<NoData> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "DeleteApiKey",
            params = DeleteApiKeyParams(key),
            paramsSerializer = DeleteApiKeyParams.serializer(),
            responseSerializer = NoData.serializer()
        )

    override suspend fun listApiKeys(): Result<List<ApiKey>> =
        rpcClient.call(
            endpoint = RpcEndpoint.AUTH,
            operation = "ListApiKeys",
            params = EmptyParams(),
            paramsSerializer = EmptyParams.serializer(),
            responseSerializer = ListSerializer(ApiKey.serializer())
        )

    // ... implement remaining methods following same pattern
}
```

### 3. Request Parameter DTOs

**File: `core-domain/src/commonMain/kotlin/ca/glong/komodo/core/domain/model/auth/params/`**

```kotlin
// EmptyParams.kt
@Serializable
data object EmptyParams

// LoginParams.kt
@Serializable
data class LoginLocalUserParams(
    val username: String,
    val password: String
)

@Serializable
data class SignUpLocalUserParams(
    val username: String,
    val password: String
)

@Serializable
data class LoginWithSecretParams(
    val username: String,
    val secret: String
)

// TwoFactorParams.kt
@Serializable
data class CompleteTotpLoginParams(
    @SerialName("user_id") val userId: String,
    val otp: String
)

@Serializable
data class CompletePasskeyLoginParams(
    @SerialName("user_id") val userId: String,
    val credential: PublicKeyCredential
)

@Serializable
data class ExchangeForJwtParams(
    val token: String
)

// TotpParams.kt
@Serializable
data class ConfirmTotpEnrollmentParams(
    val code: String
)

// ApiKeyParams.kt
@Serializable
data class CreateApiKeyParams(
    val name: String,
    val expires: Long? = null
)

@Serializable
data class CreateApiKeyV2Params(
    val name: String,
    val expires: Long? = null,
    @SerialName("public_key") val publicKey: String? = null
)

@Serializable
data class DeleteApiKeyParams(
    val key: String
)
```

### 4. Response DTOs (extend auth types)

**Files in `core-domain/.../model/auth/`:**

```kotlin
// CreateApiKeyResponse.kt
@Serializable
data class CreateApiKeyResponse(
    val key: String
)

@Serializable
data class CreateApiKeyV2Response(
    val key: String,
    @SerialName("secret") val secret: String? = null // Only if no public_key provided
)

// PasskeyTypes.kt (WebAuthn)
@Serializable
data class PasskeyRegistrationChallenge(
    val challenge: String,
    @SerialName("rp") val relyingParty: RelyingParty,
    val user: WebAuthnUser,
    @SerialName("pub_key_cred_params") val pubKeyCredParams: List<PubKeyCredParam>,
    val timeout: Long,
    @SerialName("exclude_credentials") val excludeCredentials: List<CredentialDescriptor> = emptyList(),
    @SerialName("authenticator_selection") val authenticatorSelection: AuthenticatorSelection? = null,
    val attestation: String = "none"
)

@Serializable
data class PasskeyInfo(
    @SerialName("credential_id") val credentialId: String,
    val name: String,
    @SerialName("created_at") val createdAt: Long
)

// PublicKeyCredential.kt (for passkey assertions)
@Serializable
data class PublicKeyCredential(
    val id: String,
    @SerialName("raw_id") val rawId: String,
    val response: AuthenticatorResponse,
    val type: String = "public-key"
)
```

### 5. DI Registration

**Update: `core-network/.../di/NetworkModule.kt`**

```kotlin
@Module
class NetworkModule {
    // ... existing providers
    
    @Single
    fun provideAuthApiClient(rpcClient: KomodoRpcClient): AuthApiClient =
        AuthApiClientImpl(rpcClient)
}
```

## Auth Operations Reference (from OpenAPI)

| Operation | Endpoint | Params | Response |
|-----------|----------|--------|----------|
| GetLoginOptions | /auth | `{}` | GetLoginOptionsResponse |
| LoginLocalUser | /auth | `{username, password}` | JwtOrTwoFactor |
| SignUpLocalUser | /auth | `{username, password}` | JwtOrTwoFactor |
| LoginWithSecret | /auth | `{username, secret}` | JwtResponse |
| CompleteTotpLogin | /auth | `{user_id, otp}` | JwtResponse |
| CompletePasskeyLogin | /auth | `{user_id, credential}` | JwtResponse |
| ExchangeForJwt | /auth | `{token}` | JwtResponse |
| StartTotpEnrollment | /auth | `{}` | TotpEnrollmentResponse |
| ConfirmTotpEnrollment | /auth | `{code}` | ConfirmTotpEnrollmentResponse |
| DisableTotp | /auth | `{}` | NoData |
| StartPasskeyRegistration | /auth | `{}` | PasskeyRegistrationChallenge |
| FinishPasskeyRegistration | /auth | `{credential}` | NoData |
| DeletePasskey | /auth | `{credential_id}` | NoData |
| ListPasskeys | /auth | `{}` | List<PasskeyInfo> |
| CreateApiKey | /auth | `{name, expires?}` | CreateApiKeyResponse |
| CreateApiKeyV2 | /auth | `{name, expires?, public_key?}` | CreateApiKeyV2Response |
| DeleteApiKey | /auth | `{key}` | NoData |
| ListApiKeys | /auth | `{}` | List<ApiKey> |
| GetUser | /read | `{}` | User |
| GetUsers | /read | `{}` | List<User> |

## Tests

### Unit Tests
**File: `core-network/src/commonTest/kotlin/.../api/AuthApiClientTest.kt`**

```kotlin
class AuthApiClientTest {
    private lateinit var mockRpcClient: MockKomodoRpcClient
    private lateinit var authClient: AuthApiClient

    @BeforeTest
    fun setup() {
        mockRpcClient = MockKomodoRpcClient()
        authClient = AuthApiClientImpl(mockRpcClient)
    }

    @Test
    fun `getLoginOptions returns available options`() = runTest {
        val expected = GetLoginOptionsResponse(
            local = true,
            github = true,
            google = false
        )
        mockRpcClient.mockResponse(expected)

        val result = authClient.getLoginOptions()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrThrow())
        mockRpcClient.verifyCall("GetLoginOptions", EmptyParams())
    }

    @Test
    fun `loginLocalUser returns JWT on success`() = runTest {
        val jwt = JwtResponse(token = "abc.123.xyz")
        mockRpcClient.mockResponse(JwtOrTwoFactor.Jwt(jwt))

        val result = authClient.loginLocalUser("user", "pass")

        assertTrue(result.isSuccess)
        val response = result.getOrThrow()
        assertTrue(response is JwtOrTwoFactor.Jwt)
    }

    @Test
    fun `loginLocalUser returns TwoFactor when 2FA required`() = runTest {
        val twoFactor = TwoFactorParams(userId = "user123", methods = listOf("totp", "passkey"))
        mockRpcClient.mockResponse(JwtOrTwoFactor.TwoFactor(twoFactor))

        val result = authClient.loginLocalUser("user", "pass")

        assertTrue(result.isSuccess)
        val response = result.getOrThrow()
        assertTrue(response is JwtOrTwoFactor.TwoFactor)
    }

    @Test
    fun `loginLocalUser returns error on invalid credentials`() = runTest {
        mockRpcClient.mockError(ApiError.Unauthorized("Invalid credentials"))

        val result = authClient.loginLocalUser("user", "wrongpass")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is ApiError.Unauthorized)
    }

    @Test
    fun `createApiKey returns key on success`() = runTest {
        val expected = CreateApiKeyResponse(key = "komodo_key_abc123")
        mockRpcClient.mockResponse(expected)

        val result = authClient.createApiKey("my-key", expires = null)

        assertTrue(result.isSuccess)
        assertEquals("komodo_key_abc123", result.getOrThrow().key)
    }

    @Test
    fun `completeTotpLogin returns JWT on valid code`() = runTest {
        val jwt = JwtResponse(token = "abc.123.xyz")
        mockRpcClient.mockResponse(jwt)

        val result = authClient.completeTotpLogin("user123", "123456")

        assertTrue(result.isSuccess)
        assertEquals(jwt.token, result.getOrThrow().token)
    }

    // ... additional tests for each operation
}
```

### Mock RPC Client (Test Utility)
**File: `core-network/src/commonTest/kotlin/.../MockKomodoRpcClient.kt`**

```kotlin
class MockKomodoRpcClient : KomodoRpcClient {
    private var nextResponse: Any? = null
    private var nextError: Throwable? = null
    private val calls = mutableListOf<Pair<String, Any>>()

    fun <T> mockResponse(response: T) {
        nextResponse = response
        nextError = null
    }

    fun mockError(error: Throwable) {
        nextError = error
        nextResponse = null
    }

    fun verifyCall(operation: String, params: Any) {
        assertTrue(calls.any { it.first == operation && it.second == params })
    }

    override suspend fun <P, R> call(
        endpoint: RpcEndpoint,
        operation: String,
        params: P,
        paramsSerializer: KSerializer<P>,
        responseSerializer: KSerializer<R>
    ): Result<R> {
        calls.add(operation to (params as Any))
        return when {
            nextError != null -> Result.failure(nextError!!)
            nextResponse != null -> Result.success(nextResponse as R)
            else -> Result.failure(IllegalStateException("No mock configured"))
        }
    }
}
```

## File Structure (Final)
```
core-network/
└── src/
    ├── commonMain/kotlin/ca/glong/komodo/core/network/
    │   └── api/
    │       ├── AuthApiClient.kt
    │       └── AuthApiClientImpl.kt
    └── commonTest/kotlin/ca/glong/komodo/core/network/
        ├── api/
        │   └── AuthApiClientTest.kt
        └── MockKomodoRpcClient.kt

core-domain/
└── src/commonMain/kotlin/ca/glong/komodo/core/domain/model/auth/
    ├── params/
    │   ├── ApiKeyParams.kt
    │   ├── EmptyParams.kt
    │   ├── LoginParams.kt
    │   ├── PasskeyParams.kt
    │   ├── TotpParams.kt
    │   └── TwoFactorParams.kt
    ├── ApiKey.kt
    ├── CreateApiKeyResponse.kt
    ├── JwtOrTwoFactor.kt
    ├── JwtResponse.kt
    ├── LoginOptions.kt
    ├── PasskeyTypes.kt
    ├── TotpTypes.kt
    └── User.kt
```

## Acceptance Criteria
- [ ] `./gradlew :core-network:test` passes
- [ ] AuthApiClient covers all ~20 auth operations
- [ ] All param DTOs serialize correctly
- [ ] All response DTOs deserialize correctly
- [ ] Error cases return appropriate ApiError types
- [ ] Mock RPC client enables isolated testing
- [ ] DI registration added to NetworkModule

## Migration Notes
After this plan is complete:
1. Migrate existing `AuthRepositoryImpl` to use `AuthApiClient`
2. Update `feature-auth-impl` to use the new client

## Estimated Effort
- Interface definition: 1 hour
- Implementation: 2 hours
- Param/Response DTOs: 2 hours
- Tests: 3 hours
- **Total: 8-10 hours**
