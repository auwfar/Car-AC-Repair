package com.caracrepair.app.presentation.rescheduleservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityRescheduleServiceBinding
import com.caracrepair.app.presentation.bookingservice.adapter.ServiceTimeAdapter
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem
import com.caracrepair.app.presentation.chooserepairshop.ChooseRepairShopActivity
import com.caracrepair.app.utils.SimpleDateUtil
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class RescheduleServiceActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RescheduleServiceActivity::class.java)
        }
    }

    private lateinit var binding: ActivityRescheduleServiceBinding
    private val serviceTimeAdapter by lazy { ServiceTimeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRescheduleServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            etRepairShop.setOnClickListener {
                startActivity(ChooseRepairShopActivity.createIntent(this@RescheduleServiceActivity))
            }
            etServiceDate.setOnClickListener {
                MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.App_MaterialCalendarTheme).build()
                    .apply {
                        addOnPositiveButtonClickListener { time ->
                            etServiceDate.setText(SimpleDateUtil.dayFullMonthYearFormat.format(time))
                            rvServiceTime.isVisible = true
                        }
                    }
                    .show(supportFragmentManager, null)
            }
            btnRescheduleService.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvServiceTime) {
            layoutManager = GridLayoutManager(this@RescheduleServiceActivity, 3)
            adapter = serviceTimeAdapter.apply {
                setItems(
                    listOf(
                        ServiceTimeItem("08:00", true),
                        ServiceTimeItem("09:00", true),
                        ServiceTimeItem("10:00", true),
                        ServiceTimeItem("11:00", false),
                        ServiceTimeItem("12:00", true),
                        ServiceTimeItem("13:00", true),
                        ServiceTimeItem("14:00", false),
                        ServiceTimeItem("15:00", true),
                        ServiceTimeItem("16:00", true)
                    )
                )
            }
        }
    }
}