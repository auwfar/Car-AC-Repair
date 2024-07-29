package com.caracrepair.app.presentation.changeprofile

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.databinding.ActivityChangeProfileBinding
import com.caracrepair.app.presentation.changeprofile.viewmodel.ChangeProfileViewModel
import com.caracrepair.app.presentation.successresponse.SuccessResponseActivity
import com.caracrepair.app.presentation.successresponse.constants.SuccessResponseType
import com.caracrepair.app.utils.FileUtil
import com.caracrepair.app.utils.FormUtil
import com.caracrepair.app.utils.dialog.ImagePickerDialog
import com.caracrepair.app.utils.hideKeyboard
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
    private val fileUtil by lazy { FileUtil(this) }
    @Inject
    lateinit var generalPreference: GeneralPreference

    private var profileImageUri: Uri? = null

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
                    profileImageUri = fileUtil.createTemporaryImageUri()
                    val uri = profileImageUri
                    lifecycleScope.launchWhenStarted {
                        if (uri != null) takePicturePreview.launch(uri)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    private val pickImageFromGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        profileImageUri = uri
        loadProfileImage(uri)
    }
    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess: Boolean? ->
        if (isSuccess == true) loadProfileImage(profileImageUri)
    }

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
                .into(binding.ivUserImage)

            etPhoneNumber.setText(user?.phoneNumber)
            etName.setText(user?.name)

            ivUserImage.setOnClickListener {
                imagePickerDialog.show(supportFragmentManager, null)
            }
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
        hideKeyboard()
        val name = binding.etName.text.toString()
        if (FormUtil.validateRequired(StringConst.FieldName.NAME, binding.tilName, name)) {
            val profileImageFile = profileImageUri?.let { fileUtil.createImageFile(it) }
            viewModel.changeProfile(name, profileImageFile)
        }
    }

    private fun loadProfileImage(uri: Uri?) {
        val requestOptions = RequestOptions().transform(CenterCrop(), CircleCrop())
        val requestBuilder = Glide.with(this).load(R.drawable.ic_user_circle).apply(requestOptions)
        Glide.with(this)
            .load(uri)
            .apply(requestOptions)
            .thumbnail(requestBuilder)
            .into(binding.ivUserImage)
    }
}