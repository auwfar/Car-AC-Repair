package com.caracrepair.app.presentation.changeprofile.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.ChangeProfileBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.repositories.GeneralRepository
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalRepository: GeneralRepository,
    private val apiResponseUtil: ApiResponseUtil,
    private val generalPreference: GeneralPreference
) : ViewModel() {
    private val _changeProfileResult = MutableLiveData<Unit>()
    val changeProfileResult: LiveData<Unit> = _changeProfileResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun changeProfile(name: String, profileImage: File?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val currentImageProfile = generalPreference.getUser()?.profileImage.orEmpty()
            val currentPhoneNumber = generalPreference.getUser()?.phoneNumber.orEmpty()
            val profileImageUrl = async {
                if (profileImage != null) {
                    generalRepository.uploadImage(profileImage) ?: currentImageProfile
                } else {
                    currentImageProfile
                }
            }.await()
            val response = accountRepository.changeProfile(ChangeProfileBody(name, profileImageUrl, currentPhoneNumber))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    val user = generalPreference.getUser()?.copy(name = name, profileImage = profileImageUrl) ?: return
                    generalPreference.setUser(user)
                    _changeProfileResult.postValue(Unit)
                }
            })
            _loadingState.postValue(false)
        }
    }
}