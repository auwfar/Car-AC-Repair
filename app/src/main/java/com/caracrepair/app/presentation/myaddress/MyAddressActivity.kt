package com.caracrepair.app.presentation.myaddress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityMyAddressBinding
import com.caracrepair.app.presentation.myaddress.adapter.MyAddressAdapter
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem
import com.caracrepair.app.presentation.myaddresses.viewmodel.MyAddressViewModel
import com.caracrepair.app.presentation.myaddressform.MyAddressFormActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAddressActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MY_ADDRESS_ITEM = "extra_my_address_item"
        fun createIntent(context: Context): Intent {
            return Intent(context, MyAddressActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyAddressBinding
    private val viewModel by viewModels<MyAddressViewModel>()
    private val myAddressAdapter by lazy { MyAddressAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnAddAddress.setOnClickListener {
                startActivity(MyAddressFormActivity.createIntent(this@MyAddressActivity))
            }
        }
        viewModel.getAddresses()
    }

    private fun observeViewModel() {
        viewModel.addressesResult.observe(this) { address ->
            if (address.isEmpty()) {
                binding.llErrorView.isVisible = true
                binding.tvErrorTitle.text = getString(R.string.title_no_address_yet)
                binding.tvErrorDescription.text = getString(R.string.desc_no_address)
                binding.btnErrorAction.text = getString(R.string.title_add_address)
                binding.btnErrorAction.setOnClickListener {
                    startActivity(MyAddressFormActivity.createIntent(this@MyAddressActivity))
                }
            } else {
                binding.llErrorView.isVisible = false
            }
            myAddressAdapter.setItems(address)
        }
        viewModel.loadingState.observe(this) {
            binding.flLoading.isVisible = it
        }
        viewModel.errorMessage.observe(this) { message ->
            binding.llErrorView.isVisible = true
            binding.tvErrorTitle.text = getString(R.string.title_oops_there_is_problem)
            binding.tvErrorDescription.text = message
            binding.btnErrorAction.text = getString(R.string.title_reload)
            binding.btnErrorAction.setOnClickListener {
                viewModel.getAddresses()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvMyAddress) {
            layoutManager = LinearLayoutManager(this@MyAddressActivity)
            adapter = myAddressAdapter.apply {
                setOnClickChangeDataListener {
                    startActivity(MyAddressFormActivity.createIntent(this@MyAddressActivity))
                }
                if (callingActivity != null) {
                    setOnClickItemListener { myAddressItem ->
                        setResult(RESULT_OK, Intent().apply {
                            putExtra(EXTRA_MY_ADDRESS_ITEM, myAddressItem)
                        })
                        finish()
                    }
                }
            }
        }
    }
}

class MyAddressActivityContract : ActivityResultContract<Unit?, MyAddressItem?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, MyAddressActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): MyAddressItem? {
        return intent?.getParcelableExtra(MyAddressActivity.EXTRA_MY_ADDRESS_ITEM)
    }
}