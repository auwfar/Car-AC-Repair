package com.caracrepair.app.presentation.otpverification.constants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OTPType : Parcelable {
    data class SignUp(val userId: String) : OTPType()
    data class ForgotPassword(val userId: String) : OTPType()
}