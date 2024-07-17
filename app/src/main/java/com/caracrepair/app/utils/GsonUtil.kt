package com.caracrepair.app.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GsonUtil {
    val gson = Gson()

    inline fun <reified T> fromJson(value: String?): T? {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(value, type)
    }

    fun toJson(value: Any): String {
        return gson.toJson(value)
    }
}