package com.caracrepair.app.presentation.mycarform

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityMyCarFormBinding
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.presentation.mycarform.MyCarFormActivity.Companion.EXTRA_MY_CAR_ITEM
import com.caracrepair.app.presentation.mycarform.viewmodel.MyCarFormViewModel
import com.caracrepair.app.utils.FormUtil

class MyCarFormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MY_CAR_ITEM = "extra_my_car_item"
    }

    private lateinit var binding: ActivityMyCarFormBinding
    private val viewModel by viewModels<MyCarFormViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCarFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupViews()
    }

    private fun observeViewModel() {
        viewModel.updateCarResult.observe(this) { message ->
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
        val myCarItem = intent.getParcelableExtra<MyCarItem>(EXTRA_MY_CAR_ITEM)
        with(binding) {
            etInputCarName.setText(myCarItem?.carName)
            etInputCarLicenseNumber.setText(myCarItem?.carLicenseNumber)
            etInputCarYear.setText(myCarItem?.carYear)

            ivBack.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                updateCar()
            }
        }
    }

    private fun isValidMyCarForm(name: String, licenseNumber: String, year: String): Boolean {
        val isValidName = FormUtil.validateRequired(StringConst.FieldName.NAME, binding.tilCarName, name)
        val isValidLicenseNumber = FormUtil.validateRequired(StringConst.FieldName.LICENSE_NUMBER, binding.tilCarLicenseNumber, licenseNumber)
        val isValidYear = FormUtil.validateRequired(StringConst.FieldName.YEAR, binding.tilCarYear, year)
        return isValidName && isValidLicenseNumber && isValidYear
    }

    private fun updateCar() {
        val carId = intent.getParcelableExtra<MyCarItem>(EXTRA_MY_CAR_ITEM)?.id ?: 0
        val name = binding.etInputCarName.text.toString()
        val licenseNumber = binding.etInputCarLicenseNumber.text.toString()
        val year = binding.etInputCarYear.text.toString()
        if (isValidMyCarForm(name, licenseNumber, year)) {
            viewModel.updateCar(carId, name, licenseNumber, year)
        }
    }
}

class MyCarFormActivityContract : ActivityResultContract<MyCarItem?, Boolean>() {
    override fun createIntent(context: Context, input: MyCarItem?): Intent {
        return Intent(context, MyCarFormActivity::class.java).apply {
            putExtra(EXTRA_MY_CAR_ITEM, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return (resultCode == Activity.RESULT_OK)
    }
}