package com.caracrepair.app.models.body

import android.net.Uri

data class UploadPaymentProofImageBody(
    val serviceId: String,
    val imageUri: Uri
)