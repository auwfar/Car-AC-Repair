package com.caracrepair.app.presentation.repairshopdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityRepairShopDetailBinding
import com.caracrepair.app.presentation.repairshopdetail.viewmodel.RepairShopDetailViewModel
import com.caracrepair.app.presentation.repairshopdetail.viewparam.RepairShopDetail
import com.caracrepair.app.utils.GMapsUtil
import com.caracrepair.app.utils.WhatsAppUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    private val viewModel by viewModels<RepairShopDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repairShopId = intent.getIntExtra(EXTRA_REPAIR_SHOP_ID, 0)

        observeViewModel()

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnReload.setOnClickListener {
            viewModel.getRepairShopDetail(repairShopId)
        }
        viewModel.getRepairShopDetail(repairShopId)
    }

    private fun observeViewModel() {
        viewModel.repairShopDetailResult.observe(this) {
            binding.llErrorView.isVisible = false
            setupViews(it)
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            binding.llErrorView.isVisible = true
            binding.tvErrorDescription.text = message
        }
    }

    private fun setupViews(detail: RepairShopDetail) {
        with(binding) {
            ivContactAdmin.isVisible = true
            ivContactAdmin.setOnClickListener {
                val message =
                    getString(R.string.desc_contact_admin_from_repair_shop_detail, detail.name)
                WhatsAppUtil.sendWhatsAppMessage(
                    this@RepairShopDetailActivity,
                    detail.adminPhoneNumber,
                    message
                )
            }
            btnCheckLocation.setOnClickListener {
                GMapsUtil.pinLocationMap(this@RepairShopDetailActivity, detail.location.lat, detail.location.long)
            }

            val requestOptions = RequestOptions().transform(FitCenter(), RoundedCorners(16))
            val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
            Glide.with(root)
                .load(detail.imageUrl)
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivRepairShop)

            tvTitle.text = detail.name
            tvDescription.text = detail.description
            tvAddress.text = detail.address
            tvPhone.text = detail.adminPhoneNumber
        }
    }
}