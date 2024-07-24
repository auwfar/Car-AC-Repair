package com.caracrepair.app.presentation.main.repairshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.FragmentRepairShopBinding
import com.caracrepair.app.presentation.main.repairshop.adapter.RepairShopAdapter
import com.caracrepair.app.presentation.main.repairshop.viewmodel.RepairShopViewModel
import com.caracrepair.app.presentation.repairshopdetail.RepairShopDetailActivity

class RepairShopFragment : Fragment() {
    private lateinit var binding: FragmentRepairShopBinding

    private val viewModel by viewModels<RepairShopViewModel>()
    private val repairShopAdapter by lazy { RepairShopAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepairShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupRecyclerView()

        binding.btnReload.setOnClickListener {
            viewModel.getRepairShops()
        }

        viewModel.getRepairShops()
    }

    private fun observeViewModel() {
        viewModel.repairShopsResult.observe(viewLifecycleOwner) {
            binding.llErrorView.isVisible = false
            repairShopAdapter.setItems(it)
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            binding.llErrorView.isVisible = true
            binding.tvErrorDescription.text = message
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvRepairShop) {
            layoutManager = LinearLayoutManager(context)
            adapter = repairShopAdapter.apply {
                setOnItemClickListener { item ->
                    startActivity(RepairShopDetailActivity.createIntent(requireContext(), item?.id ?: 0))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RepairShopFragment()
    }
}