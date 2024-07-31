package com.caracrepair.app.presentation.servicepayment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityServicePaymentBinding
import com.caracrepair.app.presentation.servicedetail.adapter.FeeDetailAdapter
import com.caracrepair.app.presentation.servicedetail.viewparam.ServiceDetail
import com.caracrepair.app.presentation.servicepayment.viewmodel.ServicePaymentViewModel
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FileUtil
import com.caracrepair.app.utils.dialog.ImagePickerDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicePaymentActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SERVICE_DETAIL = "extra_service_detail"
        fun createIntent(context: Context, serviceDetail: ServiceDetail): Intent {
            return Intent(context, ServicePaymentActivity::class.java).apply {
                putExtra(EXTRA_SERVICE_DETAIL, serviceDetail)
            }
        }
    }

    private lateinit var binding: ActivityServicePaymentBinding
    private val viewModel by viewModels<ServicePaymentViewModel>()
    private val feeDetailAdapter by lazy { FeeDetailAdapter() }
    private val fileUtil by lazy { FileUtil(this) }

    private var serviceId = ""
    private var paymentProofImageUri: Uri? = null

    private val imagePickerDialog by lazy {
        ImagePickerDialog.newInstance().apply {
            setOnOptionClickListener(object : ImagePickerDialog.OnOptionClickListener {
                override fun onCameraOptionClicked() {
                    requestCameraPermission.launch(fileUtil.storagePermissions)
                }

                override fun onGalleryOptionClicked() {
                    pickImageFromGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            })
        }
    }
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var isGranted = false
            permissions.forEach {
                if (!it.value) {
                    isGranted = false
                    return@forEach
                }
                isGranted = true
            }
            if (isGranted) {
                try {
                    paymentProofImageUri = fileUtil.createTemporaryImageUri()
                    val uri = paymentProofImageUri
                    lifecycleScope.launchWhenStarted {
                        if (uri != null) takePicturePreview.launch(uri)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    private val pickImageFromGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        loadProofPaymentImage(uri)
    }
    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess: Boolean? ->
        if (isSuccess == true) loadProofPaymentImage(paymentProofImageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.serviceDetail = intent.getParcelableExtra(EXTRA_SERVICE_DETAIL)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            llActionChangePaymentProofImage.setOnClickListener {
                imagePickerDialog.show(supportFragmentManager, null)
            }
            flChangePaymentProofImage.setOnClickListener {
                imagePickerDialog.show(supportFragmentManager, null)
            }
            btnUpload.setOnClickListener {
                val proofImageFile = paymentProofImageUri?.let { fileUtil.createImageFile(it) }
                if (proofImageFile != null) {
                    viewModel.uploadPaymentProofImage(serviceId, proofImageFile)
                }
            }
        }

        observeViewModel()
        setupRecyclerView()
        setupViews()
    }

    private fun observeViewModel() {
        viewModel.uploadPaymentProofImageResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            startActivity(SuccessResponseActivity.createIntent(this@ServicePaymentActivity, SuccessResponseType.Pay))
            finish()
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViews() {
        val detail = viewModel.serviceDetail
        with(binding) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(8))
            val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
            Glide.with(root)
                .load(detail?.repairShop?.imageUrl)
                .apply(requestOptions)
                .thumbnail(requestBuilder)
                .into(ivRepairShop)

            tvOrderId.text = detail?.orderId.toString()
            tvOrderDate.text = detail?.orderTime
            tvServiceTime.text = detail?.serviceAt
            tvCarName.text = detail?.carName
            tvCarDistance.text = detail?.carDistance
            tvRepairShopName.text = detail?.repairShop?.name
            tvRepairShopAddress.text = detail?.repairShop?.address
            tvServiceMechanic.text = detail?.mechanicName?.ifBlank { "-" }

            tvFeeTotal.text = detail?.fee?.feeTotal
            feeDetailAdapter.setItems(detail?.fee?.fees.orEmpty())
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvFeeDetail) {
            layoutManager = LinearLayoutManager(this@ServicePaymentActivity)
            adapter = feeDetailAdapter
        }
    }

    private fun loadProofPaymentImage(uri: Uri?) {
        binding.llActionChangePaymentProofImage.isVisible = false
        binding.cvPaymentProofImage.isVisible = true

        Glide.with(this)
            .load(uri)
            .into(binding.ivPaymentProofImage)
    }
}