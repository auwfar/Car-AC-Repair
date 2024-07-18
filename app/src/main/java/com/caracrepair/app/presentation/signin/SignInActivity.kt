package com.caracrepair.app.presentation.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivitySignInBinding
import com.caracrepair.app.presentation.signup.SignUpActivity
import com.caracrepair.app.presentation.forgotpassword.ForgotPasswordActivity
import com.caracrepair.app.presentation.main.MainActivity

class SignInActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                startActivity(MainActivity.createIntent(this@SignInActivity))
            }
        }
    }
}