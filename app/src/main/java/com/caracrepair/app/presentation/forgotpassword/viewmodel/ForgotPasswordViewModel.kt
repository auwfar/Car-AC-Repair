package com.caracrepair.app.presentation.forgotpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.ForgotPasswordBody
import com.caracrepair.app.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository
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
            if (response != null) {
                if (response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _forgotPasswordResult.postValue(response.data?.userId.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}