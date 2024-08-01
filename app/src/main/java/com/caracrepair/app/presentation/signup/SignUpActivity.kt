package com.caracrepair.app.presentation.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivitySignUpBinding
import com.caracrepair.app.presentation.otpverification.OtpVerificationActivity
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.presentation.signup.viewmodel.SignUpViewModel
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnCreateAccount.setOnClickListener {
                createAccount()
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@SignUpActivity))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.signUpResult.observe(this) { phoneNumber ->
            if (phoneNumber.isNullOrBlank()) return@observe
            startActivity(OtpVerificationActivity.createIntent(this, OTPType.SignUp(phoneNumber)))
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidRegisterForm(name: String, phoneNumber: String, password: String, confirmationPassword: String): Boolean {
        val isValidName = FormUtil.validateRequired(StringConst.FieldName.NAME, binding.tilName, name)
        val isValidPhoneNumber = FormUtil.validatePhoneNumber(binding.tilPhoneNumber, phoneNumber)
        val isValidPassword = FormUtil.validatePassword(StringConst.FieldName.PASSWORD, binding.tilPassword, password)
        val isValidConfirmationPassword = FormUtil.validateConfirmationPassword(StringConst.FieldName.PASSWORD_CONFIRMATION, binding.tilConfirmationPassword, password, confirmationPassword)
        return isValidName && isValidPhoneNumber && isValidPassword && isValidConfirmationPassword
    }

    private fun createAccount() {
        hideKeyboard()
        val name = binding.etName.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmationPassword = binding.etConfirmationPassword.text.toString()
        if (isValidRegisterForm(name, phoneNumber, password, confirmationPassword)) {
            viewModel.signUp(name, phoneNumber, password)
        }
    }
}