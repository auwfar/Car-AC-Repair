package com.caracrepair.app.presentation.rescheduleservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityRescheduleServiceBinding
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.presentation.bookingservice.adapter.ServiceTimeAdapter
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem
import com.caracrepair.app.presentation.chooserepairshop.ChooseRepairShopActivityContract
import com.caracrepair.app.presentation.rescheduleservice.viewmodel.RescheduleServiceViewModel
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
class RescheduleServiceActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SERVICE_ID = "extra_service_id"
        fun createIntent(context: Context, serviceId: String): Intent {
            return Intent(context, RescheduleServiceActivity::class.java).apply {
                putExtra(EXTRA_SERVICE_ID, serviceId)
            }
        }
    }

    private lateinit var binding: ActivityRescheduleServiceBinding
    private val viewModel by viewModels<RescheduleServiceViewModel>()
    private val serviceTimeAdapter by lazy { ServiceTimeAdapter() }

    private val chooseRepairShopLauncher = registerForActivityResult(ChooseRepairShopActivityContract()) { selectedRepairShop ->
        binding.etRepairShop.setText(selectedRepairShop?.name)
        viewModel.selectedRepairShopId = selectedRepairShop?.id.orEmpty()
    }

    @Inject
    lateinit var generalPreference: GeneralPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRescheduleServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()
        setupViews()
    }

    private fun observeViewModel() {
        viewModel.rescheduleServiceResult.observe(this) {
            startActivity(SuccessResponseActivity.createIntent(this, SuccessResponseType.RescheduleService, it))
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
            etRepairShop.setOnClickListener {
                chooseRepairShopLauncher.launch(null)
            }
            etServiceDate.setOnClickListener {
                showDatePicker()
            }
            btnRescheduleService.setOnClickListener {
                rescheduleService()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvServiceTime) {
            layoutManager = GridLayoutManager(this@RescheduleServiceActivity, 3)
            adapter = serviceTimeAdapter.apply {
                setItems(ServiceTimeItem.TIMES_DEFAULT)
            }
        }
    }

    private fun isValidBookingService(
        repairShop: String,
        serviceDate: String,
        serviceTime: String
    ): Boolean {
        val isValidRepairShop = FormUtil.validateRequired(StringConst.FieldName.REPAIR_SHOP, binding.tilRepairShop, repairShop)
        val isValidServiceDate = FormUtil.validateRequired(StringConst.FieldName.SERVICE_DATE, binding.tilServiceDate, serviceDate)
        val isValidServiceTime = if (serviceTime.isBlank()) {
            Toast.makeText(this, StringConst.requiredMessage(StringConst.FieldName.SERVICE_TIME), Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
        return isValidRepairShop && isValidServiceDate && isValidServiceTime
    }

    private fun rescheduleService() {
        hideKeyboard()
        val repairShopId = binding.etRepairShop.text.toString()
        val serviceDate = binding.etServiceDate.text.toString()
        val serviceTime = serviceTimeAdapter.getSelectedServiceTime()

        if (isValidBookingService(repairShopId, serviceDate, serviceTime?.time.orEmpty())) {
            viewModel.rescheduleService(
                RescheduleServiceBody(
                    generalPreference.getUser()?.userId.orEmpty(),
                    viewModel.selectedRepairShopId,
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