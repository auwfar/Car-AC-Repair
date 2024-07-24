package com.caracrepair.app.repositories

import com.caracrepair.app.models.bodymodel.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.bodymodel.ResendOtpSignUpBody
import com.caracrepair.app.models.bodymodel.ResetPasswordBody
import com.caracrepair.app.models.bodymodel.VerifyOtpForgotPasswordBody
import com.caracrepair.app.models.bodymodel.VerifyOtpSignUpBody
import com.caracrepair.app.models.bodymodel.SignInBody
import com.caracrepair.app.models.bodymodel.SignUpBody
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.models.responsemodel.DataResponse
import com.caracrepair.app.models.responsemodel.SignInResponse
import com.caracrepair.app.models.responsemodel.SignUpResponse
import com.caracrepair.app.models.responsemodel.StatusResponse
import com.caracrepair.app.models.responsemodel.VerifyOtpForgotPasswordResponse
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

    suspend fun signUp(body: SignUpBody): DataResponse<SignUpResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.signUp(body)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun verifyOtpSignUp(verifyOtpSignUpBody: VerifyOtpSignUpBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.verifyOtpSignUp(verifyOtpSignUpBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun verifyOtpForgotPassword(verifyOtpForgotPasswordBody: VerifyOtpForgotPasswordBody): DataResponse<VerifyOtpForgotPasswordResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.verifyOtpForgotPassword(verifyOtpForgotPasswordBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun resendOtpSignUp(resendOtpSignUpBody: ResendOtpSignUpBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.resendOtpSignUp(resendOtpSignUpBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun resendOtpForgotPassword(resendOtpForgotPasswordBody: ResendOtpForgotPasswordBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.resendOtpForgotPassword(resendOtpForgotPasswordBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun resetPassword(resetPasswordBody: ResetPasswordBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.resetPassword(resetPasswordBody)
            } catch (e: Exception) {
                null
            }
        }
    }
}