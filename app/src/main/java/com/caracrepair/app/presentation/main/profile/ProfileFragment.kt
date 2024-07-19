package com.caracrepair.app.presentation.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.caracrepair.app.databinding.FragmentProfileBinding
import com.caracrepair.app.presentation.mycar.MyCarActivity

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

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
            // Remove later after integrate to server
            tvUserName.text = "Ach Chadil Auwfar"
            tvUserPhoneNumber.text = "08984119934"

            btnMyAddress.setOnClickListener {
                startActivity(MyCarActivity.createIntent(requireContext()))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}