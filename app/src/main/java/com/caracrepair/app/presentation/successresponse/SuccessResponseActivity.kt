package com.caracrepair.app.presentation.successresponse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivitySuccessResponseBinding
import com.caracrepair.app.presentation.main.MainActivity
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType

class SuccessResponseActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SUCCESS_RESPONSE_TYPE = "EXTRA_SUCCESS_RESPONSE_TYPE"
        fun createIntent(context: Context, successResponseType: SuccessResponseType): Intent {
            return Intent(context, SuccessResponseActivity::class.java).apply {
                putExtra(EXTRA_SUCCESS_RESPONSE_TYPE, successResponseType)
            }
        }
    }

    private lateinit var binding: ActivitySuccessResponseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessResponseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        with(binding) {
            ivBack.setOnClickListener {
                startActivity(MainActivity.createIntent(this@SuccessResponseActivity))
            }
            tvSignIn.setOnClickListener {
                startActivity(MainActivity.createIntent(this@SuccessResponseActivity))
            }
        }
    }

    private fun setupViews() {
        val successResponseType = intent.getParcelableExtra<SuccessResponseType>(EXTRA_SUCCESS_RESPONSE_TYPE) ?: SuccessResponseType.SignUp
        with(binding) {
            when (successResponseType) {
                SuccessResponseType.SignUp -> {
                    ivSuccess.setImageResource(R.drawable.img_driving)
                    tvSuccessTitle.text = getString(R.string.title_account_successfully_created)
                    tvSuccessDescription.text = getString(R.string.desc_account_successfully_created)
                }
                SuccessResponseType.ResetPassword -> {
                    ivSuccess.setImageResource(R.drawable.img_fingerprint)
                    tvSuccessTitle.text = getString(R.string.title_password_successfully_edited)
                    tvSuccessDescription.text = getString(R.string.desc_password_successfully_edited)
                }
            }
        }
    }
}