package com.caracrepair.app.presentation.bookingservice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _bookingServiceResult = MutableLiveData<String>()
    val bookingServiceResult: LiveData<String> = _bookingServiceResult
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
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _bookingServiceResult.postValue(response?.data?.orderId.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}