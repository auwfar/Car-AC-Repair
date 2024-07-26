package com.caracrepair.app.presentation.chooserepairshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.ActivityChooseRepairShopBinding
import com.caracrepair.app.presentation.chooserepairshop.adapter.RepairShopOptionAdapter
import com.caracrepair.app.presentation.chooserepairshop.viewmodel.ChooseRepairShopViewModel
import com.caracrepair.app.presentation.chooserepairshop.viewparam.RepairShopOptionItem
import com.caracrepair.app.utils.GMapsUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseRepairShopActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REPAIR_SHOP_OPTION_ITEM = "extra_repair_shop_option_item"
    }

    private lateinit var binding: ActivityChooseRepairShopBinding
    private val viewModel by viewModels<ChooseRepairShopViewModel>()
    private val repairShopOptionAdapter by lazy { RepairShopOptionAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRepairShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnReload.setOnClickListener {
                viewModel.getRepairShops()
            }
            btnChoose.setOnClickListener {
                val intent = Intent().apply {
                    putExtra(EXTRA_REPAIR_SHOP_OPTION_ITEM, repairShopOptionAdapter.getSelectedItem())
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        viewModel.getRepairShops()
    }

    private fun observeViewModel() {
        viewModel.repairShopsResult.observe(this) {
            binding.llErrorView.isVisible = false
            binding.flChoose.isVisible = true
            repairShopOptionAdapter.setItems(it)
            repairShopOptionAdapter.setOnCheckLocationClickListener {
                GMapsUtil.pinLocationMap(
                    this@ChooseRepairShopActivity,
                    it?.location?.lat ?: 0.0,
                    it?.location?.long ?: 0.0
                )
            }
        }
        viewModel.loadingState.observe(this) { isLoading ->
            binding.flLoading.isVisible = isLoading
        }
        viewModel.errorMessage.observe(this) { message ->
            binding.flChoose.isVisible = false
            binding.llErrorView.isVisible = true
            binding.tvErrorDescription.text = message
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvRepairShop) {
            layoutManager = LinearLayoutManager(this@ChooseRepairShopActivity)
            adapter = repairShopOptionAdapter
        }
    }
}

class ChooseRepairShopActivityContract : ActivityResultContract<Unit?, RepairShopOptionItem?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, ChooseRepairShopActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): RepairShopOptionItem? {
        return intent?.getParcelableExtra(ChooseRepairShopActivity.EXTRA_REPAIR_SHOP_OPTION_ITEM)
    }
}