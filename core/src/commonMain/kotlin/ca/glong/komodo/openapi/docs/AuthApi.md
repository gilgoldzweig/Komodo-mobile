# AuthApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**beginExternalLoginLink**](AuthApi.md#beginExternalLoginLink) | **POST** /auth/manage/BeginExternalLoginLink |  |
| [**beginPasskeyEnrollment**](AuthApi.md#beginPasskeyEnrollment) | **POST** /auth/manage/BeginPasskeyEnrollment |  |
| [**beginTotpEnrollment**](AuthApi.md#beginTotpEnrollment) | **POST** /auth/manage/BeginTotpEnrollment |  |
| [**completePasskeyLogin**](AuthApi.md#completePasskeyLogin) | **POST** /auth/login/CompletePasskeyLogin |  |
| [**completeTotpLogin**](AuthApi.md#completeTotpLogin) | **POST** /auth/login/CompleteTotpLogin |  |
| [**confirmPasskeyEnrollment**](AuthApi.md#confirmPasskeyEnrollment) | **POST** /auth/manage/ConfirmPasskeyEnrollment |  |
| [**confirmTotpEnrollment**](AuthApi.md#confirmTotpEnrollment) | **POST** /auth/manage/ConfirmTotpEnrollment |  |
| [**createApiKey**](AuthApi.md#createApiKey) | **POST** /auth/manage/CreateApiKey |  |
| [**createApiKeyV2**](AuthApi.md#createApiKeyV2) | **POST** /auth/manage/CreateApiKeyV2 |  |
| [**deleteApiKey**](AuthApi.md#deleteApiKey) | **POST** /auth/manage/DeleteApiKey |  |
| [**deleteApiKeyV2**](AuthApi.md#deleteApiKeyV2) | **POST** /auth/manage/DeleteApiKeyV2 |  |
| [**exchangeForJwt**](AuthApi.md#exchangeForJwt) | **POST** /auth/login/ExchangeForJwt |  |
| [**getLoginOptions**](AuthApi.md#getLoginOptions) | **POST** /auth/login/GetLoginOptions |  |
| [**githubCallback**](AuthApi.md#githubCallback) | **GET** /auth/github/callback |  |
| [**githubLink**](AuthApi.md#githubLink) | **GET** /auth/github/link |  |
| [**githubLogin**](AuthApi.md#githubLogin) | **GET** /auth/github/login |  |
| [**googleCallback**](AuthApi.md#googleCallback) | **GET** /auth/google/callback |  |
| [**googleLink**](AuthApi.md#googleLink) | **GET** /auth/google/link |  |
| [**googleLogin**](AuthApi.md#googleLogin) | **GET** /auth/google/login |  |
| [**loginLocalUser**](AuthApi.md#loginLocalUser) | **POST** /auth/login/LoginLocalUser |  |
| [**oidcCallback**](AuthApi.md#oidcCallback) | **GET** /auth/oidc/callback |  |
| [**oidcLink**](AuthApi.md#oidcLink) | **GET** /auth/oidc/link |  |
| [**oidcLogin**](AuthApi.md#oidcLogin) | **GET** /auth/oidc/login |  |
| [**signUpLocalUser**](AuthApi.md#signUpLocalUser) | **POST** /auth/login/SignUpLocalUser |  |
| [**unenrollPasskey**](AuthApi.md#unenrollPasskey) | **POST** /auth/manage/UnenrollPasskey |  |
| [**unenrollTotp**](AuthApi.md#unenrollTotp) | **POST** /auth/manage/UnenrollTotp |  |
| [**unlinkLogin**](AuthApi.md#unlinkLogin) | **POST** /auth/manage/UnlinkLogin |  |
| [**updateExternalSkip2fa**](AuthApi.md#updateExternalSkip2fa) | **POST** /auth/manage/UpdateExternalSkip2fa |  |
| [**updatePassword**](AuthApi.md#updatePassword) | **POST** /auth/manage/UpdatePassword |  |
| [**updateUsername**](AuthApi.md#updateUsername) | **POST** /auth/manage/UpdateUsername |  |


<a id="beginExternalLoginLink"></a>
# **beginExternalLoginLink**
> kotlin.String beginExternalLoginLink(body)



Begin linking flow for an external login.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : kotlin.String = apiInstance.beginExternalLoginLink(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#beginExternalLoginLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#beginExternalLoginLink")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="beginPasskeyEnrollment"></a>
# **beginPasskeyEnrollment**
> kotlin.collections.Map&lt;kotlin.String, kotlin.Any&gt; beginPasskeyEnrollment(body)



Begins enrollment flow for Passkey 2FA.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : kotlin.collections.Map<kotlin.String, kotlin.Any> = apiInstance.beginPasskeyEnrollment(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#beginPasskeyEnrollment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#beginPasskeyEnrollment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**kotlin.collections.Map&lt;kotlin.String, kotlin.Any&gt;**](kotlin.Any.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="beginTotpEnrollment"></a>
# **beginTotpEnrollment**
> BeginTotpEnrollmentResponse beginTotpEnrollment(body)



Begins enrollment flow for Totp 2FA.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : BeginTotpEnrollmentResponse = apiInstance.beginTotpEnrollment(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#beginTotpEnrollment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#beginTotpEnrollment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**BeginTotpEnrollmentResponse**](BeginTotpEnrollmentResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="completePasskeyLogin"></a>
# **completePasskeyLogin**
> JwtResponse completePasskeyLogin(completePasskeyLogin)



Complete login using passkey as second factor.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val completePasskeyLogin : CompletePasskeyLogin =  // CompletePasskeyLogin | 
try {
    val result : JwtResponse = apiInstance.completePasskeyLogin(completePasskeyLogin)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#completePasskeyLogin")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#completePasskeyLogin")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **completePasskeyLogin** | [**CompletePasskeyLogin**](CompletePasskeyLogin.md)|  | |

### Return type

[**JwtResponse**](JwtResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="completeTotpLogin"></a>
# **completeTotpLogin**
> JwtResponse completeTotpLogin(completeTotpLogin)



Complete login using TOTP code as second factor.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val completeTotpLogin : CompleteTotpLogin =  // CompleteTotpLogin | 
try {
    val result : JwtResponse = apiInstance.completeTotpLogin(completeTotpLogin)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#completeTotpLogin")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#completeTotpLogin")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **completeTotpLogin** | [**CompleteTotpLogin**](CompleteTotpLogin.md)|  | |

### Return type

[**JwtResponse**](JwtResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="confirmPasskeyEnrollment"></a>
# **confirmPasskeyEnrollment**
> kotlin.String confirmPasskeyEnrollment(confirmPasskeyEnrollment)



Confirm enrollment for Passkey 2FA.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val confirmPasskeyEnrollment : ConfirmPasskeyEnrollment =  // ConfirmPasskeyEnrollment | 
try {
    val result : kotlin.String = apiInstance.confirmPasskeyEnrollment(confirmPasskeyEnrollment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#confirmPasskeyEnrollment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#confirmPasskeyEnrollment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **confirmPasskeyEnrollment** | [**ConfirmPasskeyEnrollment**](ConfirmPasskeyEnrollment.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="confirmTotpEnrollment"></a>
# **confirmTotpEnrollment**
> ConfirmTotpEnrollmentResponse confirmTotpEnrollment(confirmTotpEnrollment)



Confirm enrollment for Totp 2FA.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val confirmTotpEnrollment : ConfirmTotpEnrollment =  // ConfirmTotpEnrollment | 
try {
    val result : ConfirmTotpEnrollmentResponse = apiInstance.confirmTotpEnrollment(confirmTotpEnrollment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#confirmTotpEnrollment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#confirmTotpEnrollment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **confirmTotpEnrollment** | [**ConfirmTotpEnrollment**](ConfirmTotpEnrollment.md)|  | |

### Return type

[**ConfirmTotpEnrollmentResponse**](ConfirmTotpEnrollmentResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="createApiKey"></a>
# **createApiKey**
> CreateApiKeyResponse createApiKey(createApiKey)



Create an api key for the calling user.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val createApiKey : CreateApiKey =  // CreateApiKey | 
try {
    val result : CreateApiKeyResponse = apiInstance.createApiKey(createApiKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#createApiKey")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#createApiKey")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createApiKey** | [**CreateApiKey**](CreateApiKey.md)|  | |

### Return type

[**CreateApiKeyResponse**](CreateApiKeyResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="createApiKeyV2"></a>
# **createApiKeyV2**
> CreateApiKeyV2Response createApiKeyV2(createApiKeyV2)



Create an api key (v2) for the calling user.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val createApiKeyV2 : CreateApiKeyV2 =  // CreateApiKeyV2 | 
try {
    val result : CreateApiKeyV2Response = apiInstance.createApiKeyV2(createApiKeyV2)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#createApiKeyV2")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#createApiKeyV2")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createApiKeyV2** | [**CreateApiKeyV2**](CreateApiKeyV2.md)|  | |

### Return type

[**CreateApiKeyV2Response**](CreateApiKeyV2Response.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deleteApiKey"></a>
# **deleteApiKey**
> kotlin.String deleteApiKey(deleteApiKey)



Delete an api key for the calling user.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val deleteApiKey : DeleteApiKey =  // DeleteApiKey | 
try {
    val result : kotlin.String = apiInstance.deleteApiKey(deleteApiKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#deleteApiKey")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#deleteApiKey")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deleteApiKey** | [**DeleteApiKey**](DeleteApiKey.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deleteApiKeyV2"></a>
# **deleteApiKeyV2**
> kotlin.String deleteApiKeyV2(deleteApiKeyV2)



Create an api key (v2) for the calling user.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val deleteApiKeyV2 : DeleteApiKeyV2 =  // DeleteApiKeyV2 | 
try {
    val result : kotlin.String = apiInstance.deleteApiKeyV2(deleteApiKeyV2)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#deleteApiKeyV2")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#deleteApiKeyV2")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deleteApiKeyV2** | [**DeleteApiKeyV2**](DeleteApiKeyV2.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="exchangeForJwt"></a>
# **exchangeForJwt**
> JwtResponse exchangeForJwt(body)



Retrieve a JWT after completing third party login flows.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : JwtResponse = apiInstance.exchangeForJwt(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#exchangeForJwt")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#exchangeForJwt")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**JwtResponse**](JwtResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="getLoginOptions"></a>
# **getLoginOptions**
> GetLoginOptionsResponse getLoginOptions(body)



Get the available options to login, eg. local and external providers.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : GetLoginOptionsResponse = apiInstance.getLoginOptions(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#getLoginOptions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#getLoginOptions")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**GetLoginOptionsResponse**](GetLoginOptionsResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="githubCallback"></a>
# **githubCallback**
> githubCallback(UNKNOWN_PARAMETER_NAME, UNKNOWN_PARAMETER_NAME2, UNKNOWN_PARAMETER_NAME3)



Callback to finish Github login

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Callback state.
val UNKNOWN_PARAMETER_NAME2 :  =  //  | Callback code.
val UNKNOWN_PARAMETER_NAME3 :  =  //  | Callback error.
try {
    apiInstance.githubCallback(UNKNOWN_PARAMETER_NAME, UNKNOWN_PARAMETER_NAME2, UNKNOWN_PARAMETER_NAME3)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#githubCallback")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#githubCallback")
    e.printStackTrace()
}
```

### Parameters
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Callback state. | |
| **UNKNOWN_PARAMETER_NAME2** | [****](.md)| Callback code. | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME3** | [****](.md)| Callback error. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="githubLink"></a>
# **githubLink**
> githubLink()



Link existing account to Github user

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
try {
    apiInstance.githubLink()
} catch (e: ClientException) {
    println("4xx response calling AuthApi#githubLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#githubLink")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="githubLogin"></a>
# **githubLogin**
> githubLogin(UNKNOWN_PARAMETER_NAME)



Login using Github

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Optional path to redirect back to after login.
try {
    apiInstance.githubLogin(UNKNOWN_PARAMETER_NAME)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#githubLogin")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#githubLogin")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Optional path to redirect back to after login. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="googleCallback"></a>
# **googleCallback**
> googleCallback(UNKNOWN_PARAMETER_NAME, UNKNOWN_PARAMETER_NAME2, UNKNOWN_PARAMETER_NAME3)



Callback to finish Google login

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Callback state.
val UNKNOWN_PARAMETER_NAME2 :  =  //  | Callback code.
val UNKNOWN_PARAMETER_NAME3 :  =  //  | Callback error.
try {
    apiInstance.googleCallback(UNKNOWN_PARAMETER_NAME, UNKNOWN_PARAMETER_NAME2, UNKNOWN_PARAMETER_NAME3)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#googleCallback")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#googleCallback")
    e.printStackTrace()
}
```

### Parameters
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Callback state. | |
| **UNKNOWN_PARAMETER_NAME2** | [****](.md)| Callback code. | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME3** | [****](.md)| Callback error. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="googleLink"></a>
# **googleLink**
> googleLink()



Link existing account to Google user

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
try {
    apiInstance.googleLink()
} catch (e: ClientException) {
    println("4xx response calling AuthApi#googleLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#googleLink")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="googleLogin"></a>
# **googleLogin**
> googleLogin(UNKNOWN_PARAMETER_NAME)



Login using Google

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Optional path to redirect back to after login.
try {
    apiInstance.googleLogin(UNKNOWN_PARAMETER_NAME)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#googleLogin")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#googleLogin")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Optional path to redirect back to after login. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="loginLocalUser"></a>
# **loginLocalUser**
> JwtOrTwoFactor loginLocalUser(loginLocalUser)



Login as a local user.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val loginLocalUser : LoginLocalUser =  // LoginLocalUser | 
try {
    val result : JwtOrTwoFactor = apiInstance.loginLocalUser(loginLocalUser)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#loginLocalUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#loginLocalUser")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **loginLocalUser** | [**LoginLocalUser**](LoginLocalUser.md)|  | |

### Return type

[**JwtOrTwoFactor**](JwtOrTwoFactor.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="oidcCallback"></a>
# **oidcCallback**
> oidcCallback(UNKNOWN_PARAMETER_NAME, UNKNOWN_PARAMETER_NAME2, UNKNOWN_PARAMETER_NAME3)



Callback to finish OIDC login

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Callback state.
val UNKNOWN_PARAMETER_NAME2 :  =  //  | Callback code.
val UNKNOWN_PARAMETER_NAME3 :  =  //  | Callback error.
try {
    apiInstance.oidcCallback(UNKNOWN_PARAMETER_NAME, UNKNOWN_PARAMETER_NAME2, UNKNOWN_PARAMETER_NAME3)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#oidcCallback")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#oidcCallback")
    e.printStackTrace()
}
```

### Parameters
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Callback state. | |
| **UNKNOWN_PARAMETER_NAME2** | [****](.md)| Callback code. | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME3** | [****](.md)| Callback error. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="oidcLink"></a>
# **oidcLink**
> oidcLink(UNKNOWN_PARAMETER_NAME)



Link existing account to OIDC user

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Optional path to redirect back to after login.
try {
    apiInstance.oidcLink(UNKNOWN_PARAMETER_NAME)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#oidcLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#oidcLink")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Optional path to redirect back to after login. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="oidcLogin"></a>
# **oidcLogin**
> oidcLogin(UNKNOWN_PARAMETER_NAME)



Login using OIDC

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val UNKNOWN_PARAMETER_NAME :  =  //  | Optional path to redirect back to after login.
try {
    apiInstance.oidcLogin(UNKNOWN_PARAMETER_NAME)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#oidcLogin")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#oidcLogin")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **UNKNOWN_PARAMETER_NAME** | [****](.md)| Optional path to redirect back to after login. | |

### Return type

null (empty response body)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="signUpLocalUser"></a>
# **signUpLocalUser**
> JwtResponse signUpLocalUser(loginLocalUser)



Sign up a new local user account.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val loginLocalUser : LoginLocalUser =  // LoginLocalUser | 
try {
    val result : JwtResponse = apiInstance.signUpLocalUser(loginLocalUser)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#signUpLocalUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#signUpLocalUser")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **loginLocalUser** | [**LoginLocalUser**](LoginLocalUser.md)|  | |

### Return type

[**JwtResponse**](JwtResponse.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unenrollPasskey"></a>
# **unenrollPasskey**
> kotlin.String unenrollPasskey(body)



Unenroll user in Passkey 2FA.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : kotlin.String = apiInstance.unenrollPasskey(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#unenrollPasskey")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#unenrollPasskey")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unenrollTotp"></a>
# **unenrollTotp**
> kotlin.String unenrollTotp(body)



Unenroll user in Totp 2FA.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : kotlin.String = apiInstance.unenrollTotp(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#unenrollTotp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#unenrollTotp")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unlinkLogin"></a>
# **unlinkLogin**
> kotlin.String unlinkLogin(unlinkLogin)



Unlink a login provider.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val unlinkLogin : UnlinkLogin =  // UnlinkLogin | 
try {
    val result : kotlin.String = apiInstance.unlinkLogin(unlinkLogin)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#unlinkLogin")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#unlinkLogin")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **unlinkLogin** | [**UnlinkLogin**](UnlinkLogin.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="updateExternalSkip2fa"></a>
# **updateExternalSkip2fa**
> kotlin.String updateExternalSkip2fa(updateExternalSkip2fa)



Update whether the calling user skips 2fa when using external login method.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val updateExternalSkip2fa : UpdateExternalSkip2fa =  // UpdateExternalSkip2fa | 
try {
    val result : kotlin.String = apiInstance.updateExternalSkip2fa(updateExternalSkip2fa)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#updateExternalSkip2fa")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#updateExternalSkip2fa")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updateExternalSkip2fa** | [**UpdateExternalSkip2fa**](UpdateExternalSkip2fa.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="updatePassword"></a>
# **updatePassword**
> kotlin.String updatePassword(updatePassword)



Update the calling user&#39;s password.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val updatePassword : UpdatePassword =  // UpdatePassword | 
try {
    val result : kotlin.String = apiInstance.updatePassword(updatePassword)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#updatePassword")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#updatePassword")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updatePassword** | [**UpdatePassword**](UpdatePassword.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="updateUsername"></a>
# **updateUsername**
> kotlin.String updateUsername(updateUsername)



Update the calling user&#39;s username.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = AuthApi()
val updateUsername : UpdateUsername =  // UpdateUsername | 
try {
    val result : kotlin.String = apiInstance.updateUsername(updateUsername)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#updateUsername")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#updateUsername")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updateUsername** | [**UpdateUsername**](UpdateUsername.md)|  | |

### Return type

**kotlin.String**

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

