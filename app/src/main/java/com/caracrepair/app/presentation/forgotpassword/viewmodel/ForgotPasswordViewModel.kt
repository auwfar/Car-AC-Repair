package com.caracrepair.app.presentation.forgotpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.ForgotPasswordBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _forgotPasswordResult = MutableLiveData<String>()
    val forgotPasswordResult: LiveData<String> = _forgotPasswordResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun forgotPassword(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.forgotPassword(ForgotPasswordBody(phoneNumber))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _forgotPasswordResult.postValue(response?.data?.userId.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}