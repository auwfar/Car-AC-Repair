package com.caracrepair.app.models.viewparam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmationDialogParam(
    val title: String,
    val message: String,
    val positiveButton: ButtonParam,
    val negativeButton: ButtonParam
) : Parcelable

@Parcelize
data class ButtonParam(
    val text: String,
    val action: () -> Unit
) : Parcelable