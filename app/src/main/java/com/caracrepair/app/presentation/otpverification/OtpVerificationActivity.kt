package com.caracrepair.app.presentation.otpverification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityOtpVerificationBinding
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.resetpassword.ResetPasswordActivity

class OtpVerificationActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_OTP_TYPE = "EXTRA_OTP_TYPE"
        fun createIntent(context: Context, otpType: OTPType): Intent {
            return Intent(context, OtpVerificationActivity::class.java).apply {
                putExtra(EXTRA_OTP_TYPE, otpType)
            }
        }
    }

    private lateinit var binding: ActivityOtpVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val otpType = intent.getParcelableExtra<OTPType>(EXTRA_OTP_TYPE) ?: OTPType.SignUp
        with(binding) {
            tvOtpVerificationTitle.text = when(otpType) {
                OTPType.SignUp -> getString(R.string.otp_verification)
                OTPType.ForgotPassword -> getString(R.string.reset_password)
            }
            ivBack.setOnClickListener {
                finish()
            }
            btnVerify.setOnClickListener {
                when(otpType) {
                    OTPType.SignUp -> {

                    }
                    OTPType.ForgotPassword -> {
                        startActivity(ResetPasswordActivity.createIntent(this@OtpVerificationActivity))
                    }
                }
            }
        }
    }
}