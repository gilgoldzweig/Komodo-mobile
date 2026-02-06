package ca.glong.komodo.feature.passkeytest.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterStartRequest(
    val username: String,
    val email: String
)

@Serializable
data class RegisterStartResponse(
    val data: RegisterData
)

@Serializable
data class RegisterData(
    val handshakeId: String,
    val options: PublicKeyCredentialCreationOptions
)

@Serializable
data class PublicKeyCredentialCreationOptions(
    val publicKey: PublicKeyOptions
)

@Serializable
data class PublicKeyOptions(
    val challenge: String,
    val rp: RelyingParty,
    val user: UserEntity,
    val pubKeyCredParams: List<PubKeyCredParam>,
    val timeout: Long? = null,
    val attestation: String? = null
)

@Serializable
data class RelyingParty(
    val id: String,
    val name: String
)

@Serializable
data class UserEntity(
    val id: String,
    val name: String,
    val displayName: String
)

@Serializable
data class PubKeyCredParam(
    val type: String,
    val alg: Int
)

@Serializable
data class RegisterFinishRequest(
    val id: String,
    val rawId: String,
    val type: String = "public-key",
    val response: AttestationResponseDto
)

@Serializable
data class AttestationResponseDto(
    val clientDataJSON: String,
    val attestationObject: String
)
