package com.caracrepair.app.presentation.repairshopdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityRepairShopDetailBinding

class RepairShopDetailActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RepairShopDetailActivity::class.java)
        }
    }

    private lateinit var binding: ActivityRepairShopDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }

            val requestOptions = RequestOptions().transform(FitCenter(), RoundedCorners(16))
            val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
            Glide.with(root)
                .load("https://www.caracrepair.com/assets/images/repair-shop/repair-shop-1.jpg")
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivRepairShop)

            tvTitle.text = "Car AC Repair"
            tvDescription.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"
            tvAddress.text = "Jl. Raya Kuta No. 88, Kuta, Bali"
            tvPhone.text = "+62 812-3456-7890"
        }
    }
}