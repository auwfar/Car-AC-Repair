package com.caracrepair.app.presentation.myaddress.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAddressViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _addressesResult = MutableLiveData<List<MyAddressItem>>()
    val addressesResult: LiveData<List<MyAddressItem>> = _addressesResult
    private val _removeAddressResult = MutableLiveData<String>()
    val removeAddressResult: LiveData<String> = _removeAddressResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorPageMessage = MutableLiveData<String>()
    val errorPageMessage: LiveData<String> = _errorPageMessage
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.getAddresses()
            apiResponseUtil.setResponseListener(response, _errorPageMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _addressesResult.postValue(response?.data?.map { MyAddressItem(it) }.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun removeAddress(addressId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = accountRepository.removeAddress(addressId)
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _removeAddressResult.postValue(response?.message.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}