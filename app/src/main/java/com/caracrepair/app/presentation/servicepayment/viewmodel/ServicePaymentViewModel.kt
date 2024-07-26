package com.caracrepair.app.presentation.servicepayment.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.UploadPaymentProofImageBody
import com.caracrepair.app.presentation.servicepayment.viewparam.ServicePayment
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicePaymentViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _servicePaymentResult = MutableLiveData<ServicePayment>()
    val servicePaymentResult: LiveData<ServicePayment> = _servicePaymentResult
    private val _uploadPaymentProofImageResult = MutableLiveData<String>()
    val uploadPaymentProofImageResult: LiveData<String> = _uploadPaymentProofImageResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _errorUploadMessage = MutableLiveData<String>()
    val errorUploadMessage: LiveData<String> = _errorUploadMessage

    fun getServicePayment(serviceId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.getServicePayment(serviceId, generalPreference.getUser()?.userId ?: 0)
            if (response != null) {
                if (response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _servicePaymentResult.postValue(ServicePayment(response.data))
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }

    fun uploadPaymentProofImage(serviceId: Int, imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.uploadPaymentProofImage(UploadPaymentProofImageBody(
                serviceId = serviceId,
                imageUri = imageUri
            ))
            if (response != null) {
                if (response.status != true) {
                    _errorUploadMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _uploadPaymentProofImageResult.postValue(response.message.orEmpty())
            } else {
                _errorUploadMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}