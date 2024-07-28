package com.caracrepair.app.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_PREFERENCE = "KEY_PREFERENCE"

abstract class BasePreference(context: Context) {
    private val preference: SharedPreferences = context.getSharedPreferences(KEY_PREFERENCE, Context.MODE_PRIVATE)

    protected fun clearAll() {
        preference.edit { clear() }
    }

    fun writeString(key: String, value: String) {
        preference.edit {
            putString(key, value)
        }
    }

    protected fun writeBoolean(key: String, value: Boolean) {
        preference.edit {
            putBoolean(key, value)
        }
    }

    protected fun writeInt(key: String, value: Int) {
        preference.edit {
            putInt(key, value)
        }
    }

    protected fun writeLong(key: String, value: Long) {
        preference.edit {
            putLong(key, value)
        }
    }

    protected fun readString(key: String, defValue: String): String {
        return preference.getString(key, defValue)!!
    }

    protected fun readString(key: String): String? {
        return preference.getString(key, null)
    }

    protected fun readBoolean(key: String, defValue: Boolean): Boolean {
        return preference.getBoolean(key, defValue)
    }

    protected fun readInt(key: String, defValue: Int): Int {
        return preference.getInt(key, defValue)
    }

    protected fun readLong(key: String, defValue: Long): Long {
        return preference.getLong(key, defValue)
    }
}