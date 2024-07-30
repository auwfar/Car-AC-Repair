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

    object ServiceStatusDescription {
        const val DESC_PENDING = "Pesanan Anda telah diterima dan sedang menunggu giliran untuk dilayani oleh mekanik kami. Kami akan segera memproses pesanan Anda."
        const val DESC_SELF_DELIVER = "Anda telah memilih untuk mengantar mobil sendiri ke bengkel. Silakan datang sesuai waktu yang disepakati. Terima kasih atas kerjasamanya."
        const val DESC_MECHANIC_PICKUP = "Kami sedang menjemput mobil Anda di lokasi yang telah disepakati. Pastikan mobil siap untuk diambil. Kami akan menghubungi Anda setelah mobil sampai di bengkel."
        const val DESC_CHECKING = "Kendaraan Anda sedang dalam tahap pengecekan oleh teknisi kami. Kami sedang melakukan pemeriksaan menyeluruh untuk mengidentifikasi semua masalah potensial."
        const val DESC_IN_PROGRESS = "Mekanik kami sedang melakukan perbaikan sesuai dengan hasil pengecekan dan persetujuan Anda. Kami akan memberikan informasi lebih lanjut setelah perbaikan selesai."
        const val DESC_MECHANIC_DELIVERY = "Mobil Anda sedang diantar ke lokasi yang disepakati. Kami akan menghubungi Anda setelah mobil tiba. Terima kasih atas kesabaran Anda."
        const val DESC_READY_TO_PICKUP = "Perbaikan mobil Anda telah selesai. Silakan datang ke bengkel untuk mengambil kendaraan Anda. Terima kasih telah mempercayakan perbaikan mobil Anda kepada kami."
        const val DESC_WAITING_PAYMENT_CONFIRMATION = "Kami telah menerima pembayaran Anda dan sedang menunggu konfirmasi dari admin. Anda akan segera mendapatkan pemberitahuan setelah pembayaran dikonfirmasi."
        const val DESC_COMPLETE = "Mobil Anda telah selesai diperbaiki dan sudah diantar atau dijemput sesuai dengan pilihan Anda. Terima kasih atas kepercayaan Anda kepada kami!"
    }
}