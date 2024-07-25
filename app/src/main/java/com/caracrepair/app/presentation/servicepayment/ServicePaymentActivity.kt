package com.caracrepair.app.presentation.servicepayment

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
import com.caracrepair.app.databinding.ActivityServicePaymentBinding
import com.caracrepair.app.presentation.servicedetail.adapter.FeeDetailAdapter
import com.caracrepair.app.presentation.servicepayment.viewmodel.ServicePaymentViewModel
import com.caracrepair.app.presentation.servicepayment.viewparam.ServicePayment
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType

class ServicePaymentActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SERVICE_ID = "extra_service_id"
        fun createIntent(context: Context, serviceId: Int): Intent {
            return Intent(context, ServicePaymentActivity::class.java).apply {
                putExtra(EXTRA_SERVICE_ID, serviceId)
            }
        }
    }

    private lateinit var binding: ActivityServicePaymentBinding
    private val viewModel by viewModels<ServicePaymentViewModel>()
    private val feeDetailAdapter by lazy { FeeDetailAdapter() }

    private var serviceId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceId = intent.getIntExtra(EXTRA_SERVICE_ID, 0)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnReload.setOnClickListener {
                viewModel.getServicePayment(serviceId)
            }
        }

        observeViewModel()
        setupRecyclerView()

        viewModel.getServicePayment(serviceId)
    }

    private fun observeViewModel() {
        viewModel.servicePaymentResult.observe(this) {
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

    private fun setupViews(detail: ServicePayment) {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }

            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(8))
            val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
            Glide.with(root)
                .load(detail.repairShop.imageUrl)
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivRepairShop)

            tvOrderId.text = detail.orderId.toString()
            tvOrderDate.text = detail.orderTime
            tvServiceTime.text = detail.serviceTime
            tvCarName.text = detail.carName
            tvCarDistance.text = detail.carDistance
            tvRepairShopName.text = detail.repairShop.name
            tvRepairShopAddress.text = detail.repairShop.address
            tvServiceMechanic.text = detail.mechanicName

            tvFeeTotal.text = detail.fee.feeTotal
            feeDetailAdapter.setItems(detail.fee.fees)
            btnPay.setOnClickListener {
                startActivity(SuccessResponseActivity.createIntent(this@ServicePaymentActivity, SuccessResponseType.Pay))
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFeeDetail) {
            layoutManager = LinearLayoutManager(this@ServicePaymentActivity)
            adapter = feeDetailAdapter
        }
    }
}