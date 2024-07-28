package com.caracrepair.app.presentation.changeprofile.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.ChangeProfileBody
import com.caracrepair.app.models.viewparam.User
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.repositories.GeneralRepository
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalRepository: GeneralRepository,
    private val apiResponseUtil: ApiResponseUtil,
    private val generalPreference: GeneralPreference
) : ViewModel() {
    private val _changeProfileResult = MutableLiveData<User>()
    val changeProfileResult: LiveData<User> = _changeProfileResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun changeProfile(name: String, profileImage: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)

            val currentImageProfile = generalPreference.getUser()?.profileImage.orEmpty()
            val profileImageUrl = if (profileImage != null) {
                generalRepository.uploadImage(profileImage) ?: currentImageProfile
            } else {
                currentImageProfile
            }

            val response = accountRepository.changeProfile(ChangeProfileBody(name, profileImageUrl))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    val user = User(response?.data)
                    generalPreference.setUser(user)
                    _changeProfileResult.postValue(user)
                }
            })
            _loadingState.postValue(false)
        }
    }
}