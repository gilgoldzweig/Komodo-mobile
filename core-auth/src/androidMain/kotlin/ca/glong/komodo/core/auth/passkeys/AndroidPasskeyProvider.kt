package ca.glong.komodo.core.auth.passkeys

import android.content.Context
import android.util.Base64
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CreatePublicKeyCredentialResponse
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import ca.glong.komodo.core.auth.error.AuthError
import org.json.JSONArray
import org.json.JSONObject

class AndroidPasskeyProvider(
    private val context: Context
) : AuthProvider {
    
    private val credentialManager = CredentialManager.create(context)
    
    override suspend fun createCredential(
        challenge: ByteArray,
        rpId: String,
        userId: String
    ): Result<AttestationResponse> = runCatching {
        val requestJson = buildCreateCredentialJson(challenge, rpId, userId)
        
        val request = CreatePublicKeyCredentialRequest(requestJson)
        val result = credentialManager.createCredential(context, request)
        
        val response = result as CreatePublicKeyCredentialResponse
        parseAttestationResponse(response)
    }.fold(
        onSuccess = { Result.success(it) },
        onFailure = { error ->
            Result.failure(mapPasskeyError(error))
        }
    )
    
    override suspend fun getAssertion(
        challenge: ByteArray,
        rpId: String
    ): Result<AssertionResponse> = runCatching {
        val requestJson = buildGetAssertionJson(challenge, rpId)
        
        val option = GetPublicKeyCredentialOption(requestJson)
        val request = GetCredentialRequest(listOf(option))
        val result = credentialManager.getCredential(context, request)
        
        val credential = result.credential as PublicKeyCredential
        parseAssertionResponse(credential)
    }.fold(
        onSuccess = { Result.success(it) },
        onFailure = { error ->
            Result.failure(mapPasskeyError(error))
        }
    )
    
    private fun buildCreateCredentialJson(
        challenge: ByteArray,
        rpId: String,
        userId: String
    ): String {
        val challengeBase64 = challenge.toBase64Url()
        val userIdBase64 = userId.encodeToByteArray().toBase64Url()
        
        return JSONObject().apply {
            put("challenge", challengeBase64)
            put("rp", JSONObject().apply {
                put("id", rpId)
                put("name", rpId)
            })
            put("user", JSONObject().apply {
                put("id", userIdBase64)
                put("displayName", rpId)
            })
            put("pubKeyCredParams", JSONArray().apply {
                put(JSONObject().apply {
                    put("type", "public-key")
                    put("alg", -7)
                })
                put(JSONObject().apply {
                    put("type", "public-key")
                    put("alg", -257)
                })
            })
            put("timeout", 60000)
            put("attestation", "none")
            put("authenticatorSelection", JSONObject().apply {
                put("authenticatorAttachment", "platform")
                put("requireResidentKey", true)
                put("residentKey", "required")
                put("userVerification", "required")
            })
        }.toString()
    }
    
    private fun buildGetAssertionJson(
        challenge: ByteArray,
        rpId: String
    ): String {
        val challengeBase64 = challenge.toBase64Url()
        
        return JSONObject().apply {
            put("challenge", challengeBase64)
            put("rpId", rpId)
            put("timeout", 60000)
            put("userVerification", "required")
        }.toString()
    }
    
    private fun parseAttestationResponse(
        response: CreatePublicKeyCredentialResponse
    ): AttestationResponse {
        val json = JSONObject(response.registrationResponseJson)
        
        val id = json.getString("id")
        val rawId = id.fromBase64Url()
        val responseObj = json.getJSONObject("response")
        val clientDataJson = responseObj.getString("clientDataJSON").fromBase64Url()
        val attestationObject = responseObj.getString("attestationObject").fromBase64Url()
        
        return AttestationResponse(
            id = id,
            rawId = rawId,
            response = AttestationObject(
                clientDataJSON = clientDataJson,
                attestationObject = attestationObject
            )
        )
    }
    
    private fun parseAssertionResponse(
        credential: PublicKeyCredential
    ): AssertionResponse {
        val json = JSONObject(credential.authenticationResponseJson)
        
        val id = json.getString("id")
        val rawId = id.fromBase64Url()
        val responseObj = json.getJSONObject("response")
        val clientDataJson = responseObj.getString("clientDataJSON").fromBase64Url()
        val authenticatorData = responseObj.getString("authenticatorData").fromBase64Url()
        val signature = responseObj.getString("signature").fromBase64Url()
        val userHandle = if (responseObj.has("userHandle")) {
            responseObj.getString("userHandle").fromBase64Url()
        } else {
            null
        }
        
        return AssertionResponse(
            id = id,
            rawId = rawId,
            response = AssertionObject(
                clientDataJSON = clientDataJson,
                authenticatorData = authenticatorData,
                signature = signature,
                userHandle = userHandle
            )
        )
    }
    
    private fun mapPasskeyError(error: Throwable): AuthError.PasskeyError {
        return when (error) {
            is CreateCredentialCancellationException,
            is GetCredentialCancellationException -> 
                AuthError.PasskeyError.UserCancelled()
            
            is NoCredentialException -> 
                AuthError.PasskeyError.NoCredentials()
            
            is CreateCredentialException,
            is GetCredentialException -> 
                AuthError.PasskeyError.OperationFailed(error.message ?: "Unknown credential error")
            
            else -> 
                AuthError.PasskeyError.OperationFailed(error.message ?: "Unknown error")
        }
    }
    
    private fun ByteArray.toBase64Url(): String {
        return Base64.encodeToString(this, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }
    
    private fun String.fromBase64Url(): ByteArray {
        return Base64.decode(this, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }
}
