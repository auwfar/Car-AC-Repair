package com.caracrepair.app.network

import com.caracrepair.app.models.bodymodel.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.bodymodel.ResendOtpSignUpBody
import com.caracrepair.app.models.bodymodel.VerifyOtpSignUpBody
import com.caracrepair.app.models.bodymodel.SignInBody
import com.caracrepair.app.models.bodymodel.SignUpBody
import com.caracrepair.app.models.bodymodel.VerifyOtpForgotPasswordBody
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

    @POST("api/verify-otp-register")
    suspend fun verifyOtpSignUp(@Body request: VerifyOtpSignUpBody): StatusResponse

    @POST("api/verify-otp-forgot-password")
    suspend fun verifyOtpForgotPassword(@Body request: VerifyOtpForgotPasswordBody): StatusResponse

    @POST("api/resend-otp-register")
    suspend fun resendOtpSignUp(@Body request: ResendOtpSignUpBody): StatusResponse

    @POST("api/resend-otp-forgot-password")
    suspend fun resendOtpForgotPassword(@Body request: ResendOtpForgotPasswordBody): StatusResponse
}