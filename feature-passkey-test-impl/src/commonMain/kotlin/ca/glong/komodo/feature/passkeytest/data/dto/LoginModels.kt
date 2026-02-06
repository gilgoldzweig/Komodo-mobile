package ca.glong.komodo.feature.passkeytest.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginStartRequest(
    val username: String
)

@Serializable
data class LoginStartResponse(
    val data: LoginData
)

@Serializable
data class LoginData(
    val handshakeId: String,
    val publicKey: PublicKeyRequestOptions
)

@Serializable
data class PublicKeyRequestOptions(
    val challenge: String,
    val rpId: String? = null,
    val allowCredentials: List<AllowCredential>? = null,
    val timeout: Long? = null
)

@Serializable
data class AllowCredential(
    val id: String,
    val type: String = "public-key"
)

@Serializable
data class LoginFinishRequest(
    val handshakeId: String,
    val id: String,
    val rawId: String,
    val type: String = "public-key",
    val response: AssertionResponseDto
)

@Serializable
data class AssertionResponseDto(
    val authenticatorData: String,
    val clientDataJSON: String,
    val signature: String,
    val userHandle: String?
)
