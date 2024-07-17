package com.caracrepair.app.utils.customviews

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.caracrepair.app.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class OTPInputEditText : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private val textInputLayouts: List<TextInputLayout> by lazy {
        listOf(
            findViewById(R.id.til_input_1),
            findViewById(R.id.til_input_2),
            findViewById(R.id.til_input_3),
            findViewById(R.id.til_input_4)
        )
    }

    private val textInputEditTexts: List<TextInputEditText> by lazy {
        listOf(
            findViewById(R.id.et_input_1),
            findViewById(R.id.et_input_2),
            findViewById(R.id.et_input_3),
            findViewById(R.id.et_input_4)
        )
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
        inflater?.inflate(R.layout.view_otp_input_edit_text, this, true)
        setupTextWatcher()
    }

    private fun setupTextWatcher() {
        for (i in textInputLayouts.indices) {
            val currentEditText = textInputEditTexts[i]

            currentEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    // No implementation needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        // Move focus to the next TextInputEditText
                        moveFocusToNext(i)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (i == textInputEditTexts.size - 1) {
                        var otp = ""
                        for (et in textInputEditTexts) {
                            otp += (et.text)
                        }

                        if (otp.length == textInputEditTexts.size) {
                            currentEditText.clearFocus()
                            hideKeyboard(currentEditText)
                        }
                    }
                }
            })

            if (i != 0) {
                currentEditText.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                        if (!currentEditText.text.isNullOrEmpty()) {
                            //if the editText is not empty, it clears it
                            false
                        } else {
                            moveFocusToPreviousAndClear(i)
                            true
                        }

                    } else {
                        false
                    }
                }

            }
        }
    }

    private fun moveFocusToNext(index: Int) {
        if (index < textInputEditTexts.size - 1) {
            textInputEditTexts[index + 1].requestFocus()
        }
    }

    private fun moveFocusToPreviousAndClear(index: Int) {
        if (index > 0) {
            val previousEditText = textInputEditTexts[index - 1]
            previousEditText.requestFocus()
            previousEditText.text?.clear()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getOTP(): String {
        var otp = ""
        for (et in textInputEditTexts) {
            otp += (et.text)
        }
        return otp
    }
}