package com.caracrepair.app.presentation.bookingservice.viewparam

data class ServiceTimeItem(
    val time: String,
    val isEnabled: Boolean
) {
    companion object {
        val TIMES_DEFAULT = listOf(
            ServiceTimeItem("08:00", true),
            ServiceTimeItem("09:00", true),
            ServiceTimeItem("10:00", true),
            ServiceTimeItem("11:00", true),
            ServiceTimeItem("12:00", true),
            ServiceTimeItem("13:00", true),
            ServiceTimeItem("14:00", true),
            ServiceTimeItem("15:00", true),
            ServiceTimeItem("16:00", true)
        )
    }
}
