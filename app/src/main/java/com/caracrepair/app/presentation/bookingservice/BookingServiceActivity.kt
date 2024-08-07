package com.caracrepair.app.presentation.bookingservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.consts.ServiceTypeConst
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityBookingServiceBinding
import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.presentation.bookingservice.adapter.ServiceTimeAdapter
import com.caracrepair.app.presentation.bookingservice.viewmodel.BookingServiceViewModel
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem
import com.caracrepair.app.presentation.chooserepairshop.ChooseRepairShopActivityContract
import com.caracrepair.app.presentation.myaddress.MyAddressActivityContract
import com.caracrepair.app.presentation.mycar.MyCarActivityContract
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.ServiceDateValidator
import com.caracrepair.app.utils.hideKeyboard
import com.caracrepair.app.utils.preferences.GeneralPreference
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class BookingServiceActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, BookingServiceActivity::class.java)
        }
    }

    private lateinit var binding: ActivityBookingServiceBinding
    private val viewModel by viewModels<BookingServiceViewModel>()
    private val serviceTimeAdapter by lazy { ServiceTimeAdapter() }

    private val myCarLauncher = registerForActivityResult(MyCarActivityContract()) { selectedCar ->
        binding.etCar.setText(selectedCar?.carName)
        viewModel.selectedCar = selectedCar
    }
    private val myAddressLauncher = registerForActivityResult(MyAddressActivityContract()) { selectedAddress ->
        binding.etAddress.setText(selectedAddress?.address)
        viewModel.selectedAddress = selectedAddress
    }
    private val chooseRepairShopLauncher = registerForActivityResult(ChooseRepairShopActivityContract()) { selectedRepairShop ->
        binding.etRepairShop.setText(selectedRepairShop?.name)
        viewModel.selectedRepairShopId = selectedRepairShop?.id.orEmpty()
    }

    @Inject
    lateinit var generalPreference: GeneralPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()
        setupViews()
    }

    private fun observeViewModel() {
        viewModel.bookingServiceResult.observe(this) {
            startActivity(SuccessResponseActivity.createIntent(this, SuccessResponseType.BookingService, it))
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViews() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            toggleOrderType.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.btn_deliver -> {
                            groupAddress.isVisible = false
                        }
                        R.id.btn_pickup -> {
                            groupAddress.isVisible = true
                        }
                    }
                }
            }
            etCar.setOnClickListener {
                myCarLauncher.launch(null)
            }
            etAddress.setOnClickListener {
                myAddressLauncher.launch(null)
            }
            etRepairShop.setOnClickListener {
                chooseRepairShopLauncher.launch(null)
            }
            etServiceDate.setOnClickListener {
                showDatePicker()
            }
            btnOrderService.setOnClickListener {
                bookingService()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvServiceTime) {
            layoutManager = GridLayoutManager(this@BookingServiceActivity, 3)
            adapter = serviceTimeAdapter.apply {
                setItems(ServiceTimeItem.TIMES_DEFAULT)
            }
        }
    }

    private fun isValidBookingService(
        car: String,
        carDistance: String,
        address: String,
        complaint: String,
        repairShop: String,
        serviceDate: String,
        serviceTime: String
    ): Boolean {
        val isValidCar = FormUtil.validateRequired(StringConst.FieldName.CAR, binding.tilCar, car)
        val isValidCarDistance = FormUtil.validateRequired(StringConst.FieldName.CAR_DISTANCE, binding.tilCarDistance, carDistance)
        val isValidAddress = if (binding.toggleOrderType.checkedButtonId == R.id.btn_pickup) {
            FormUtil.validateRequired(StringConst.FieldName.ADDRESS, binding.tilAddress, address)
        } else {
            true
        }
        val isValidComplaint = FormUtil.validateRequired(StringConst.FieldName.COMPLAINT, binding.tilComplaint, complaint)
        val isValidRepairShop = FormUtil.validateRequired(StringConst.FieldName.REPAIR_SHOP, binding.tilRepairShop, repairShop)
        val isValidServiceDate = FormUtil.validateRequired(StringConst.FieldName.SERVICE_DATE, binding.tilServiceDate, serviceDate)
        val isValidServiceTime = if (serviceTime.isBlank()) {
            Toast.makeText(this, StringConst.requiredMessage(StringConst.FieldName.SERVICE_TIME), Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
        return isValidCar && isValidCarDistance && isValidAddress && isValidComplaint && isValidRepairShop && isValidServiceDate && isValidServiceTime
    }

    private fun bookingService() {
        hideKeyboard()
        val serviceType = if (binding.toggleOrderType.checkedButtonId == R.id.btn_pickup) {
            ServiceTypeConst.TYPE_PICKUP
        } else {
            ServiceTypeConst.TYPE_DELIVER
        }
        val carId = binding.etCar.text.toString()
        val carDistance = binding.etCarDistance.text.toString()
        val addressId = binding.etAddress.text.toString()
        val complaint = binding.etComplaint.text.toString()
        val repairShopId = binding.etRepairShop.text.toString()
        val serviceDate = binding.etServiceDate.text.toString()
        val serviceTime = serviceTimeAdapter.getSelectedServiceTime()

        if (isValidBookingService(carId, carDistance, addressId, complaint, repairShopId, serviceDate, serviceTime?.time.orEmpty())) {
            viewModel.bookingService(
                BookingServiceBody(
                    generalPreference.getUser()?.userId.orEmpty(),
                    generalPreference.getUser()?.name.orEmpty(),
                    viewModel.selectedCar?.id.orEmpty(),
                    viewModel.selectedCar?.carLicenseNumber.orEmpty(),
                    viewModel.selectedCar?.carName.orEmpty(),
                    viewModel.selectedCar?.carYear.orEmpty(),
                    viewModel.selectedAddress?.id.orEmpty(),
                    viewModel.selectedAddress?.address.orEmpty(),
                    carDistance.toIntOrNull() ?: 0,
                    complaint,
                    viewModel.selectedRepairShopId,
                    serviceType,
                    DateUtil.DATE_FOR_SERVER.simpleDateFormat.format(viewModel.selectedServiceDate) +" " +serviceTime?.time.orEmpty()
                )
            )
        }
    }

    private fun showDatePicker() {
        val open = Calendar.getInstance().apply {
            val selectedServiceDate = viewModel.selectedServiceDate
            if (selectedServiceDate != null) {
                time = Date(selectedServiceDate)
            } else {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        val start = Calendar.getInstance()
        val end = Calendar.getInstance().apply { add(Calendar.MONTH, 1) }
        val constraints = CalendarConstraints.Builder()
            .setStart(start.timeInMillis)
            .setEnd(end.timeInMillis)
            .setOpenAt(open.timeInMillis)
            .setValidator(ServiceDateValidator())
            .build()

        MaterialDatePicker.Builder.datePicker()
            .setSelection(open.timeInMillis)
            .setCalendarConstraints(constraints)
            .setTheme(R.style.App_MaterialCalendarTheme).build()
            .apply {
                addOnPositiveButtonClickListener { time ->
                    viewModel.selectedServiceDate = time
                    binding.etServiceDate.setText(DateUtil.DAY_FULL_MONTH_YEAR.simpleDateFormat.format(time))
                }
            }
            .show(supportFragmentManager, null)
    }
}