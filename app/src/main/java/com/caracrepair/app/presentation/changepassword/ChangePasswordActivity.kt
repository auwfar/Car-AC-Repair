package com.caracrepair.app.presentation.changepassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityMyCarFormBinding

class ChangePasswordActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChangePasswordActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyCarFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCarFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}