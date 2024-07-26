package com.caracrepair.app.utils.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.caracrepair.app.R
import com.caracrepair.app.databinding.DialogImagePickerDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImagePickerDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogImagePickerDialogBinding
    private var onOptionClickListener: OnOptionClickListener? = null

    fun setOnOptionClickListener(onOptionClickListener: OnOptionClickListener) {
        this.onOptionClickListener = onOptionClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_SheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogImagePickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewGallery.setOnClickListener {
            onOptionClickListener?.onGalleryOptionClicked()
        }

        binding.viewCamera.setOnClickListener {
            onOptionClickListener?.onCameraOptionClicked()
        }
    }

    companion object {
        fun newInstance(): ImagePickerDialog {
            return ImagePickerDialog()
        }
    }

    interface OnOptionClickListener {
        fun onCameraOptionClicked()
        fun onGalleryOptionClicked()
    }
}