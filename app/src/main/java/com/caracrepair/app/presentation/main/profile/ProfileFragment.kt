package com.caracrepair.app.presentation.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.caracrepair.app.R
import com.caracrepair.app.databinding.FragmentProfileBinding
import com.caracrepair.app.models.viewparam.ButtonParam
import com.caracrepair.app.models.viewparam.ConfirmationDialogParam
import com.caracrepair.app.presentation.changepassword.ChangePasswordActivity
import com.caracrepair.app.presentation.changeprofile.ChangeProfileActivity
import com.caracrepair.app.presentation.myaddress.MyAddressActivity
import com.caracrepair.app.presentation.mycar.MyCarActivity
import com.caracrepair.app.presentation.onboarding.OnboardingActivity
import com.caracrepair.app.utils.dialog.ConfirmationDialog
import com.caracrepair.app.utils.preferences.GeneralPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var generalPreference: GeneralPreference

    private val signOutConfirmationDialog by lazy {
        ConfirmationDialog.newInstance(ConfirmationDialogParam(
            title = getString(R.string.sign_out),
            message = getString(R.string.desc_sign_out),
            positiveButton = ButtonParam(
                text = getString(R.string.title_no_cancel),
                action = {}
            ),
            negativeButton = ButtonParam(
                text = getString(R.string.title_yes_sign_out),
                action = {
                    generalPreference.clearAllPreferences()
                    startActivity(OnboardingActivity.createIntent(requireContext()))
                }
            )
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvUserName.text = generalPreference.getUser()?.name
            tvUserPhoneNumber.text = generalPreference.getUser()?.phoneNumber

            btnChangeProfile.setOnClickListener {
                startActivity(ChangeProfileActivity.createIntent(requireContext()))
            }
            btnChangePassword.setOnClickListener {
                 startActivity(ChangePasswordActivity.createIntent(requireContext()))
            }
            btnMyAddress.setOnClickListener {
                startActivity(MyAddressActivity.createIntent(requireContext()))
            }
            btnMyCar.setOnClickListener {
                startActivity(MyCarActivity.createIntent(requireContext()))
            }
            tvSignOut.setOnClickListener {
                showSignOutDialog()
            }
        }
    }

    private fun showSignOutDialog() {
        signOutConfirmationDialog.show(childFragmentManager, null)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}