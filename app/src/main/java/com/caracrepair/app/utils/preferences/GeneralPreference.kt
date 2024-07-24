package com.caracrepair.app.utils.preferences

import android.content.Context
import com.caracrepair.app.models.viewparam.User
import com.caracrepair.app.utils.GsonUtil

private const val KEY_USER = "KEY_USER"

class GeneralPreference(
    context: Context,
    private val gsonUtil: GsonUtil
): BasePreference(context) {

    fun setUser(value: User) {
        writeString(KEY_USER, gsonUtil.toJson(value))
    }

    fun getUser(): User? {
        val value = readString(KEY_USER)
        return gsonUtil.fromJson(value)
    }
}