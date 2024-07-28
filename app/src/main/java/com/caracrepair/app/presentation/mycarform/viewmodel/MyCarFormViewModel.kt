package com.caracrepair.app.presentation.mycarform.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.body.AddCarBody
import com.caracrepair.app.models.body.UpdateCarBody
import com.caracrepair.app.repositories.AccountRepository
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCarFormViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val generalPreference: GeneralPreference
) : ViewModel() {

    private val _updateCarResult = MutableLiveData<String>()
    val updateCarResult: LiveData<String> = _updateCarResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun updateCar(carId: Int?, name: String, licenseNumber: String, year: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = if (carId != null) {
                accountRepository.updateCar(UpdateCarBody(
                    carId,
                    name,
                    licenseNumber,
                    year
                ))
            } else {
                accountRepository.addCar(AddCarBody(
                    generalPreference.getUser()?.userId.orEmpty(),
                    name,
                    licenseNumber,
                    year
                ))
            }
            if (response != null) {
                _updateCarResult.postValue(response.message.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}