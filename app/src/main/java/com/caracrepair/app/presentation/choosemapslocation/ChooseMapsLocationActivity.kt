package com.caracrepair.app.presentation.choosemapslocation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.caracrepair.app.databinding.ActivityChooseMapsLocationBinding
import com.caracrepair.app.utils.GpsUtil
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.logo.logo
import kotlinx.coroutines.launch

class ChooseMapsLocationActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChooseMapsLocationActivity::class.java)
        }
    }

    private lateinit var binding: ActivityChooseMapsLocationBinding
    private val gpsUtil by lazy { GpsUtil(this) }

    private var cancellationTokenSource: CancellationTokenSource? = null
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var isGranted = false
        permissions.forEach {
            if (!it.value) {
                isGranted = false
                return@forEach
            }
            isGranted = true
        }
        if (isGranted) getCurrentLocation()
    }

    private val gpsListener = { isEnable: Boolean ->
        if (isEnable) getCurrentLocation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseMapsLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
        setupMapView()
        getCurrentLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == GpsUtil.RC_GPS) {
            gpsListener(true)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
            return
        } else if (!gpsUtil.isGpsOn()) {
            gpsUtil.turnGPSOn(gpsListener)
            return
        }

        lifecycleScope.launch {
            val cancellationToken = CancellationTokenSource().also {
                this@ChooseMapsLocationActivity.cancellationTokenSource = it
            }
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationToken.token
            ).addOnSuccessListener { location ->
                binding.mapView.mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(location.longitude, location.latitude))
                        .zoom(16.0)
                        .build()
                )
            }.addOnFailureListener {
                it.printStackTrace()
            }
        }
    }

    private fun setupMapView() {
        with(binding.mapView) {
            logo.enabled = false
            attribution.enabled = false
            mapboxMap.apply {
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(106.816666, -6.200000))
                        .zoom(13.0)
                        .build()
                )
            }
        }
    }
}