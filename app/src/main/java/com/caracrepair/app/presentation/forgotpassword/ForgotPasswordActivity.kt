package com.caracrepair.app.presentation.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityForgotPasswordBinding
import com.caracrepair.app.presentation.otpverification.OtpVerificationActivity
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.signin.SignInActivity
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

    @Inject
    lateinit var generalPreference: GeneralPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnResetPassword.setOnClickListener {
                startActivity(
                    OtpVerificationActivity.createIntent(
                        this@ForgotPasswordActivity,
                        OTPType.ForgotPassword,
                        generalPreference.getUser()?.userId ?: 0
                    )
                )
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@ForgotPasswordActivity))
            }
        }
    }
}