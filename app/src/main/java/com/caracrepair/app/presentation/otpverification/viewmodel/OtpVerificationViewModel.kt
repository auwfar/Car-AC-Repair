package com.caracrepair.app.presentation.otpverification.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.body.ResendOtpSignUpBody
import com.caracrepair.app.models.body.VerifyOtpForgotPasswordBody
import com.caracrepair.app.models.body.VerifyOtpSignUpBody
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _otpSignUpVerificationResult = MutableLiveData<Unit>()
    val otpSignUpVerificationResult: LiveData<Unit> = _otpSignUpVerificationResult
    private val _otpForgotPasswordVerificationResult = MutableLiveData<String>()
    val otpForgotPasswordVerificationResult: LiveData<String> = _otpForgotPasswordVerificationResult
    private val _resendOtpResult = MutableLiveData<String>()
    val resendOtpResult: LiveData<String> = _resendOtpResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun verifyOtpSignUp(otpCode: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.verifyOtpSignUp(VerifyOtpSignUpBody(otpCode, userId))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _otpSignUpVerificationResult.postValue(Unit)
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun verifyOtpForgotPassword(otpCode: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.verifyOtpForgotPassword(VerifyOtpForgotPasswordBody(otpCode, userId))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _otpForgotPasswordVerificationResult.postValue(response?.data?.userId.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun resendOtp(otpType: OTPType) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = when (otpType) {
                is OTPType.SignUp -> accountRepository.resendOtpSignUp(
                    ResendOtpSignUpBody(otpType.userId)
                )
                is OTPType.ForgotPassword -> accountRepository.resendOtpForgotPassword(
                    ResendOtpForgotPasswordBody(otpType.userId)
                )
            }
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _resendOtpResult.postValue(response?.message.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}