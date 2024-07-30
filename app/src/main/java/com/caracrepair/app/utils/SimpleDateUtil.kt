package com.caracrepair.app.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

enum class DateUtil(val simpleDateFormat: SimpleDateFormat) {
    DAY_FULL_MONTH_YEAR(SimpleDateFormat("dd MMMM yyyy", Locale("id")).apply {
        timeZone = TimeZone.getTimeZone("GMT+07:00")
    }),
    HOURS_DAY_FULL_MONTH_YEAR(SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id")).apply {
        timeZone = TimeZone.getTimeZone("GMT+07:00")
    }),
    SERVER(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("id")).apply {
        timeZone = TimeZone.getTimeZone("GMT+07:00")
    })
}

object SimpleDateUtil {
    fun parseDate(date: String, fromDateUtil: DateUtil, toDateUtil: DateUtil): String? {
        return fromDateUtil.simpleDateFormat.parse(date)?.let {
            toDateUtil.simpleDateFormat.format(it)
        }
    }
}