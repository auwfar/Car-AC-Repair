package com.caracrepair.app.presentation.mycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCarViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _carsResult = MutableLiveData<List<MyCarItem>>()
    val carsResult: LiveData<List<MyCarItem>> = _carsResult
    private val _removeCarResult = MutableLiveData<String>()
    val removeCarResult: LiveData<String> = _removeCarResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorPageMessage = MutableLiveData<String>()
    val errorPageMessage: LiveData<String> = _errorPageMessage
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getCars() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.getCars()
            apiResponseUtil.setResponseListener(response, _errorPageMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _carsResult.postValue(response?.data?.map { MyCarItem(it) }.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun removeCar(carId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.removeCar(carId)
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _removeCarResult.postValue(response?.message.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}