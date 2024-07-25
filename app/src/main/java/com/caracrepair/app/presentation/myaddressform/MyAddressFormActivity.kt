package com.caracrepair.app.presentation.myaddressform

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityMyAddressFormBinding
import com.caracrepair.app.models.body.LocationBody
import com.caracrepair.app.models.viewparam.Location
import com.caracrepair.app.presentation.choosemapslocation.ChooseMapsLocationActivity
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem
import com.caracrepair.app.presentation.myaddressform.MyAddressFormActivity.Companion.EXTRA_MY_ADDRESS_ITEM
import com.caracrepair.app.presentation.myaddressform.viewmodel.MyAddressFormViewModel
import com.caracrepair.app.utils.FormUtil
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.logo.logo

class MyAddressFormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MY_ADDRESS_ITEM = "extra_my_address_item"
        fun createIntent(context: Context): Intent {
            return Intent(context, MyAddressFormActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyAddressFormBinding
    private val viewModel by viewModels<MyAddressFormViewModel>()
    private var selectedLocation = Location(-6.200000, 106.816666)
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) setupMapView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAddressFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupViews()
        requestPermission.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun observeViewModel() {
        viewModel.updateAddressResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViews() {
        val myAddressItem = intent.getParcelableExtra<MyAddressItem>(EXTRA_MY_ADDRESS_ITEM)
        with(binding) {
            etInputAddressName.setText(myAddressItem?.addressLabel)
            selectedLocation = myAddressItem?.location ?: selectedLocation
            etInputLocationAddress.setText(myAddressItem?.address)
            etInputAddressNotes.setText(myAddressItem?.addressNote)

            ivBack.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                updateAddress()
            }
        }
    }

    private fun setupMapView() {
        binding.viewAddressLocation.setOnClickListener {
            startActivity(ChooseMapsLocationActivity.createIntent(this@MyAddressFormActivity))
        }
        with(binding.mapView) {
            logo.enabled = false
            attribution.enabled = false

            with(mapboxMap) {
                gesturesPlugin {
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
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(selectedLocation.long, selectedLocation.lat))
                        .zoom(13.0)
                        .build()
                )
            }
        }
    }

    private fun isValidMyAddressForm(
        label: String,
        address: String
    ): Boolean {
        val isValidAddressLabel = FormUtil.validateRequired(StringConst.FieldName.ADDRESS_LABEL, binding.tilAddressName, label)
        val isValidAddress = FormUtil.validateRequired(StringConst.FieldName.ADDRESS, binding.tilLocationAddress, address)
        return isValidAddressLabel && isValidAddress
    }

    private fun updateAddress() {
        val addressId = intent.getParcelableExtra<MyAddressItem>(EXTRA_MY_ADDRESS_ITEM)?.id ?: 0
        val label = binding.etInputAddressName.text.toString()
        val address = binding.etInputLocationAddress.text.toString()
        val addressNote = binding.etInputAddressNotes.text.toString()

        if (isValidMyAddressForm(label, address)) {
            viewModel.updateAddress(addressId, label, address, addressNote, LocationBody(selectedLocation))
        }
    }
}

class MyAddressFormActivityContract : ActivityResultContract<MyAddressItem?, Boolean>() {
    override fun createIntent(context: Context, input: MyAddressItem?): Intent {
        return Intent(context, MyAddressFormActivity::class.java).apply {
            putExtra(EXTRA_MY_ADDRESS_ITEM, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return (resultCode == Activity.RESULT_OK)
    }
}