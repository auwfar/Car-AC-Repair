package com.caracrepair.app.presentation.successresponse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivitySuccessResponseBinding
import com.caracrepair.app.presentation.onboarding.OnboardingActivity
import com.caracrepair.app.presentation.servicedetail.ServiceDetailActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType

class SuccessResponseActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SUCCESS_RESPONSE_TYPE = "EXTRA_SUCCESS_RESPONSE_TYPE"
        private const val EXTRA_ID = "EXTRA_ID"
        fun createIntent(context: Context, successResponseType: SuccessResponseType, id: String? = null): Intent {
            return Intent(context, SuccessResponseActivity::class.java).apply {
                putExtra(EXTRA_SUCCESS_RESPONSE_TYPE, successResponseType)
                id?.let { putExtra(EXTRA_ID, it) }
            }
        }
    }

    private lateinit var binding: ActivitySuccessResponseBinding

    private var successResponseType: SuccessResponseType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessResponseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        successResponseType = intent.getParcelableExtra(EXTRA_SUCCESS_RESPONSE_TYPE) ?: SuccessResponseType.SignUp
        setupViews()
    }

    private fun setupViews() {
        val successResponseType = successResponseType ?: return
        with(binding) {
            tvAction.setOnClickListener {
                nextNavigation()
            }
            ivBack.setOnClickListener {
                nextNavigation()
            }

            when (successResponseType) {
                SuccessResponseType.SignUp -> {
                    ivSuccess.setImageResource(R.drawable.img_driving)
                    tvSuccessTitle.text = getString(R.string.title_account_successfully_created)
                    tvSuccessDescription.text = getString(R.string.desc_account_successfully_created)
                    tvAction.text = getString(R.string.sign_in_here)
                }
                SuccessResponseType.ResetPassword -> {
                    ivSuccess.setImageResource(R.drawable.img_fingerprint)
                    tvSuccessTitle.text = getString(R.string.title_password_successfully_edited)
                    tvSuccessDescription.text = getString(R.string.desc_password_successfully_edited)
                    tvAction.text = getString(R.string.sign_in_here)
                }
                SuccessResponseType.Pay -> {
                    ivSuccess.setImageResource(R.drawable.img_wallet)
                    tvSuccessTitle.text = getString(R.string.title_proof_image_successfully_uploaded)
                    tvSuccessDescription.text = getString(R.string.desc_proof_image_successfully_uploaded)
                    tvAction.text = getString(R.string.back)
                }
                SuccessResponseType.ChangeProfile -> {
                    ivSuccess.setImageResource(R.drawable.img_profile_interface)
                    tvSuccessTitle.text = getString(R.string.title_profile_successfully_updated)
                    tvSuccessDescription.text = getString(R.string.desc_profile_successfully_updated)
                    tvAction.text = getString(R.string.back)
                }
                SuccessResponseType.BookingService -> {
                    ivSuccess.setImageResource(R.drawable.img_booking)
                    tvSuccessTitle.text = getString(R.string.title_service_successfully_booking)
                    tvSuccessDescription.text = getString(R.string.desc_service_successfully_booking)
                    tvAction.text = getString(R.string.title_see_detail)
                }
                SuccessResponseType.RescheduleService -> {
                    ivSuccess.setImageResource(R.drawable.img_reschedule)
                    tvSuccessTitle.text = getString(R.string.title_service_successfully_rescheduled)
                    tvSuccessDescription.text = getString(R.string.desc_service_successfully_rescheduled)
                    tvAction.text = getString(R.string.title_see_detail)
                }
            }
        }
    }

    private fun nextNavigation() {
        when (successResponseType) {
            SuccessResponseType.SignUp, SuccessResponseType.ResetPassword, SuccessResponseType.ChangeProfile -> {
                startActivity(OnboardingActivity.createIntent(this@SuccessResponseActivity))
            }
            SuccessResponseType.BookingService -> {
                startActivity(ServiceDetailActivity.createIntent(this@SuccessResponseActivity, intent.getStringExtra(EXTRA_ID).orEmpty()))
                finishAffinity()
            }
            SuccessResponseType.RescheduleService, SuccessResponseType.Pay -> finish()
            else -> finish()
        }
    }

    override fun onBackPressed() {
        nextNavigation()
        super.onBackPressed()
    }
}