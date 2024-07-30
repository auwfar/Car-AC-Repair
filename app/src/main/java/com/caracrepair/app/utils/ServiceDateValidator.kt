package com.caracrepair.app.utils

import com.google.android.material.datepicker.CalendarConstraints
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
class ServiceDateValidator : CalendarConstraints.DateValidator {
    override fun isValid(date: Long): Boolean {
        val now = Calendar.getInstance().timeInMillis
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
        }
        return date >= now && calendar.get(Calendar.DAY_OF_WEEK) !in listOf(Calendar.SUNDAY)
    }
}
