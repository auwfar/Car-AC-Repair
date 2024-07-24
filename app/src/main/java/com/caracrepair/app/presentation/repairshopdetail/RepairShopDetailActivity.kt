package com.caracrepair.app.presentation.repairshopdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityRepairShopDetailBinding
import com.caracrepair.app.utils.GMapsUtil
import com.caracrepair.app.utils.WhatsAppUtil

class RepairShopDetailActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_REPAIR_SHOP_ID = "extra_repair_shop_id"
        fun createIntent(context: Context, repairShopId: Int): Intent {
            return Intent(context, RepairShopDetailActivity::class.java).apply {
                putExtra(EXTRA_REPAIR_SHOP_ID, repairShopId)
            }
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
            ivContactAdmin.setOnClickListener {
                val message =
                    getString(R.string.desc_contact_admin_from_repair_shop_detail, "Car AC Repair")
                WhatsAppUtil.sendWhatsAppMessage(
                    this@RepairShopDetailActivity,
                    "+628984119934",
                    message
                )
            }
            btnCheckLocation.setOnClickListener {
                val latitude = -6.200000
                val longitude = 106.816666
                GMapsUtil.pinLocationMap(this@RepairShopDetailActivity, latitude, longitude)
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