package com.caracrepair.app.presentation.changepassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityChangePasswordBinding
import com.caracrepair.app.presentation.changepassword.viewmodel.ChangePasswordViewModel
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FormUtil

class ChangePasswordActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChangePasswordActivity::class.java)
        }
    }

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel by viewModels<ChangePasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            resetPassword()
        }
    }

    private fun observeViewModel() {
        viewModel.changePasswordResult.observe(this) {
            startActivity(SuccessResponseActivity.createIntent(this, SuccessResponseType.ResetPassword))
        }
        viewModel.loadingState.observe(this) {
            binding.flLoading.isVisible = it
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidResetPasswordForm(password: String, newPassword: String, confirmationPassword: String): Boolean {
        val isValidPassword = FormUtil.validatePassword(StringConst.FieldName.PASSWORD, binding.tilOldPassword, password)
        val isValidNewPassword = FormUtil.validatePassword(StringConst.FieldName.NEW_PASSWORD, binding.tilNewPassword, newPassword)
        val isValidConfirmationNewPassword = FormUtil.validateConfirmationPassword(StringConst.FieldName.NEW_PASSWORD_CONFIRMATION, binding.tilConfirmationNewPassword, password, confirmationPassword)
        return isValidPassword && isValidNewPassword && isValidConfirmationNewPassword
    }

    private fun resetPassword() {
        val password = binding.etOldPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmationNewPassword.text.toString()
        if (isValidResetPasswordForm(password, newPassword, confirmPassword)) {
            viewModel.changePassword(password, newPassword)
        }
    }
}