package com.caracrepair.app.presentation.chooserepairshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.presentation.chooserepairshop.viewparam.RepairShopOptionItem
import com.caracrepair.app.presentation.main.repairshop.viewparam.RepairShopItem
import com.caracrepair.app.repositories.RepairShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseRepairShopViewModel @Inject constructor(
    private val repairShopRepository: RepairShopRepository
) : ViewModel() {

    private val _repairShopsResult = MutableLiveData<List<RepairShopOptionItem>>()
    val repairShopsResult: LiveData<List<RepairShopOptionItem>> = _repairShopsResult
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getRepairShops() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.postValue(true)
            val response = repairShopRepository.getRepairShops()
            if (response != null) {
                if (response.status != true) {
                    _errorMessage.postValue(response.message.orEmpty())
                    return@launch
                }
                _repairShopsResult.postValue(response.data?.map { RepairShopOptionItem(it) }.orEmpty())
            } else {
                _errorMessage.postValue(StringConst.GENERAL_ERROR_MESSAGE)
            }
            _loadingState.postValue(false)
        }
    }
}