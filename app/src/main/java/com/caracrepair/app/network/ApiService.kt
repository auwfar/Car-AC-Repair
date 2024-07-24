package com.caracrepair.app.network

import com.caracrepair.app.models.bodymodel.SignInBody
import com.caracrepair.app.models.responsemodel.DataResponse
import com.caracrepair.app.models.responsemodel.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun signIn(@Body request: SignInBody): DataResponse<SignInResponse>
}