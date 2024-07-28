package com.caracrepair.app.presentation.choosemapslocation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityChooseMapsLocationBinding
import com.caracrepair.app.models.viewparam.Location
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.logo

class ChooseMapsLocationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_LOCATION = "extra_location"
    }

    private lateinit var binding: ActivityChooseMapsLocationBinding
    private var currentLocation: Location? = null

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var isGranted = false
        permissions.forEach {
            if (!it.value) {
                isGranted = false
                return@forEach
            }
            isGranted = true
        }
        if (isGranted) setupMapView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseMapsLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentLocation = intent.getParcelableExtra(EXTRA_LOCATION)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnChoose.setOnClickListener {
            val mapLatLng = binding.mapView.mapboxMap.cameraState.center
            val location = Location(mapLatLng.latitude(), mapLatLng.longitude())
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(EXTRA_LOCATION, location)
            })
            finish()
        }
        binding.ivCurrentLocation.setOnClickListener {
            getCurrentLocation()
        }

        requestPermission.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun setupMapView() {
        with(binding.mapView) {
            logo.enabled = false
            attribution.enabled = false
            mapboxMap.apply {
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(currentLocation?.long ?: 106.816666, currentLocation?.lat ?: -6.200000))
                        .zoom(13.0)
                        .build()
                )
            }
        }
        if (currentLocation == null) getCurrentLocation()
    }

    private fun getCurrentLocation() {
        val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
            binding.mapView.mapboxMap.setCamera(CameraOptions.Builder().bearing(it).build())
        }

        val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
            binding.mapView.mapboxMap.setCamera(CameraOptions.Builder().center(it).build())
            binding.mapView.gestures.focalPoint = binding.mapView.mapboxMap.pixelForCoordinate(it)
        }

        binding.mapView.location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.location.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }
}

class ChooseMapsLocationActivityContract : ActivityResultContract<Location?, Location?>() {
    override fun createIntent(context: Context, input: Location?): Intent {
        return Intent(context, ChooseMapsLocationActivity::class.java).apply {
            putExtra(ChooseMapsLocationActivity.EXTRA_LOCATION, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Location? {
        return if (resultCode == Activity.RESULT_OK) {
            intent?.getParcelableExtra(ChooseMapsLocationActivity.EXTRA_LOCATION)
        } else null
    }
}