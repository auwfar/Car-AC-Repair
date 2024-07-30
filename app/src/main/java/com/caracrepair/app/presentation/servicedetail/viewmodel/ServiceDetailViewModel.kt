package com.caracrepair.app.presentation.servicedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.presentation.servicedetail.viewparam.ServiceDetail
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _serviceDetailResult = MutableLiveData<ServiceDetail>()
    val serviceDetailResult: LiveData<ServiceDetail> = _serviceDetailResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getServiceDetail(serviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.getServiceDetail(serviceId)
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _serviceDetailResult.postValue(ServiceDetail(response?.data))
                }
            })
            _loadingState.postValue(false)
        }
    }
}