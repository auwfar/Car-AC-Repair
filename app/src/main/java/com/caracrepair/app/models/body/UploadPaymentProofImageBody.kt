package com.caracrepair.app.models.body

import android.net.Uri

data class UploadPaymentProofImageBody(
    val serviceId: Int,
    val imageUri: Uri
)