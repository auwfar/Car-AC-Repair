package com.caracrepair.app.presentation.changepassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.ChangePasswordBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _changePasswordResult = MutableLiveData<Unit>()
    val changePasswordResult: LiveData<Unit> = _changePasswordResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun changePassword(password: String, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.changePassword(ChangePasswordBody(
                generalPreference.getUser()?.userId.orEmpty(),
                password,
                newPassword
            ))
            if (response != null) {
                if (response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _changePasswordResult.postValue(Unit)
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}