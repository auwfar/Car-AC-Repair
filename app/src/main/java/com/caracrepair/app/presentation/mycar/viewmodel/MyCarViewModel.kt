package com.caracrepair.app.presentation.mycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCarViewModel @Inject constructor(
    private val accountRepository: AccountRepository
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
            when {
                response != null && response.status == true -> {
                    _carsResult.postValue(response.data?.map { MyCarItem(it) }.orEmpty())
                }
                response != null && response.status != true -> {
                    _errorPageMessage.postValue(response.message.orEmpty())
                }
                else -> {
                    _errorPageMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
                }
            }
            _loadingState.postValue(false)
        }
    }

    fun removeCar(carId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.removeCar(carId)
            when {
                response != null && response.status == true -> {
                    _removeCarResult.postValue(response.message.orEmpty())
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