# Plan 001: Networking Infrastructure

## Overview
Establish the foundational networking layer for the Komodo KMP API client. This creates the HTTP client provider, RPC client abstraction, and error handling that all feature modules depend on.

## Prerequisites
- None (this is the foundational plan)

## Depends On
- `core-network` module (exists, has Ktor dependencies configured)
- `core-domain` module (exists)
- Existing `RpcRequest`/`RpcError` DTOs in `komodo-core`

## Deliverables

### 1. HttpClient Provider
**Files to Create:**
- `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/HttpClientProvider.kt`
- `core-network/src/androidMain/kotlin/ca/glong/komodo/core/network/AndroidHttpEngine.kt`
- `core-network/src/iosMain/kotlin/ca/glong/komodo/core/network/IosHttpEngine.kt`

**Specifications:**
```kotlin
// HttpClientProvider.kt
interface HttpClientProvider {
    fun create(config: HttpClientConfig): HttpClient
}

data class HttpClientConfig(
    val baseUrl: String,
    val tokenProvider: suspend () -> String?,
    val enableLogging: Boolean = false
)
```

**Requirements:**
- Configure `ContentNegotiation` with `kotlinx.serialization`
- Configure `Auth` plugin with Bearer token from `tokenProvider`
- Configure `Logging` plugin (debug builds only)
- Configure `DefaultRequest` with base URL and JSON content type
- Platform-specific engines: OkHttp (Android), Darwin (iOS)

### 2. RPC Client Abstraction
**Files to Create:**
- `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/rpc/KomodoRpcClient.kt`
- `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/rpc/RpcEndpoint.kt`

**Specifications:**
```kotlin
// RpcEndpoint.kt
enum class RpcEndpoint(val path: String) {
    AUTH("/auth"),
    READ("/read"),
    WRITE("/write"),
    EXECUTE("/execute")
}

// KomodoRpcClient.kt
interface KomodoRpcClient {
    suspend fun <P, R> call(
        endpoint: RpcEndpoint,
        operation: String,
        params: P,
        paramsSerializer: KSerializer<P>,
        responseSerializer: KSerializer<R>
    ): Result<R>
}
```

**Requirements:**
- Wrap params in `RpcRequest(type=operation, params=params)`
- POST to `{baseUrl}{endpoint.path}`
- Handle successful responses (deserialize to `R`)
- Handle error responses (deserialize `RpcError`, map to `ApiError`)
- Handle network failures (map to `ApiError.NetworkError`)

### 3. Error Handling Layer
**Files to Create:**
- `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/error/ApiError.kt`
- `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/error/ErrorMapper.kt`

**Specifications:**
```kotlin
// ApiError.kt
sealed class ApiError : Exception() {
    data class Unauthorized(override val message: String) : ApiError()
    data class Forbidden(override val message: String) : ApiError()
    data class NotFound(val resource: String) : ApiError()
    data class BadRequest(val errors: List<String>) : ApiError()
    data class ServerError(val code: Int, override val message: String) : ApiError()
    data class NetworkError(override val cause: Throwable) : ApiError()
    data class RpcError(val type: String, override val message: String) : ApiError()
    data object Unknown : ApiError()
}

// ErrorMapper.kt
object ErrorMapper {
    fun fromHttpStatus(status: HttpStatusCode, body: String?): ApiError
    fun fromRpcError(error: RpcError): ApiError
    fun fromException(e: Throwable): ApiError
}
```

### 4. DI Module
**Files to Create:**
- `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/di/NetworkModule.kt`

**Specifications:**
```kotlin
@Module
class NetworkModule {
    @Single
    fun provideHttpClientProvider(): HttpClientProvider
    
    @Single
    fun provideHttpClient(
        provider: HttpClientProvider,
        settingsRepository: SettingsRepository // for base URL
        tokenRepository: TokenRepository // for auth token
    ): HttpClient
    
    @Single
    fun provideKomodoRpcClient(httpClient: HttpClient): KomodoRpcClient
}
```

### 5. Move Existing DTOs
**Files to Move:**
- Move `RpcRequest` from `komodo-core` to `core-network` (or create typealias)
- Move `RpcError` from `komodo-core` to `core-network` (or create typealias)

**Rationale:** Network DTOs belong in network module. Use typealiases for backward compatibility.

## Tests

### Unit Tests
**Files to Create:**
- `core-network/src/commonTest/kotlin/.../HttpClientProviderTest.kt`
- `core-network/src/commonTest/kotlin/.../KomodoRpcClientTest.kt`
- `core-network/src/commonTest/kotlin/.../ErrorMapperTest.kt`

**Test Cases:**

1. **HttpClientProviderTest**
   - Creates client with correct content negotiation
   - Adds bearer token header when token available
   - Omits auth header when no token
   - Applies base URL to requests

2. **KomodoRpcClientTest**
   - Wraps params in RpcRequest correctly
   - Deserializes successful response
   - Maps RpcError response to ApiError.RpcError
   - Maps HTTP 401 to ApiError.Unauthorized
   - Maps HTTP 403 to ApiError.Forbidden
   - Maps HTTP 404 to ApiError.NotFound
   - Maps HTTP 500 to ApiError.ServerError
   - Maps network exception to ApiError.NetworkError

3. **ErrorMapperTest**
   - Maps all HTTP status codes correctly
   - Parses RpcError JSON correctly
   - Handles malformed error responses gracefully

## File Structure (Final)
```
core-network/
├── build.gradle.kts (exists)
└── src/
    ├── commonMain/kotlin/ca/glong/komodo/core/network/
    │   ├── HttpClientProvider.kt
    │   ├── di/
    │   │   └── NetworkModule.kt
    │   ├── error/
    │   │   ├── ApiError.kt
    │   │   └── ErrorMapper.kt
    │   └── rpc/
    │       ├── KomodoRpcClient.kt
    │       ├── RpcEndpoint.kt
    │       └── RpcRequest.kt (typealias or move)
    ├── commonTest/kotlin/ca/glong/komodo/core/network/
    │   ├── HttpClientProviderTest.kt
    │   ├── KomodoRpcClientTest.kt
    │   └── ErrorMapperTest.kt
    ├── androidMain/kotlin/ca/glong/komodo/core/network/
    │   └── AndroidHttpEngine.kt
    └── iosMain/kotlin/ca/glong/komodo/core/network/
        └── IosHttpEngine.kt
```

## Acceptance Criteria
- [ ] `./gradlew :core-network:test` passes
- [ ] `./gradlew :core-network:compileKotlinAndroid` succeeds
- [ ] `./gradlew :core-network:compileKotlinIosX64` succeeds
- [ ] No LSP diagnostics errors in created files
- [ ] HttpClient properly injects auth token
- [ ] RPC client correctly wraps/unwraps requests
- [ ] All error scenarios map to appropriate ApiError types

## Migration Notes
After this plan is complete:
1. Update `AuthRepositoryImpl` in `komodo-core` to use `KomodoRpcClient`
2. Other repositories can depend on `core-network` for the RPC client

## Estimated Effort
- Implementation: 4-6 hours
- Testing: 2-3 hours
- Total: 6-9 hours
