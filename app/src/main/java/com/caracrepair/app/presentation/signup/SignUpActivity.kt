package com.caracrepair.app.presentation.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivitySignUpBinding
import com.caracrepair.app.presentation.otpverification.OtpVerificationActivity
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.signin.SignInActivity

class SignUpActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnCreateAccount.setOnClickListener {
                startActivity(OtpVerificationActivity.createIntent(this@SignUpActivity, OTPType.SignUp))
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@SignUpActivity))
            }
        }
    }
}