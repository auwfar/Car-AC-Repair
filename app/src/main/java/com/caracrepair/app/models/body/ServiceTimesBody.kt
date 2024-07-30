package com.caracrepair.app.models.body

data class ServiceTimesBody(
    val userId: String,
    val repairShopId: String,
    val serviceDate: String
)