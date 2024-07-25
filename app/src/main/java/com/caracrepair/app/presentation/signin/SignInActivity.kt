package com.caracrepair.app.presentation.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivitySignInBinding
import com.caracrepair.app.presentation.signup.SignUpActivity
import com.caracrepair.app.presentation.forgotpassword.ForgotPasswordActivity
import com.caracrepair.app.presentation.main.MainActivity
import com.caracrepair.app.presentation.signin.viewmodel.SignInViewModel
import com.caracrepair.app.utils.FormUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            tvForgotPassword.setOnClickListener {
                startActivity(ForgotPasswordActivity.createIntent(this@SignInActivity))
            }
            tvCreateAccount.setOnClickListener {
                startActivity(SignUpActivity.createIntent(this@SignInActivity))
            }
            btnSignIn.setOnClickListener {
                signIn()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.signInResult.observe(this) {
            startActivity(MainActivity.createIntent(this))
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidPhoneNumberAndPassword(phoneNumber: String, password: String): Boolean {
        val isValidPhoneNumber = FormUtil.validatePhoneNumber(binding.tilPhoneNumber, phoneNumber)
        val isValidPassword = FormUtil.validatePassword(StringConst.FieldName.PASSWORD, binding.tilPassword, password)
        return isValidPhoneNumber && isValidPassword
    }

    private fun signIn() {
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val password = binding.etPassword.text.toString()
        if (isValidPhoneNumberAndPassword(phoneNumber, password)) {
            viewModel.signIn(phoneNumber, password)
        }
    }
}