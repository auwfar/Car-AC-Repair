package com.caracrepair.app.presentation.successresponse.constants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class SuccessResponseType : Parcelable {
    data object SignUp : SuccessResponseType()
    data object ResetPassword : SuccessResponseType()
    data object Pay : SuccessResponseType()
    data object ChangeProfile : SuccessResponseType()
    data object BookingService : SuccessResponseType()
    data object RescheduleService : SuccessResponseType()
    data object ChangePassword : SuccessResponseType()
}