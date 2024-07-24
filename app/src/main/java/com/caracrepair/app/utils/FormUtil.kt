package com.caracrepair.app.utils

import com.caracrepair.app.consts.StringConst
import com.google.android.material.textfield.TextInputLayout

object FormUtil {
    fun validatePhoneNumber(textInputLayout: TextInputLayout, phoneNumber: String): Boolean {
        val fieldName = StringConst.FieldName.PHONE_NUMBER
        val minimumDigit = 10
        val maximumDigit = 14
        val isValid = when {
            phoneNumber.isBlank() -> {
                textInputLayout.error = StringConst.requiredMessage(fieldName)
                false
            }
            (phoneNumber.length < minimumDigit || phoneNumber.length > maximumDigit) -> {
                textInputLayout.error = StringConst.minimumAndMaximumDigitMessage(fieldName, minimumDigit, maximumDigit)
                false
            }
            !phoneNumber.startsWith("0") -> {
                textInputLayout.error = StringConst.notValidMessage(fieldName)
                false
            }
            else -> {
                textInputLayout.error = ""
                true
            }
        }
        textInputLayout.isErrorEnabled = !isValid
        return isValid
    }

    fun validatePassword(textInputLayout: TextInputLayout, password: String): Boolean {
        val fieldName = StringConst.FieldName.PASSWORD
        val minimumPasswordCharacter = 8
        val isValid = when {
            password.isBlank() -> {
                textInputLayout.error = StringConst.requiredMessage(fieldName)
                false
            }
            password.length < minimumPasswordCharacter -> {
                textInputLayout.error =StringConst.minimumCharacterMessage(fieldName, minimumPasswordCharacter)
                false
            }
            else -> {
                textInputLayout.error = ""
                true
            }
        }
        textInputLayout.isErrorEnabled = !isValid
        return isValid
    }

    fun validateName(textInputLayout: TextInputLayout, name: String): Boolean {
        val fieldName = StringConst.FieldName.NAME
        val isValid = when {
            name.isBlank() -> {
                textInputLayout.error = StringConst.requiredMessage(fieldName)
                false
            }
            else -> {
                textInputLayout.error = ""
                true
            }
        }
        textInputLayout.isErrorEnabled = !isValid
        return isValid
    }

    fun validateConfirmationPassword(
        textInputLayout: TextInputLayout,
        password: String,
        confirmationPassword: String
    ): Boolean {
        val fieldName = "Konfirmasi Password"
        val isValid = when {
            confirmationPassword.isBlank() -> {
                textInputLayout.error = StringConst.requiredMessage(fieldName)
                false
            }
            confirmationPassword != password -> {
                textInputLayout.error = StringConst.CONFIRMATION_PASSWORD_NOT_SAME_MESSAGE
                false
            }
            else -> {
                textInputLayout.error = ""
                true
            }
        }
        textInputLayout.isErrorEnabled = !isValid
        return isValid
    }
}