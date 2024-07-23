package com.caracrepair.app.utils

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient

class GpsUtil(val context: Context) {
    companion object {
        const val RC_GPS = 100
    }
    private var locationManager: LocationManager? = null
    private var mSettingsClient: SettingsClient
    private var mLocationSettingsRequest: LocationSettingsRequest

    private var activity: Activity

    init {
        if (context !is Activity) {
            throw IllegalAccessException("Gps Util must access with activity context")
        }
        activity = context
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        mSettingsClient = LocationServices.getSettingsClient(context)

        val builder = LocationSettingsRequest.Builder().addLocationRequest(LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        })
        mLocationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
    }

    fun turnGPSOn(listener: (Boolean) -> Unit) {
        if (isGpsOn()) {
            listener(true)
        } else {
            mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(activity) {
                    listener(true)
                }
                .addOnFailureListener(activity) { e ->
                    val exception = e as? ApiException
                    when (exception?.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                val resolvableApiException = e as? ResolvableApiException
                                resolvableApiException?.startResolutionForResult(activity, RC_GPS)
                            } catch (e: IntentSender.SendIntentException) {
                                Toast.makeText(activity, "PendingIntent unable to execute request.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            Toast.makeText(activity, "Nyalakan GPS Anda melalui settings", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }

    fun isGpsOn(): Boolean {
        return (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true)
    }
}