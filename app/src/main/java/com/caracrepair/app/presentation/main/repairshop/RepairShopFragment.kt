package com.caracrepair.app.presentation.main.repairshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.FragmentRepairShopBinding
import com.caracrepair.app.presentation.main.repairshop.adapter.RepairShopAdapter
import com.caracrepair.app.presentation.main.repairshop.viewparam.RepairShopItem

class RepairShopFragment : Fragment() {
    private lateinit var binding: FragmentRepairShopBinding

    private val repairShopAdapter by lazy { RepairShopAdapter() }
    private val repairShop = listOf(
        RepairShopItem(
            1,
            "Bengkel A",
            "Jl. A No. 1",
            "08123456789",
            "",
        ),
        RepairShopItem(
            1,
            "Bengkel B",
            "Jl. B No. 2",
            "08123456789",
            "https://www.w3schools.com/w3images/paris.jpg",
        ),
        RepairShopItem(
            1,
            "Bengkel C",
            "Jl. C No. 3",
            "08123456789",
            "https://www.w3schools.com/w3images/lights.jpg",
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepairShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvRepairShop) {
            layoutManager = LinearLayoutManager(context)
            adapter = repairShopAdapter.apply {
                setItems(repairShop)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RepairShopFragment()
    }
}