package com.caracrepair.app.presentation.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.bodymodel.SignInBody
import com.caracrepair.app.models.viewparam.User
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.FirebaseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _signInResult = MutableLiveData<User>()
    val signInResult: LiveData<User> = _signInResult
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun signIn(phoneNumber: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = accountRepository.signIn(SignInBody(phoneNumber, password, FirebaseUtil().getInstanceId()))
            if (response != null) {
                val user = User(response.data)
                generalPreference.setUser(user)
                _signInResult.postValue(user)
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
        }
    }
}