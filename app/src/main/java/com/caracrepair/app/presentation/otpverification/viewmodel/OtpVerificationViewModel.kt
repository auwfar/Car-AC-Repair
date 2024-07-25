package com.caracrepair.app.presentation.otpverification.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.body.ResendOtpSignUpBody
import com.caracrepair.app.models.body.VerifyOtpForgotPasswordBody
import com.caracrepair.app.models.body.VerifyOtpSignUpBody
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _otpSignUpVerificationResult = MutableLiveData<Unit>()
    val otpSignUpVerificationResult: LiveData<Unit> = _otpSignUpVerificationResult
    private val _otpForgotPasswordVerificationResult = MutableLiveData<Int>()
    val otpForgotPasswordVerificationResult: LiveData<Int> = _otpForgotPasswordVerificationResult
    private val _resendOtpResult = MutableLiveData<String>()
    val resendOtpResult: LiveData<String> = _resendOtpResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun verifyOtpSignUp(otpCode: String, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.verifyOtpSignUp(VerifyOtpSignUpBody(otpCode, userId))
            if (response != null) {
                if (response.message == null || response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _otpSignUpVerificationResult.postValue(Unit)
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }

    fun verifyOtpForgotPassword(otpCode: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.verifyOtpForgotPassword(VerifyOtpForgotPasswordBody(otpCode, phoneNumber))
            if (response != null) {
                if (response.message == null || response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _otpForgotPasswordVerificationResult.postValue(response.data?.userId ?: 0)
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
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
                    ResendOtpForgotPasswordBody(otpType.phoneNumber)
                )
            }
            if (response != null) {
                _resendOtpResult.postValue(response.message.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}