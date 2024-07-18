package com.caracrepair.app.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.FragmentHomeBinding
import com.caracrepair.app.presentation.main.home.adapter.LastServiceAdapter
import com.caracrepair.app.presentation.main.home.adapter.OnProgressServiceAdapter
import com.caracrepair.app.presentation.main.home.adapter.RepairShopSliderAdapter
import com.caracrepair.app.presentation.main.home.viewparam.LastServiceItem
import com.caracrepair.app.presentation.main.home.viewparam.OnProgressServiceItem
import com.caracrepair.app.presentation.main.home.viewparam.RepairShopSlider
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val repairShopSliderAdapter by lazy { RepairShopSliderAdapter() }
    private val onProgressServiceAdapter by lazy { OnProgressServiceAdapter() }
    private val lastServiceAdapter by lazy { LastServiceAdapter() }

    private val slider = listOf(
        RepairShopSlider(
            "",
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        ),
        RepairShopSlider(
            "https://www.w3schools.com/w3images/mountains.jpg",
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        ),
        RepairShopSlider(
            "https://www.w3schools.com/w3images/paris.jpg",
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        )
    )

    private val onProgressService = listOf(
        OnProgressServiceItem(1, "Toyota Avanza", "Menunggu Konfirmasi Pembayaran"),
        OnProgressServiceItem(2, "Honda Jazz", "Jadwal Diubah, Menunggu Antrian"),
        OnProgressServiceItem(3, "Suzuki Ertiga", "Menunggu Antrian")
    )

    private val lastService = listOf(
        LastServiceItem(1, "Toyota Avanza", "18 Juli 2024", "1000 KM"),
        LastServiceItem(2, "Honda Jazz", "10 Juli 2024", "1000 KM"),
        LastServiceItem(3, "Suzuki Ertiga", "9 Juli 2024", "1000 KM")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        with(binding) {
            // Remove later after integrate to server
            tvUserName.text = "Ach Chadil Auwfar"
            tvUserPhoneNumber.text = "08984119934"

            vpRepairShopSlider.adapter = repairShopSliderAdapter.apply {
                // Remove later after integrate to server
                setItems(slider)
            }
            TabLayoutMediator(intoTabLayout, vpRepairShopSlider) { _, _ -> }.attach()
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvOnProgressService.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvOnProgressService.adapter = onProgressServiceAdapter.apply {
                // Remove later after integrate to server
                setItems(onProgressService)
            }
            rvLastService.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvLastService.adapter = lastServiceAdapter.apply {
                // Remove later after integrate to server
                setItems(lastService)
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}