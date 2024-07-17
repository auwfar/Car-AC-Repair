package com.caracrepair.app.presentation.otpverification.constants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OTPType : Parcelable {
    data object SignUp : OTPType()
    data object ForgotPassword : OTPType()
}