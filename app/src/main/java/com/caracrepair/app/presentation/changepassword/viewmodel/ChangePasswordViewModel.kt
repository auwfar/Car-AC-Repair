package com.caracrepair.app.presentation.changepassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.ChangePasswordBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _changePasswordResult = MutableLiveData<Unit>()
    val changePasswordResult: LiveData<Unit> = _changePasswordResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun changePassword(password: String, newPassword: String, newPasswordConfirmation: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.changePassword(ChangePasswordBody(
                password,
                newPassword,
                newPasswordConfirmation
            ))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _changePasswordResult.postValue(Unit)
                }
            })
            _loadingState.postValue(false)
        }
    }
}