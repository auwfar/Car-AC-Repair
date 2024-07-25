package com.caracrepair.app.presentation.chooserepairshop.viewparam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepairShopOptionItem(
    val id: Int,
    val name: String,
    val address: String
) : Parcelable