package com.caracrepair.app.presentation.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.databinding.ActivityForgotPasswordBinding
import com.caracrepair.app.presentation.forgotpassword.viewmodel.ForgotPasswordViewModel
import com.caracrepair.app.presentation.otpverification.OtpVerificationActivity
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.hideKeyboard
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    @Inject
    lateinit var generalPreference: GeneralPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnResetPassword.setOnClickListener {
                forgotPassword()
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@ForgotPasswordActivity))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.requestOtpResult.observe(this) { phoneNumber ->
            startActivity(
                OtpVerificationActivity.createIntent(
                    this@ForgotPasswordActivity,
                    OTPType.ForgotPassword(phoneNumber)
                )
            )
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return FormUtil.validatePhoneNumber(binding.tilPhoneNumber, phoneNumber)
    }

    private fun forgotPassword() {
        hideKeyboard()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        if (isValidPhoneNumber(phoneNumber)) {
            viewModel.requestOtp(phoneNumber)
        }
    }
}