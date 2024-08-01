package com.caracrepair.app.presentation.otpverification.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.RequestOtpBody
import com.caracrepair.app.models.body.VerifyOtpBody
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

    private val _otpVerificationResult = MutableLiveData<OTPType>()
    val otpVerificationResult: LiveData<OTPType> = _otpVerificationResult
    private val _resendOtpResult = MutableLiveData<String>()
    val resendOtpResult: LiveData<String> = _resendOtpResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var otpType: OTPType? = null
    var phoneNumber: String = ""

    fun verifyOtpSignUp(otpCode: String) {
        val otpType = otpType ?: return
        val phoneNumber = when (otpType) {
            is OTPType.SignUp -> otpType.phoneNumber
            is OTPType.ForgotPassword -> otpType.phoneNumber
        }
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.verifyOtp(VerifyOtpBody(otpCode, phoneNumber))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _otpVerificationResult.postValue(otpType)
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun resendOtp() {
        val otpType = otpType ?: return
        val phoneNumber = when (otpType) {
            is OTPType.SignUp -> otpType.phoneNumber
            is OTPType.ForgotPassword -> otpType.phoneNumber
        }
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.requestOtp(RequestOtpBody(phoneNumber))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _resendOtpResult.postValue(response?.message.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}