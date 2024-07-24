package com.caracrepair.app.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.bodymodel.SignUpBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.FirebaseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _signUpResult = MutableLiveData<Int>()
    val signUpResult: LiveData<Int> = _signUpResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun signUp(name: String, phoneNumber: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.signUp(SignUpBody(name, phoneNumber, password, FirebaseUtil().getInstanceId()))
            if (response != null) {
                if (response.message == null || response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _signUpResult.postValue(response.data?.userId ?: 0)
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}