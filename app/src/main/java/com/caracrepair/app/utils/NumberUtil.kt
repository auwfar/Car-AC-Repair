package com.caracrepair.app.utils

import java.util.*

object NumberUtil {
    fun convertToRupiah(number: Int): String {
        return "Rp %,.0f".format(Locale("id"), number.toDouble())
    }
}