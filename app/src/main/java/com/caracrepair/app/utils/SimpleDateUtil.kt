package com.caracrepair.app.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object SimpleDateUtil {
    val dayFullMonthYearFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id")).apply {
        timeZone = TimeZone.getTimeZone("GMT+07:00")
    }
}