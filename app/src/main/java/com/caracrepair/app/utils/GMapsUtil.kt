package com.caracrepair.app.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object GMapsUtil {
    fun pinLocationMap(context: Context, latitude: Double, longitude: Double) {
        val mapUri = Uri.parse("https://maps.google.com/maps/search/$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        context.startActivity(intent)
    }
}