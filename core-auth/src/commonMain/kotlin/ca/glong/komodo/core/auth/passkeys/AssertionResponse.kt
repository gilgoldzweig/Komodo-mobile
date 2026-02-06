package ca.glong.komodo.core.auth.passkeys

import kotlinx.serialization.Serializable

@Serializable
data class AssertionResponse(
    val id: String,
    val rawId: ByteArray,
    val response: AssertionObject,
    val type: String = "public-key"
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AssertionResponse) return false

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
data class AssertionObject(
    val clientDataJSON: ByteArray,
    val authenticatorData: ByteArray,
    val signature: ByteArray,
    val userHandle: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AssertionObject) return false

        if (!clientDataJSON.contentEquals(other.clientDataJSON)) return false
        if (!authenticatorData.contentEquals(other.authenticatorData)) return false
        if (!signature.contentEquals(other.signature)) return false
        if (userHandle != null) {
            if (other.userHandle == null) return false
            if (!userHandle.contentEquals(other.userHandle)) return false
        } else if (other.userHandle != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clientDataJSON.contentHashCode()
        result = 31 * result + authenticatorData.contentHashCode()
        result = 31 * result + signature.contentHashCode()
        result = 31 * result + (userHandle?.contentHashCode() ?: 0)
        return result
    }
}
