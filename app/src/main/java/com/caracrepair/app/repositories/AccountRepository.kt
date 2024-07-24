package com.caracrepair.app.repositories

import com.caracrepair.app.models.bodymodel.SignInBody
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.models.responsemodel.DataResponse
import com.caracrepair.app.models.responsemodel.SignInResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AccountRepository @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun signIn(body: SignInBody): DataResponse<SignInResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.signIn(body)
            } catch (e: Exception) {
                null
            }
        }
    }
}