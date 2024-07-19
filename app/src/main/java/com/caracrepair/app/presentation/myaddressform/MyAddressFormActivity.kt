package com.caracrepair.app.presentation.myaddressform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityMyAddressFormBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.logo.logo

class MyAddressFormActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MyAddressFormActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyAddressFormBinding
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) setupMapView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAddressFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
        requestPermission.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun setupMapView() {
        with(binding.mapView) {
            logo.enabled = false
            attribution.enabled = false
            mapboxMap.gesturesPlugin {
                updateSettings {
                    rotateEnabled = false
                    pinchToZoomEnabled = false
                    scrollEnabled = false
                    simultaneousRotateAndPinchToZoomEnabled = false
                    pitchEnabled = false
                    doubleTapToZoomInEnabled = false
                    doubleTouchToZoomOutEnabled = false
                    quickZoomEnabled = false
                    pinchToZoomDecelerationEnabled = false
                    rotateDecelerationEnabled = false
                    scrollDecelerationEnabled = false
                    pinchScrollEnabled = false
                }
            }
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