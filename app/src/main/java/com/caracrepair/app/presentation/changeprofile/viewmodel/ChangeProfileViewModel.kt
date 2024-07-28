package com.caracrepair.app.presentation.changeprofile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.viewparam.User
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class ChangeProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {
    private val _changeProfileResult = MutableLiveData<User>()
    val changeProfileResult: LiveData<User> = _changeProfileResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun changeProfile(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.changeProfile(name)
            if (response != null && response.status == true) {
                val user = User(response.data)
                generalPreference.setUser(user)
                _changeProfileResult.postValue(user)
            } else if (response != null && response.status != true) {
                _errorMessage.postValue(response.message.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}