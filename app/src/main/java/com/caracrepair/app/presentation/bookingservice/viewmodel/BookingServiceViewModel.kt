package com.caracrepair.app.presentation.bookingservice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.ServiceTimesBody
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _bookingServiceResult = MutableLiveData<Int>()
    val bookingServiceResult: LiveData<Int> = _bookingServiceResult
    private val _serviceTimeResult = MutableLiveData<List<ServiceTimeItem>>()
    val serviceTimeResult: LiveData<List<ServiceTimeItem>> = _serviceTimeResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var selectedCar: MyCarItem? = null
    var selectedAddress: MyAddressItem? = null
    var selectedRepairShopId = ""
    var selectedServiceDate: Long? = null

    fun bookingService(body: BookingServiceBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.bookingService(body)
            when {
                response != null && response.status == true -> {
                    _bookingServiceResult.postValue(response.data?.orderId ?: 0)
                }
                response != null && response.status != true -> {
                    _errorMessage.postValue(response.message.orEmpty())
                }
                else -> {
                    _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
                }
            }
            _loadingState.postValue(false)
        }
    }

    fun getServiceTimes(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.getServiceTimes(ServiceTimesBody(
                generalPreference.getUser()?.userId.orEmpty(),
                selectedRepairShopId,
                date
            ))
            when {
                response != null && response.status == true -> {
                    _serviceTimeResult.postValue(response.data?.map { ServiceTimeItem(it) })
                }
                response != null && response.status != true -> {
                    _errorMessage.postValue(response.message.orEmpty())
                }
                else -> {
                    _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
                }
            }
            _loadingState.postValue(false)
        }
    }
}