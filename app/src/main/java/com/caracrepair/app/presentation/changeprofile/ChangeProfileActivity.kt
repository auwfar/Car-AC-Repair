package com.caracrepair.app.presentation.changeprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityChangeProfileBinding
import com.caracrepair.app.presentation.changeprofile.viewmodel.ChangeProfileViewModel
import com.caracrepair.app.presentation.otpverification.OtpVerificationActivity
import com.caracrepair.app.presentation.otpverification.constants.OTPType
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangeProfileActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChangeProfileActivity::class.java)
        }
    }

    private lateinit var binding: ActivityChangeProfileBinding
    private val viewModel by viewModels<ChangeProfileViewModel>()
    @Inject
    lateinit var generalPreference: GeneralPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupViews()
    }

    private fun setupViews() {
        val user = generalPreference.getUser()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }

            val requestOptions = RequestOptions().transform(CenterCrop(), CircleCrop())
            val requestBuilder = Glide.with(root).load(R.drawable.ic_user_circle).apply(requestOptions)
            Glide.with(root)
                .load(user?.profileImage)
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivUserImage)

            etPhoneNumber.setText(user?.phoneNumber)
            etName.setText(user?.name)

            btnSave.setOnClickListener {
                changeProfile()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.changeProfileResult.observe(this) { user ->
            if (user == null) return@observe
            startActivity(SuccessResponseActivity.createIntent(this, SuccessResponseType.ChangeProfile))
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeProfile() {
        val name = binding.etName.text.toString()
        if (FormUtil.validateRequired(StringConst.FieldName.NAME, binding.tilName, name)) {
            viewModel.changeProfile(name)
        }
    }
}