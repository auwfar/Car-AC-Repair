package com.caracrepair.app.presentation.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.SignInBody
import com.caracrepair.app.models.viewparam.User
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.FirebaseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val apiResponseUtil: ApiResponseUtil,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _signInResult = MutableLiveData<User>()
    val signInResult: LiveData<User> = _signInResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun signIn(phoneNumber: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.signIn(SignInBody(phoneNumber, password, FirebaseUtil().getInstanceId()))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    val user = User(response?.data)
                    generalPreference.setUser(user)
                    _signInResult.postValue(user)
                }
            })
            _loadingState.postValue(false)
        }
    }
}