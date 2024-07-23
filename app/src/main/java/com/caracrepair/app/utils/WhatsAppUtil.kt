package com.caracrepair.app.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.caracrepair.app.R
import java.net.URLEncoder

object WhatsAppUtil {
    fun sendWhatsAppMessage(context: Context, phoneNumber: String, message: String) {
        try {
            val whatsAppUri = "https://api.whatsapp.com/send?phone=" + phoneNumber.replace("+", "") + "&text=${URLEncoder.encode(message, "UTF-8")}"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(whatsAppUri)
                setPackage("com.whatsapp")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.desc_install_whats_app_first), Toast.LENGTH_SHORT).show()
        }
    }
}