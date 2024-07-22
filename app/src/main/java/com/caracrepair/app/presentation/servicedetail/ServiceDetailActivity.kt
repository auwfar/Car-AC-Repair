package com.caracrepair.app.presentation.servicedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityServiceDetailBinding
import com.caracrepair.app.presentation.rescheduleservice.RescheduleServiceActivity
import com.caracrepair.app.presentation.servicedetail.adapter.StatusAdapter
import com.caracrepair.app.presentation.servicedetail.viewparam.FeeDetailItem
import com.caracrepair.app.presentation.servicedetail.viewparam.FeeItem
import com.caracrepair.app.presentation.servicedetail.viewparam.StatusItem

class ServiceDetailActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ServiceDetailActivity::class.java)
        }
    }

    private lateinit var binding: ActivityServiceDetailBinding
    private val statusAdapter by lazy { StatusAdapter() }
    private val status = listOf(
        StatusItem(
            statusTitle = "Konfirmasi Pengecekan",
            statusDescription = "Kami menemukan kompresor yang lemah, kebocoran freon, dan filter kabin yang kotor. Kami merekomendasikan penggantian kompresor, perbaikan kebocoran, pengisian ulang freon, serta penggantian filter kabin untuk memastikan AC bekerja optimal. Mohon konfirmasi jika Anda ingin melanjutkan perbaikan ini.",
            statusTime = "24 Juni 2024, 17:15",
            fee = FeeItem(
                feeTotal = "Rp 1.000.000",
                fees = listOf(
                    FeeDetailItem(
                        feeName = "Biaya Layanan",
                        feeTotal = "Rp 500.000"
                    ),
                    FeeDetailItem(
                        feeName = "Biaya Sparepart",
                        feeTotal = "Rp 500.000"
                    )
                )
            )
        ),
        StatusItem(
            statusTitle = "Pengecekan",
            statusDescription = "Kendaraan Anda sedang dalam tahap pengecekan oleh teknisi kami. Kami sedang melakukan pemeriksaan menyeluruh untuk mengidentifikasi semua masalah potensial.",
            statusTime = "24 Juni 2024, 17:10",
            fee = null
        ),
        StatusItem(
            statusTitle = "Mobil Sedang Dijemput",
            statusDescription = "Kami sedang menjemput mobil Anda di lokasi yang telah disepakati. Pastikan mobil siap untuk diambil. Kami akan menghubungi Anda setelah mobil sampai di bengkel.",
            statusTime = "24 Juni 2024, 17:00",
            fee = null
        ),
        StatusItem(
            statusTitle = "Menunggu Antrian",
            statusDescription = "Pesanan Anda telah diterima dan sedang menunggu giliran untuk dilayani oleh mekanik kami. Kami akan segera memproses pesanan Anda.",
            statusTime = "24 Juni 2024, 16:08",
            fee = null
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceDetailBinding.inflate(layoutInflater)
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
            tvComplaint.text = "AC tidak dingin"
            tvOrderType.text = "Antar/Ambil sendiri ke Bengkel"
            tvServiceMechanic.text = "Budi"
            tvServiceStatus.text = "Konfirmasi Pengecekan"

            tvRepairShopAddress.isVisible = true // Show Only for Service Type "Pelayanan Antar/Jemput dari Bengkel"
            tvServiceAddress.isVisible = false // Show Only for Service Type "Antar/Ambil sendiri ke Bengkel"

            btnReschedule.setOnClickListener {
                startActivity(RescheduleServiceActivity.createIntent(this@ServiceDetailActivity))
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvStatus) {
            layoutManager = LinearLayoutManager(this@ServiceDetailActivity)
            adapter = statusAdapter.apply {
                setItems(status)
            }
        }
    }
}