package com.caracrepair.app.consts

object StringConst {
    const val GENERAL_ERROR_MESSAGE = "Terjadi kesalahan. Mohon coba lagi nanti."
    const val CONFIRMATION_PASSWORD_NOT_SAME_MESSAGE = "Konfirmasi password tidak sama dengan password"
    fun requiredMessage(field: String): String = "$field wajib diisi!"
    fun notValidMessage(field: String): String = "$field tidak valid!"
    fun minimumCharacterMessage(field: String, minimumCharacter: Int): String = "$field minimal $minimumCharacter karakter!"
    fun minimumAndMaximumDigitMessage(field: String, minimumDigit: Int, maximumDigit: Int): String = "$field harus valid, min. $minimumDigit digit dan maks. $maximumDigit digit"
    fun minimumDigitMessage(field: String, minimumDigit: Int): String = "$field minimal $minimumDigit digit!"

    object FieldName {
        const val PHONE_NUMBER = "Nomor HP"
        const val PASSWORD = "Password"
        const val NAME = "Nama"
        const val PASSWORD_CONFIRMATION = "Konfirmasi Password"
        const val ADDRESS = "Alamat"
        const val SERVICE_TIME = "Waktu Layanan"
        const val SERVICE_DATE = "Tanggal Layanan"
        const val CAR = "Mobil"
        const val CAR_DISTANCE = "Jarak Tempuh Mobil"
        const val COMPLAINT = "Keluhan"
        const val REPAIR_SHOP = "Bengkel"
        const val NEW_PASSWORD = "Password Baru"
        const val NEW_PASSWORD_CONFIRMATION = "Konfirmasi Password Baru"
        const val LICENSE_NUMBER = "Nomor Plat"
        const val YEAR = "Tahun"
        const val ADDRESS_LABEL = "Label Alamat"
    }
}