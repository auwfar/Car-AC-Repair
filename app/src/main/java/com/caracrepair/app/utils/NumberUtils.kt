package com.caracrepair.app.utils

import java.util.*

object NumberUtils {
    fun convertToRupiah(number: Int): String {
        return "Rp %,.0f".format(Locale("id"), number.toDouble())
    }
}