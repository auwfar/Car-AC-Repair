package com.caracrepair.app.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityMainBinding
import com.caracrepair.app.presentation.signup.SignUpActivity
import com.caracrepair.app.presentation.signin.SignInActivity

class MainActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnCreateAccount.setOnClickListener {
                startActivity(SignUpActivity.createIntent(this@MainActivity))
            }
            btnSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@MainActivity))
            }
        }
    }
}