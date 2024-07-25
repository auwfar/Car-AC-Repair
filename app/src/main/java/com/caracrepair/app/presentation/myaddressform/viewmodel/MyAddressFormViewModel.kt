package com.caracrepair.app.presentation.myaddressform.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.AddAddressBody
import com.caracrepair.app.models.body.LocationBody
import com.caracrepair.app.models.body.UpdateAddressBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAddressFormViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _updateAddressResult = MutableLiveData<String>()
    val updateAddressResult: LiveData<String> = _updateAddressResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun updateAddress(
        addressId: Int?,
        label: String,
        address: String,
        addressNote: String,
        location: LocationBody
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = if (addressId != null) {
                accountRepository.updateAddress(UpdateAddressBody(
                    addressId,
                    label,
                    address,
                    addressNote,
                    location
                ))
            } else {
                accountRepository.addAddress(AddAddressBody(
                    generalPreference.getUser()?.userId ?: 0,
                    label,
                    address,
                    addressNote,
                    location
                ))
            }
            if (response != null) {
                _updateAddressResult.postValue(response.message.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}