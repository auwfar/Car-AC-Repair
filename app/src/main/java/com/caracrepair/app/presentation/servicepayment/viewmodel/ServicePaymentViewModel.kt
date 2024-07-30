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
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicePaymentViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val apiResponseUtil: ApiResponseUtil,
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

    fun getServicePayment(serviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.getServicePayment(serviceId, generalPreference.getUser()?.userId.orEmpty())
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _servicePaymentResult.postValue(ServicePayment(response?.data))
                }
            })
            _loadingState.postValue(false)
        }
    }

    fun uploadPaymentProofImage(serviceId: String, imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = serviceRepository.uploadPaymentProofImage(UploadPaymentProofImageBody(
                serviceId = serviceId,
                imageUri = imageUri
            ))
            apiResponseUtil.setResponseListener(response, _errorUploadMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _uploadPaymentProofImageResult.postValue(response?.message.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}