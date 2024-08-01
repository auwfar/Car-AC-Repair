package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class UploadPaymentProofImageBody(
    @SerializedName("payment_proof_image")
    val proofImageUrl: String,
    @SerializedName("payment-type")
    val paymentType: String = "non-cash"
)