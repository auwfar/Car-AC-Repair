package com.caracrepair.app.presentation.resetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityResetPasswordBinding
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType

class ResetPasswordActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }

    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnResetPassword.setOnClickListener {
                startActivity(SuccessResponseActivity.createIntent(this@ResetPasswordActivity, SuccessResponseType.ResetPassword))
            }
            tvSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@ResetPasswordActivity))
            }
        }
    }
}