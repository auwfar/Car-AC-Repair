package com.caracrepair.app.presentation.servicedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.consts.ServiceTypeConst
import com.caracrepair.app.databinding.ActivityServiceDetailBinding
import com.caracrepair.app.presentation.main.MainActivity
import com.caracrepair.app.presentation.rescheduleservice.RescheduleServiceActivity
import com.caracrepair.app.presentation.servicedetail.adapter.ServiceLogAdapter
import com.caracrepair.app.presentation.servicedetail.viewmodel.ServiceDetailViewModel
import com.caracrepair.app.presentation.servicedetail.viewparam.ServiceDetail
import com.caracrepair.app.presentation.servicepayment.ServicePaymentActivity
import com.caracrepair.app.utils.WhatsAppUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceDetailActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SERVICE_ID = "extra_service_id"
        fun createIntent(context: Context, serviceId: String): Intent {
            return Intent(context, ServiceDetailActivity::class.java).apply {
                putExtra(EXTRA_SERVICE_ID, serviceId)
            }
        }
    }

    private lateinit var binding: ActivityServiceDetailBinding
    private val viewModel by viewModels<ServiceDetailViewModel>()
    private val serviceLogAdapter by lazy { ServiceLogAdapter() }

    private var serviceId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceId = intent.getStringExtra(EXTRA_SERVICE_ID).orEmpty()

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnReload.setOnClickListener {
                viewModel.getServiceDetail(serviceId)
            }
        }

        observeViewModel()
        setupRecyclerView()

        viewModel.getServiceDetail(serviceId)
    }

    private fun observeViewModel() {
        viewModel.serviceDetailResult.observe(this) {
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

    private fun setupViews(detail: ServiceDetail) {
        with(binding) {
            ivContactAdmin.isVisible = true
            ivContactAdmin.setOnClickListener {
                val message = getString(
                    R.string.desc_contact_admin_from_service_detail,
                    detail.repairShop.name,
                    detail.orderId.toString()
                )
                WhatsAppUtil.sendWhatsAppMessage(
                    this@ServiceDetailActivity,
                    detail.repairShop.adminPhoneNumber,
                    message
                )
            }

            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(8))
            val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
            Glide.with(root)
                .load(detail.repairShop.imageUrl)
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivRepairShop)

            tvOrderId.text = detail.orderId
            tvOrderDate.text = detail.orderTime
            tvServiceTime.text = detail.serviceAt
            tvCarName.text = detail.carName
            tvCarDistance.text = detail.carDistance
            tvRepairShopName.text = detail.repairShop.name
            tvRepairShopAddress.text = detail.repairShop.address
            tvComplaint.text = detail.complaint
            tvOrderType.text = if (detail.serviceType == ServiceTypeConst.TYPE_DELIVER) {
                getString(R.string.title_deliver_to_repair_shop)
            } else {
                getString(R.string.title_pick_up_from_repair_shop)
            }
            tvServiceAddress.text = detail.pickUpAddress
            tvServiceMechanic.text = detail.mechanicName.ifBlank { "-" }
            tvServiceStatus.text = detail.status

            tvRepairShopAddress.isVisible = (detail.serviceType == ServiceTypeConst.TYPE_DELIVER)
            tvServiceAddress.isVisible = (detail.serviceType == ServiceTypeConst.TYPE_PICKUP)

            serviceLogAdapter.setItems(detail.serviceLogs)

            if (detail.isAbleToPay) {
                llAction.isVisible = true
                btnPay.isVisible = true
                btnPay.setOnClickListener {
                    startActivity(ServicePaymentActivity.createIntent(this@ServiceDetailActivity, detail))
                }
            } else if (detail.isAbleToReschedule) {
                llAction.isVisible = true
                btnReschedule.isVisible = true
                btnReschedule.setOnClickListener {
                    startActivity(RescheduleServiceActivity.createIntent(this@ServiceDetailActivity, serviceId))
                }
            } else {
                llAction.isVisible = false
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvStatus) {
            layoutManager = LinearLayoutManager(this@ServiceDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = serviceLogAdapter
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            startActivity(MainActivity.createIntent(this))
        } else {
            super.onBackPressed()
        }
    }
}