package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class HomePageResponse(
    @SerializedName("on_progress_services")
    val onProgressServices: List<OnProgressServiceModel>?,
    @SerializedName("last_services")
    val lastServices: List<LastServiceModel>?,
    @SerializedName("carshops")
    val carShops: List<CarShopModel>?
) {
    data class OnProgressServiceModel(
        @SerializedName("order_id")
        val orderId: String?,
        @SerializedName("car_name")
        val carName: String?,
        @SerializedName("status")
        val status: String?
    )

    data class LastServiceModel(
        @SerializedName("order_id")
        val orderId: Int?,
        @SerializedName("car_name")
        val carName: String?,
        @SerializedName("order_time")
        val orderTime: String?,
        @SerializedName("car_distance")
        val carDistance: String?
    )

    data class CarShopModel(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("description")
        val description: String?
    )
}