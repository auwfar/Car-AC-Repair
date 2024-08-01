package com.caracrepair.app.presentation.otpverification.constants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OTPType : Parcelable {
    data class SignUp(val phoneNumber: String) : OTPType()
    data class ForgotPassword(val phoneNumber: String) : OTPType()
}