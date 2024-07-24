package com.caracrepair.app.presentation.otpverification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityOtpVerificationBinding
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.resetpassword.ResetPasswordActivity
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType

class OtpVerificationActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_OTP_TYPE = "EXTRA_OTP_TYPE"
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        fun createIntent(context: Context, otpType: OTPType, userId: Int): Intent {
            return Intent(context, OtpVerificationActivity::class.java).apply {
                putExtra(EXTRA_OTP_TYPE, otpType)
                putExtra(EXTRA_USER_ID, userId)
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
                OTPType.SignUp, OTPType.ChangePhoneNumber -> getString(R.string.otp_verification)
                OTPType.ForgotPassword -> getString(R.string.reset_password)
            }
            ivBack.setOnClickListener {
                finish()
            }
            btnVerify.setOnClickListener {
                when(otpType) {
                    OTPType.SignUp -> {
                        startActivity(SuccessResponseActivity.createIntent(this@OtpVerificationActivity, SuccessResponseType.SignUp))
                    }
                    OTPType.ForgotPassword -> {
                        startActivity(ResetPasswordActivity.createIntent(this@OtpVerificationActivity))
                    }
                    OTPType.ChangePhoneNumber -> {
                        startActivity(SuccessResponseActivity.createIntent(this@OtpVerificationActivity, SuccessResponseType.ChangeProfile))
                    }
                }
            }
        }
    }
}