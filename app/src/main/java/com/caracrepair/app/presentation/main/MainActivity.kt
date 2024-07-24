package com.caracrepair.app.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityMainBinding
import com.caracrepair.app.presentation.bookingservice.BookingServiceActivity
import com.caracrepair.app.presentation.main.adapter.MainAdapter

class MainActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupBottomNav()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun setupViewPager() {
        binding.vpMain.apply {
            adapter = MainAdapter(this@MainActivity)
            isUserInputEnabled = false
        }
    }

    private fun setupBottomNav() {
        binding.ivServiceAction.setOnClickListener {
            startActivity(BookingServiceActivity.createIntent(this))
        }
        with(binding.bottomNavMain) {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_home -> {
                        binding.vpMain.setCurrentItem(0, false)
                        true
                    }
                    R.id.action_garage -> {
                        binding.vpMain.setCurrentItem(1, false)
                        true
                    }
                    R.id.action_history -> {
                        binding.vpMain.setCurrentItem(2, false)
                        true
                    }
                    R.id.action_profile -> {
                        binding.vpMain.setCurrentItem(3, false)
                        true
                    }
                    else -> false
                }
            }
            setOnItemReselectedListener(null)
        }
    }
}