package com.caracrepair.app.presentation.main.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.databinding.FragmentHistoryBinding
import com.caracrepair.app.presentation.main.history.adapter.HistoryAdapter
import com.caracrepair.app.presentation.main.history.viewmodel.HistoryViewModel
import com.caracrepair.app.presentation.servicedetail.ServiceDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModels<HistoryViewModel>()

    private val historyAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnReload.setOnClickListener {
            viewModel.getServiceHistory()
        }

        observeViewModel()
        setupRecyclerView()
        viewModel.getServiceHistory()
    }

    private fun observeViewModel() {
        viewModel.serviceHistoryResult.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.btnReload.isVisible = false
                binding.llErrorView.isVisible = true
                binding.tvErrorTitle.text = getString(R.string.title_no_booking_yet)
                binding.tvErrorDescription.text = getString(R.string.desc_no_booking_yet)
            } else {
                binding.llErrorView.isVisible = false
            }
            historyAdapter.setItems(it)
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            binding.btnReload.isVisible = true
            binding.llErrorView.isVisible = true
            binding.tvErrorTitle.text = getString(R.string.title_oops_there_is_problem)
            binding.tvErrorDescription.text = message
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter.apply {
                setOnItemClickListener {
                    startActivity(ServiceDetailActivity.createIntent(requireContext(), it?.id.orEmpty()))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}