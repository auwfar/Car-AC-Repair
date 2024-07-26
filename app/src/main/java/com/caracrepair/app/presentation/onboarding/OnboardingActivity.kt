package com.caracrepair.app.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.databinding.ActivityOnboardingBinding
import com.caracrepair.app.presentation.choosemapslocation.ChooseMapsLocationActivity
import com.caracrepair.app.presentation.main.MainActivity
import com.caracrepair.app.presentation.servicepayment.ServicePaymentActivity
import com.caracrepair.app.presentation.signup.SignUpActivity
import com.caracrepair.app.presentation.signin.SignInActivity
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, OnboardingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }

    private lateinit var binding: ActivityOnboardingBinding

    @Inject
    lateinit var generalPreference: GeneralPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (generalPreference.getUser() != null) {
            startActivity(MainActivity.createIntent(this))
            return
        }

        with(binding) {
            btnCreateAccount.setOnClickListener {
                startActivity(SignUpActivity.createIntent(this@OnboardingActivity))
            }
            btnSignIn.setOnClickListener {
                startActivity(ServicePaymentActivity.createIntent(this@OnboardingActivity, 1))
//                startActivity(SignInActivity.createIntent(this@OnboardingActivity))
            }
        }
    }
}