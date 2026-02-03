package ca.glong.komodo.data.dto

import kotlinx.serialization.Serializable

@Serializable
class EmptyParams

@Serializable
data class GetLoginOptionsResponse(
    val local: Boolean,
    val github: Boolean,
    val google: Boolean,
    val oidc: Boolean,
    val registration_disabled: Boolean
)

@Serializable
data class LoginLocalUserParams(
    val username: String,
    val password: String
)

@Serializable
data class JwtResponse(
    val token: String
)
