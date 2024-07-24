package com.caracrepair.app.presentation.otpverification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityOtpVerificationBinding
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.otpverification.viewmodel.OtpVerificationViewModel
import com.caracrepair.app.presentation.resetpassword.ResetPasswordActivity
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    private val viewModel by viewModels<OtpVerificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val otpType = intent.getParcelableExtra<OTPType>(EXTRA_OTP_TYPE) ?: return

        observeViewModel()
        with(binding) {
            tvOtpVerificationTitle.text = when {
                (otpType is OTPType.SignUp) -> getString(R.string.otp_verification)
                (otpType is OTPType.ForgotPassword) -> getString(R.string.reset_password)
                else -> ""
            }
            ivBack.setOnClickListener {
                finish()
            }
            btnVerify.setOnClickListener {
                viewModel.otpVerification(etOtp.getOTP(), otpType)
            }
            tvResendOtp.setOnClickListener {
                viewModel.resendOtp(otpType)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.otpVerificationResult.observe(this) { otpType ->
            when(otpType) {
                is OTPType.SignUp -> {
                    startActivity(SuccessResponseActivity.createIntent(this, SuccessResponseType.SignUp))
                }
                is OTPType.ForgotPassword -> {
                    startActivity(ResetPasswordActivity.createIntent(this))
                }
            }
        }
        viewModel.resendOtpResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}