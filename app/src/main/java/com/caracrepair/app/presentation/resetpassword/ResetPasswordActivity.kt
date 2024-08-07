package com.caracrepair.app.presentation.resetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityResetPasswordBinding
import com.caracrepair.app.presentation.resetpassword.viewmodel.ResetPasswordViewModel
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_PHONE_NUMBER = "EXTRA_PHONE_NUMBER"
        fun createIntent(context: Context, phoneNumber: String): Intent {
            return Intent(context, ResetPasswordActivity::class.java).apply {
                putExtra(EXTRA_PHONE_NUMBER, phoneNumber)
            }
        }
    }

    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel by viewModels<ResetPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnResetPassword.setOnClickListener {
                resetPassword()
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@ResetPasswordActivity))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.resetPasswordResult.observe(this) {
            startActivity(SuccessResponseActivity.createIntent(this, SuccessResponseType.ResetPassword))
        }
        viewModel.loadingState.observe(this) {
            binding.flLoading.isVisible = it
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidResetPasswordForm(password: String, confirmationPassword: String): Boolean {
        val isValidPassword = FormUtil.validatePassword(StringConst.FieldName.PASSWORD, binding.tilNewPassword, password)
        val isValidConfirmationPassword = FormUtil.validateConfirmationPassword(StringConst.FieldName.PASSWORD_CONFIRMATION, binding.tilConfirmationNewPassword, password, confirmationPassword)
        return isValidPassword && isValidConfirmationPassword
    }

    private fun resetPassword() {
        hideKeyboard()
        val phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER).orEmpty()
        val newPassword = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmationNewPassword.text.toString()
        if (isValidResetPasswordForm(newPassword, confirmPassword)) {
            viewModel.resetPassword(phoneNumber, binding.etNewPassword.text.toString())
        }
    }
}