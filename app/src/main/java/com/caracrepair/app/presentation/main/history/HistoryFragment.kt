package com.caracrepair.app.presentation.main.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.databinding.FragmentHistoryBinding
import com.caracrepair.app.presentation.main.history.adapter.HistoryAdapter
import com.caracrepair.app.presentation.main.history.viewparam.HistoryItem
import com.caracrepair.app.presentation.servicedetail.ServiceDetailActivity

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private val historyAdapter by lazy { HistoryAdapter() }
    private val history = listOf(
        HistoryItem(
            id = 1,
            carImage = "https://via.placeholder.com/150",
            carName = "Toyota Avanza",
            orderDate = "18 Juli 2024 16:00",
            serviceDate = "18 Juli 2024 17:00",
            status = "Sedang Dikerjakan"
        ),
        HistoryItem(
            id = 2,
            carImage = "https://via.placeholder.com/150",
            carName = "Honda Jazz",
            orderDate = "18 Juli 2024 16:00",
            serviceDate = "18 Juli 2024 17:00",
            status = "Pengecekan"
        ),
        HistoryItem(
            id = 3,
            carImage = "https://via.placeholder.com/150",
            carName = "Toyota Avanza",
            orderDate = "18 Juli 2024 16:00",
            serviceDate = "18 Juli 2024 17:00",
            status = "Menunggu Antrian"
        ),
        HistoryItem(
            id = 4,
            carImage = "https://via.placeholder.com/150",
            carName = "Honda Jazz",
            orderDate = "18 Juli 2024 16:00",
            serviceDate = "18 Juli 2024 17:00",
            status = "Jadwal Diubah, Menunggu Antrian"
        ),
        HistoryItem(
            id = 5,
            carImage = "https://via.placeholder.com/150",
            carName = "Toyota Avanza",
            orderDate = "18 Juli 2024 16:00",
            serviceDate = "18 Juli 2024 17:00",
            status = "Selesai"
        ),
        HistoryItem(
            id = 6,
            carImage = "https://via.placeholder.com/150",
            carName = "Honda Jazz",
            orderDate = "18 Juli 2024 16:00",
            serviceDate = "18 Juli 2024 17:00",
            status = "Selesai"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter.apply {
                setItems(history)
                setOnItemClickListener {
                    startActivity(ServiceDetailActivity.createIntent(requireContext()))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}