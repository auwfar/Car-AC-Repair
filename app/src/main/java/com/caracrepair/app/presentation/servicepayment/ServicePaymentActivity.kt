package com.caracrepair.app.presentation.servicepayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityServicePaymentBinding
import com.caracrepair.app.presentation.servicedetail.adapter.FeeDetailAdapter
import com.caracrepair.app.presentation.servicedetail.viewparam.FeeDetailItem

class ServicePaymentActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ServicePaymentActivity::class.java)
        }
    }

    private lateinit var binding: ActivityServicePaymentBinding
    private val feeDetailAdapter by lazy { FeeDetailAdapter() }
    private val fees = listOf(
        FeeDetailItem(
            feeName = "Biaya Layanan",
            feeTotal = "Rp 500.000"
        ),
        FeeDetailItem(
            feeName = "Biaya Sparepart",
            feeTotal = "Rp 500.000"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupRecyclerView()
    }

    private fun setupViews() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }

            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(8))
            val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
            Glide.with(root)
                .load("https://www.caracrepair.com/assets/images/repair-shop/repair-shop-1.jpg")
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivRepairShop)

            tvOrderId.text = "Order: S-46726733"
            tvOrderDate.text = "24 Juni 2024, 16:08"
            tvServiceTime.text = "24 Juni 2024, 17:00"
            tvCarName.text = "Toyota Avanza"
            tvCarDistance.text = "100.000 km"
            tvRepairShopName.text = "Bengkel AC Mobil"
            tvRepairShopAddress.text = "Jl. Raya Bogor No. 1, Jakarta"
            tvServiceMechanic.text = "Budi"

            tvFeeTotal.text = "Rp 1.000.000"
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFeeDetail) {
            layoutManager = LinearLayoutManager(this@ServicePaymentActivity)
            adapter = feeDetailAdapter.apply {
                setItems(fees)
            }
        }
    }
}