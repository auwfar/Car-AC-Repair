package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class HomePageResponse(
    @SerializedName("on_progress_services")
    val onProgressServices: List<OnProgressServiceModel>?,
    @SerializedName("last_services")
    val lastServices: List<LastServiceModel>?,
    @SerializedName("carshops")
    val repairShops: List<CarShopModel>?
) {
    data class OnProgressServiceModel(
        @SerializedName("id")
        val orderId: String?,
        @SerializedName("car_name")
        val carName: String?,
        @SerializedName("status_label")
        val status: String?
    )

    data class LastServiceModel(
        @SerializedName("id")
        val orderId: String?,
        @SerializedName("car_name")
        val carName: String?,
        @SerializedName("createdAt")
        val orderTime: String?,
        @SerializedName("distance")
        val carDistance: String?
    )

    data class CarShopModel(
        @SerializedName("id")
        val id: String?,
        @SerializedName("image")
        val imageUrl: String?,
        @SerializedName("description")
        val description: String?
    )
}