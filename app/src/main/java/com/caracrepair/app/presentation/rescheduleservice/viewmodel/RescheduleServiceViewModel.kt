package com.caracrepair.app.presentation.rescheduleservice.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RescheduleServiceViewModel @Inject constructor() : ViewModel() {
    var selectedRepairShopId = 0
}