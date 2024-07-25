package com.caracrepair.app.presentation.main.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.viewparam.User
import com.caracrepair.app.presentation.main.home.viewparam.LastServiceItem
import com.caracrepair.app.presentation.main.home.viewparam.OnProgressServiceItem
import com.caracrepair.app.presentation.main.home.viewparam.RepairShopSliderItem
import com.caracrepair.app.repositories.HomeRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _onProgressServicesResult = MutableLiveData<List<OnProgressServiceItem>>()
    val onProgressServicesResult: LiveData<List<OnProgressServiceItem>> = _onProgressServicesResult
    private val _lastServicesResult = MutableLiveData<List<LastServiceItem>>()
    val lastServicesResult: LiveData<List<LastServiceItem>> = _lastServicesResult
    private val _repairShopsResult = MutableLiveData<List<RepairShopSliderItem>>()
    val repairShopsResult: LiveData<List<RepairShopSliderItem>> = _repairShopsResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getHomePage() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = homeRepository.getHomePage()
            if (response != null) {
                if (response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _onProgressServicesResult.postValue(response.data?.onProgressServices?.map { OnProgressServiceItem(it) }.orEmpty())
                _lastServicesResult.postValue(response.data?.lastServices?.map { LastServiceItem(it) }.orEmpty())
                _repairShopsResult.postValue(response.data?.carShops?.map { RepairShopSliderItem(it) }.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }

    fun getUser(): User? {
        return generalPreference.getUser()
    }
}