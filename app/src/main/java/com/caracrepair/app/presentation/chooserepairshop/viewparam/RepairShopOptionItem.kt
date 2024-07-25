package com.caracrepair.app.presentation.chooserepairshop.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.RepairShopResponse
import com.caracrepair.app.models.viewparam.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepairShopOptionItem(
    val id: Int,
    val name: String,
    val address: String,
    val location: Location
) : Parcelable {
    constructor(response: RepairShopResponse?) : this(
        id = response?.id ?: 0,
        name = response?.name.orEmpty(),
        address = response?.address.orEmpty(),
        location = Location(response?.location?.lat ?: 0.0, response?.location?.long ?: 0.0)
    )
}