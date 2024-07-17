package com.caracrepair.app.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityMainBinding
import com.caracrepair.app.presentation.createaccount.CreateAccountActivity
import com.caracrepair.app.presentation.signin.SignInActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnCreateAccount.setOnClickListener {
                startActivity(CreateAccountActivity.createIntent(this@MainActivity))
            }
            btnSignIn.setOnClickListener {
                startActivity(SignInActivity.createIntent(this@MainActivity))
            }
        }
    }
}