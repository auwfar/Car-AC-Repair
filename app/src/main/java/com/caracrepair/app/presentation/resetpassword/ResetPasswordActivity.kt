package com.caracrepair.app.presentation.resetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityResetPasswordBinding
import com.caracrepair.app.presentation.otpverification.OtpVerificationActivity
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.signin.SignInActivity

class ResetPasswordActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }

    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@ResetPasswordActivity))
            }
        }
    }
}