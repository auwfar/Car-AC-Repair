package com.caracrepair.app.repositories

import com.caracrepair.app.models.body.AddAddressBody
import com.caracrepair.app.models.body.AddCarBody
import com.caracrepair.app.models.body.ChangePasswordBody
import com.caracrepair.app.models.body.ForgotPasswordBody
import com.caracrepair.app.models.body.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.body.ResendOtpSignUpBody
import com.caracrepair.app.models.body.ResetPasswordBody
import com.caracrepair.app.models.body.VerifyOtpForgotPasswordBody
import com.caracrepair.app.models.body.VerifyOtpSignUpBody
import com.caracrepair.app.models.body.SignInBody
import com.caracrepair.app.models.body.SignUpBody
import com.caracrepair.app.models.body.UpdateAddressBody
import com.caracrepair.app.models.body.UpdateCarBody
import com.caracrepair.app.models.response.AddressResponse
import com.caracrepair.app.models.response.CarResponse
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.ForgotPasswordResponse
import com.caracrepair.app.models.response.SignInResponse
import com.caracrepair.app.models.response.SignUpResponse
import com.caracrepair.app.models.response.StatusResponse
import com.caracrepair.app.models.response.VerifyOtpForgotPasswordResponse
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

    suspend fun forgotPassword(forgotPasswordBody: ForgotPasswordBody): DataResponse<ForgotPasswordResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.forgotPassword(forgotPasswordBody)
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

    suspend fun changePassword(changePasswordBody: ChangePasswordBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.changePassword(changePasswordBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getCars(): DataResponse<List<CarResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getCars()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getAddresses(): DataResponse<List<AddressResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getAddresses()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun addCar(addCarBody: AddCarBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.addCar(addCarBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun updateCar(updateCarBody: UpdateCarBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.updateCar(updateCarBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun addAddress(addAddressBody: AddAddressBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.addAddress(addAddressBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun updateAddress(updateAddressBody: UpdateAddressBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.updateAddress(updateAddressBody)
            } catch (e: Exception) {
                null
            }
        }
    }
}