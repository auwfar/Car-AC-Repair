package com.caracrepair.app.presentation.resetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.databinding.ActivityResetPasswordBinding
import com.caracrepair.app.presentation.resetpassword.viewmodel.ResetPasswordViewModel
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FormUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        fun createIntent(context: Context, userId: Int): Intent {
            return Intent(context, ResetPasswordActivity::class.java).apply {
                putExtra(EXTRA_USER_ID, userId)
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
        val isValidPassword = FormUtil.validatePassword(binding.tilNewPassword, password)
        val isValidConfirmationPassword = FormUtil.validateConfirmationPassword(binding.tilConfirmationNewPassword, password, confirmationPassword)
        return isValidPassword && isValidConfirmationPassword
    }

    private fun resetPassword() {
        val userId = intent.getIntExtra(EXTRA_USER_ID, 0)
        val newPassword = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmationNewPassword.text.toString()
        if (isValidResetPasswordForm(newPassword, confirmPassword)) {
            viewModel.resetPassword(userId, binding.etNewPassword.text.toString())
        }
    }
}