package com.caracrepair.app.presentation.servicepayment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.models.body.UploadPaymentProofImageBody
import com.caracrepair.app.presentation.servicedetail.viewparam.ServiceDetail
import com.caracrepair.app.repositories.GeneralRepository
import com.caracrepair.app.repositories.ServiceRepository
import com.caracrepair.app.utils.ApiResponseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ServicePaymentViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val generalRepository: GeneralRepository,
    private val apiResponseUtil: ApiResponseUtil
) : ViewModel() {

    private val _uploadPaymentProofImageResult = MutableLiveData<String>()
    val uploadPaymentProofImageResult: LiveData<String> = _uploadPaymentProofImageResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var serviceDetail: ServiceDetail? = null

    fun uploadPaymentProofImage(serviceId: String, proofImageFile: File) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val proofImageUrl = async { generalRepository.uploadImage(proofImageFile) }.await()
            val response = serviceRepository.uploadPaymentProofImage(serviceId, UploadPaymentProofImageBody(proofImageUrl.orEmpty()))
            apiResponseUtil.setResponseListener(response, _errorMessage, object : ApiResponseUtil.ResponseListener {
                override fun onSuccess() {
                    _uploadPaymentProofImageResult.postValue(response?.message.orEmpty())
                }
            })
            _loadingState.postValue(false)
        }
    }
}