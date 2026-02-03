package ca.glong.komodo.domain.repository

import ca.glong.komodo.data.dto.GetLoginOptionsResponse

interface AuthRepository {
    suspend fun getLoginOptions(url: String): Result<GetLoginOptionsResponse>
    suspend fun loginLocal(username: String, password: String): Result<Unit>
}
