package com.caracrepair.app.presentation.changeprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityChangeProfileBinding
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType

class ChangeProfileActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChangeProfileActivity::class.java)
        }
    }

    private lateinit var binding: ActivityChangeProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }

            val requestOptions = RequestOptions().transform(CenterCrop(), CircleCrop())
            val requestBuilder = Glide.with(root).load(R.drawable.ic_user_circle).apply(requestOptions)
            Glide.with(root)
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivUserImage)

            btnSave.setOnClickListener {
                startActivity(SuccessResponseActivity.createIntent(this@ChangeProfileActivity, SuccessResponseType.ChangeProfile))
            }
        }
    }
}