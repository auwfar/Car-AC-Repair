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
import com.caracrepair.app.presentation.chooserepairshop.ChooseRepairShopActivityContract
import com.caracrepair.app.presentation.rescheduleservice.viewmodel.RescheduleServiceViewModel
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.SimpleDateUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import com.google.android.material.datepicker.MaterialDatePicker
import javax.inject.Inject

class RescheduleServiceActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SERVICE_ID = "extra_service_id"
        fun createIntent(context: Context, serviceId: Int): Intent {
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
        viewModel.selectedRepairShopId = selectedRepairShop?.id ?: 0
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
        viewModel.serviceTimeResult.observe(this) { serviceTimeItems ->
            binding.rvServiceTime.isVisible = true
            serviceTimeAdapter.setItems(serviceTimeItems)
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
                MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.App_MaterialCalendarTheme).build()
                    .apply {
                        addOnPositiveButtonClickListener { time ->
                            etServiceDate.setText(SimpleDateUtil.dayFullMonthYearFormat.format(time))
                            viewModel.getServiceTimes(SimpleDateUtil.serverFormat.format(time))
                        }
                    }
                    .show(supportFragmentManager, null)
            }
            btnRescheduleService.setOnClickListener {
                rescheduleService()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvServiceTime) {
            layoutManager = GridLayoutManager(this@RescheduleServiceActivity, 3)
            adapter = serviceTimeAdapter
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
        val repairShopId = binding.etRepairShop.text.toString()
        val serviceDate = binding.etServiceDate.text.toString()
        val serviceTime = serviceTimeAdapter.getSelectedServiceTime()

        if (isValidBookingService(repairShopId, serviceDate, serviceTime?.time.orEmpty())) {
            viewModel.rescheduleService(
                RescheduleServiceBody(
                    generalPreference.getUser()?.userId ?: 0,
                    viewModel.selectedRepairShopId,
                    SimpleDateUtil.serverFormat.format(serviceDate),
                    serviceTime?.time.orEmpty()
                )
            )
        }
    }
}