package com.caracrepair.app.presentation.rescheduleservice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescheduleServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _rescheduleServiceResult = MutableLiveData<String>()
    val rescheduleServiceResult: LiveData<String> = _rescheduleServiceResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var selectedRepairShopId = ""
    var selectedServiceDate: Long? = null

    fun rescheduleService(body: RescheduleServiceBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.rescheduleService(body)
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _rescheduleServiceResult.postValue(response?.data?.orderId.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}