package com.caracrepair.app.presentation.chooserepairshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.presentation.chooserepairshop.viewparam.RepairShopOptionItem
import com.caracrepair.app.repositories.RepairShopRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseRepairShopViewModel @Inject constructor(
    private val repairShopRepository: RepairShopRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _repairShopsResult = MutableLiveData<List<RepairShopOptionItem>>()
    val repairShopsResult: LiveData<List<RepairShopOptionItem>> = _repairShopsResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getRepairShops() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = repairShopRepository.getRepairShops()
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _repairShopsResult.postValue(response?.data?.map { RepairShopOptionItem(it) }.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}