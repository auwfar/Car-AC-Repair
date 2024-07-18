package com.caracrepair.app.presentation.main.history.viewparam

data class HistoryItem(
    val id: Int,
    val orderDate: String,
    val carName: String,
    val carImage: String,
    val serviceDate: String,
    val status: String
)