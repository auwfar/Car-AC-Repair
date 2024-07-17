package com.caracrepair.app.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity?.hideKeyboard() {
    val view = this?.currentFocus
    view?.let {
        val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}