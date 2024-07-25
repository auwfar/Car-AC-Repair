package com.caracrepair.app.utils.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.caracrepair.app.R
import com.caracrepair.app.databinding.DialogConfirmationBinding
import com.caracrepair.app.models.viewparam.ConfirmationDialogParam
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmationDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogConfirmationBinding
    private var param: ConfirmationDialogParam? = null

    companion object {
        private const val EXTRA_CONFIRMATION_DIALOG_PARAM = "EXTRA_CONFIRMATION_DIALOG_PARAM"
        fun newInstance(confirmationDialogParam: ConfirmationDialogParam): ConfirmationDialog {
            return ConfirmationDialog().apply {
                arguments = bundleOf(EXTRA_CONFIRMATION_DIALOG_PARAM to confirmationDialogParam)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_SheetDialogTheme)
        param = arguments?.getParcelable(EXTRA_CONFIRMATION_DIALOG_PARAM)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogConfirmationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupView()
    }

    private fun setupView() {
        with(binding) {
            tvTitle.text = param?.title
            tvMessage.text = param?.message
            btnTopAction.text = param?.negativeButton?.text
            btnBottomAction.text = param?.positiveButton?.text
        }
    }

    private fun setupAction() {
        with(binding) {
            btnTopAction.setOnClickListener {
                param?.negativeButton?.action?.invoke()
                dismiss()
            }
            btnBottomAction.setOnClickListener {
                param?.positiveButton?.action?.invoke()
                dismiss()
            }
        }
    }
}