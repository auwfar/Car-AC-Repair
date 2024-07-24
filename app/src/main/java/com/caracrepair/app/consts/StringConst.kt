package com.caracrepair.app.consts

object StringConst {
    const val GENERAL_ERROR_MESSAGE = "Terjadi kesalahan. Mohon coba lagi nanti."
    const val CONFIRMATION_PASSWORD_NOT_SAME_MESSAGE = "Konfirmasi password tidak sama dengan password"
    fun requiredMessage(field: String): String = "$field wajib diisi!"
    fun notValidMessage(field: String): String = "$field tidak valid!"
    fun minimumCharacterMessage(field: String, minimumCharacter: Int): String = "$field minimal $minimumCharacter karakter!"
    fun minimumAndMaximumDigitMessage(field: String, minimumDigit: Int, maximumDigit: Int): String = "$field harus valid, min. $minimumDigit digit dan maks. $maximumDigit digit"

    object FieldName {
        const val PHONE_NUMBER = "Nomor HP"
        const val PASSWORD = "Password"
        const val NAME = "Nama"
        const val PASSWORD_CONFIRMATION = "Konfirmasi Password"
    }
}