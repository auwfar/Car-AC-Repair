package com.caracrepair.app.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_PREFERENCE = "KEY_PREFERENCE"

abstract class BasePreference(context: Context) {
    private val preference: SharedPreferences = context.getSharedPreferences(KEY_PREFERENCE, Context.MODE_PRIVATE)

    fun writeString(key: String, value: String) {
        preference.edit {
            putString(key, value)
        }
    }

    fun writeBoolean(key: String, value: Boolean) {
        preference.edit {
            putBoolean(key, value)
        }
    }

    fun writeInt(key: String, value: Int) {
        preference.edit {
            putInt(key, value)
        }
    }

    fun writeLong(key: String, value: Long) {
        preference.edit {
            putLong(key, value)
        }
    }

    fun readString(key: String, defValue: String): String {
        return preference.getString(key, defValue)!!
    }

    fun readString(key: String): String? {
        return preference.getString(key, null)
    }

    fun readBoolean(key: String, defValue: Boolean): Boolean {
        return preference.getBoolean(key, defValue)
    }

    fun readInt(key: String, defValue: Int): Int {
        return preference.getInt(key, defValue)
    }

    fun readLong(key: String, defValue: Long): Long {
        return preference.getLong(key, defValue)
    }
}