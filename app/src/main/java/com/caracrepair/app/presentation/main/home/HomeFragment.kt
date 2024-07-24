package com.caracrepair.app.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.FragmentHomeBinding
import com.caracrepair.app.presentation.main.home.adapter.LastServiceAdapter
import com.caracrepair.app.presentation.main.home.adapter.OnProgressServiceAdapter
import com.caracrepair.app.presentation.main.home.adapter.RepairShopSliderAdapter
import com.caracrepair.app.presentation.main.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val repairShopSliderAdapter by lazy { RepairShopSliderAdapter() }
    private val onProgressServiceAdapter by lazy { OnProgressServiceAdapter() }
    private val lastServiceAdapter by lazy { LastServiceAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupRecyclerViews()
        setupSliderViews()

        with(binding) {
            tvUserName.text = viewModel.getUser()?.name
            tvUserPhoneNumber.text = viewModel.getUser()?.phoneNumber
        }

        viewModel.getHomePage()
    }

    private fun observeViewModel() {
        viewModel.onProgressServicesResult.observe(viewLifecycleOwner) { onProgressServices ->
            binding.rvOnProgressService.isVisible = onProgressServices.isNotEmpty()
            binding.tvEmptyOnProgressServices.isVisible = onProgressServices.isEmpty()

            onProgressServiceAdapter.setItems(onProgressServices)
        }
        viewModel.lastServicesResult.observe(viewLifecycleOwner) { lastServices ->
            binding.rvLastService.isVisible = lastServices.isNotEmpty()
            binding.tvEmptyLastServices.isVisible = lastServices.isEmpty()

            lastServiceAdapter.setItems(lastServices)
        }
        viewModel.repairShopsResult.observe(viewLifecycleOwner) { repairShops ->
            repairShopSliderAdapter.setItems(repairShops)
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvOnProgressService.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvOnProgressService.adapter = onProgressServiceAdapter

            rvLastService.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvLastService.adapter = lastServiceAdapter
        }
    }

    private fun setupSliderViews() {
        with(binding) {
            vpRepairShopSlider.adapter = repairShopSliderAdapter
            TabLayoutMediator(intoTabLayout, vpRepairShopSlider) { _, _ -> }.attach()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}