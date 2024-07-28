package com.caracrepair.app.presentation.rescheduleservice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.models.body.ServiceTimesBody
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescheduleServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val apiResponseUtil: ApiResponseUtil,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _rescheduleServiceResult = MutableLiveData<Int>()
    val rescheduleServiceResult: LiveData<Int> = _rescheduleServiceResult
    private val _serviceTimeResult = MutableLiveData<List<ServiceTimeItem>>()
    val serviceTimeResult: LiveData<List<ServiceTimeItem>> = _serviceTimeResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var selectedRepairShopId = ""

    fun rescheduleService(body: RescheduleServiceBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.rescheduleService(body)
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _rescheduleServiceResult.postValue(response?.data?.orderId ?: 0)
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun getServiceTimes(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.getServiceTimes(
                ServiceTimesBody(
                generalPreference.getUser()?.userId.orEmpty(),
                selectedRepairShopId,
                date
            ))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _serviceTimeResult.postValue(response?.data?.map { ServiceTimeItem(it) }.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}