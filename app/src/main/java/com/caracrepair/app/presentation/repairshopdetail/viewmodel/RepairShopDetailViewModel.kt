package com.caracrepair.app.presentation.repairshopdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.presentation.repairshopdetail.viewparam.RepairShopDetail
import com.caracrepair.app.repositories.RepairShopRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepairShopDetailViewModel @Inject constructor(
    private val repairShopRepository: RepairShopRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _repairShopDetailResult = MutableLiveData<RepairShopDetail>()
    val repairShopDetailResult: LiveData<RepairShopDetail> = _repairShopDetailResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getRepairShopDetail(repairShopId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = repairShopRepository.getRepairShopDetail(repairShopId)
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _repairShopDetailResult.postValue(RepairShopDetail(response?.data))
                }
            })
            _loadingState.postValue(false)
        }
    }
}