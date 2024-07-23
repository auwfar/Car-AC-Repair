package com.caracrepair.app.presentation.chooserepairshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.ActivityChooseRepairShopBinding
import com.caracrepair.app.presentation.chooserepairshop.adapter.RepairShopOptionAdapter
import com.caracrepair.app.presentation.chooserepairshop.viewparam.RepairShopOptionItem
import com.caracrepair.app.utils.GMapsUtil

class ChooseRepairShopActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChooseRepairShopActivity::class.java)
        }
    }

    private lateinit var binding: ActivityChooseRepairShopBinding
    private val repairShopOptionAdapter by lazy { RepairShopOptionAdapter() }
    private val repairShopOption = listOf(
        RepairShopOptionItem(
            id = 1,
            name = "Bengkel A",
            address = "Jl. A No. 1"
        ),
        RepairShopOptionItem(
            id = 2,
            name = "Bengkel B",
            address = "Jl. B No. 2"
        ),
        RepairShopOptionItem(
            id = 3,
            name = "Bengkel C",
            address = "Jl. C No. 3"
        ),
        RepairShopOptionItem(
            id = 4,
            name = "Bengkel D",
            address = "Jl. D No. 4"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRepairShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.rvRepairShop) {
            layoutManager = LinearLayoutManager(this@ChooseRepairShopActivity)
            adapter = repairShopOptionAdapter.apply {
                setItems(repairShopOption)
                setOnCheckLocationClickListener {
                    val latitude = -6.200000
                    val longitude = 106.816666
                    GMapsUtil.pinLocationMap(this@ChooseRepairShopActivity, latitude, longitude)
                }
            }
        }
    }
}