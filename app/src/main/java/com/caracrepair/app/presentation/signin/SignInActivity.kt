package com.caracrepair.app.presentation.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivitySignInBinding
import com.caracrepair.app.presentation.createaccount.CreateAccountActivity

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
            tvCreateAccount.setOnClickListener {
                startActivity(CreateAccountActivity.createIntent(this@SignInActivity))
            }
        }
    }
}