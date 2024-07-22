package com.caracrepair.app.presentation.bookingservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityOrderServiceBinding
import com.caracrepair.app.presentation.bookingservice.adapter.ServiceTimeAdapter
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem
import com.caracrepair.app.presentation.chooserepairshop.ChooseRepairShopActivity
import com.caracrepair.app.presentation.myaddress.MyAddressActivity
import com.caracrepair.app.presentation.mycar.MyCarActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class BookingServiceActivity : AppCompatActivity() {
    companion object {
        private val dayFullMonthYearFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id")).apply {
            timeZone = TimeZone.getTimeZone("GMT+07:00")
        }

        fun createIntent(context: Context): Intent {
            return Intent(context, BookingServiceActivity::class.java)
        }
    }

    private lateinit var binding: ActivityOrderServiceBinding
    private val serviceTimeAdapter by lazy { ServiceTimeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViews()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupViews() {
        with(binding) {
            toggleOrderType.addOnButtonCheckedListener { group, checkedId, isChecked ->
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
                startActivity(MyCarActivity.createIntent(this@BookingServiceActivity))
            }
            etAddress.setOnClickListener {
                startActivity(MyAddressActivity.createIntent(this@BookingServiceActivity))
            }
            etRepairShop.setOnClickListener {
                startActivity(ChooseRepairShopActivity.createIntent(this@BookingServiceActivity))
            }
            etServiceDate.setOnClickListener {
                MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.App_MaterialCalendarTheme).build()
                    .apply {
                        addOnPositiveButtonClickListener { time ->
                            etServiceDate.setText(dayFullMonthYearFormat.format(time))
                            rvServiceTime.isVisible = true
                        }
                    }
                    .show(supportFragmentManager, null)
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvServiceTime) {
            layoutManager = GridLayoutManager(this@BookingServiceActivity, 3)
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