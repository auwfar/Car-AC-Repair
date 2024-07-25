package com.caracrepair.app.presentation.bookingservice.viewparam

import com.caracrepair.app.models.response.ServiceTimeResponse

data class ServiceTimeItem(
    val time: String,
    val isEnabled: Boolean
) {
    constructor(response: ServiceTimeResponse) : this(
        time = response.time.orEmpty(),
        isEnabled = response.isAvailable ?: false
    )
}