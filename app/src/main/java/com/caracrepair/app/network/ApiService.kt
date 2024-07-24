package com.caracrepair.app.network

import com.caracrepair.app.models.bodymodel.SignInBody
import com.caracrepair.app.models.bodymodel.SignUpBody
import com.caracrepair.app.models.responsemodel.DataResponse
import com.caracrepair.app.models.responsemodel.SignInResponse
import com.caracrepair.app.models.responsemodel.SignUpResponse
import com.caracrepair.app.models.responsemodel.StatusResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun signIn(@Body request: SignInBody): DataResponse<SignInResponse>

    @POST("api/register")
    suspend fun signUp(@Body request: SignUpBody): DataResponse<SignUpResponse>
}