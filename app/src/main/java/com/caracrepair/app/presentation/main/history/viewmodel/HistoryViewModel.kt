package com.caracrepair.app.presentation.main.history.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.presentation.main.history.viewparam.HistoryItem
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _serviceHistoryResult = MutableLiveData<List<HistoryItem>>()
    val serviceHistoryResult: LiveData<List<HistoryItem>> = _serviceHistoryResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getBookingHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.getBookingHistory()
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _serviceHistoryResult.postValue(response?.data?.map { HistoryItem(it) }.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}