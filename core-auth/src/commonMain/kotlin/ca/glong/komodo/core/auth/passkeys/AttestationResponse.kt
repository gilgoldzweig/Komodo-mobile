package ca.glong.komodo.core.auth.passkeys

import kotlinx.serialization.Serializable

@Serializable
data class AttestationResponse(
    val id: String,
    val rawId: ByteArray,
    val response: AttestationObject,
    val type: String = "public-key"
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AttestationResponse) return false

        if (id != other.id) return false
        if (!rawId.contentEquals(other.rawId)) return false
        if (response != other.response) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + rawId.contentHashCode()
        result = 31 * result + response.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

@Serializable
data class AttestationObject(
    val clientDataJSON: ByteArray,
    val attestationObject: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AttestationObject) return false

        if (!clientDataJSON.contentEquals(other.clientDataJSON)) return false
        if (!attestationObject.contentEquals(other.attestationObject)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clientDataJSON.contentHashCode()
        result = 31 * result + attestationObject.contentHashCode()
        return result
    }
}
