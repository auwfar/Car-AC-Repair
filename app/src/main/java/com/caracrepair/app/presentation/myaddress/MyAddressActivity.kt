package com.caracrepair.app.presentation.myaddress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.ActivityMyAddressBinding
import com.caracrepair.app.presentation.myaddress.adapter.MyAddressAdapter
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem
import com.caracrepair.app.presentation.myaddressform.MyAddressFormActivity

class MyAddressActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MY_ADDRESS_ITEM = "extra_my_address_item"
        fun createIntent(context: Context): Intent {
            return Intent(context, MyAddressActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyAddressBinding
    private val myAddressAdapter by lazy { MyAddressAdapter() }
    private val emptyAddress = listOf<MyAddressItem>()
    private val address = listOf(
        MyAddressItem(
            id = 1,
            addressLabel = "Rumah",
            address = "Jl. Raya Bogor No. 1, Jakarta"
        ),
        MyAddressItem(
            id = 2,
            addressLabel = "Kantor",
            address = "Jl. Raya Bogor No. 2, Jakarta"
        ),
        MyAddressItem(
            id = 3,
            addressLabel = "Toko",
            address = "Jl. Raya Bogor No. 3, Jakarta"
        ),
        MyAddressItem(
            id = 4,
            addressLabel = "Apartemen",
            address = "Jl. Raya Bogor No. 4, Jakarta"
        ),
        MyAddressItem(
            id = 5,
            addressLabel = "Gudang",
            address = "Jl. Raya Bogor No. 5, Jakarta"
        ),
        MyAddressItem(
            id = 6,
            addressLabel = "Pabrik",
            address = "Jl. Raya Bogor No. 6, Jakarta"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnAddAddress.setOnClickListener {
                startActivity(MyAddressFormActivity.createIntent(this@MyAddressActivity))
            }
            btnEmptyAddData.setOnClickListener {
                startActivity(MyAddressFormActivity.createIntent(this@MyAddressActivity))
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.rvMyAddress) {
            layoutManager = LinearLayoutManager(this@MyAddressActivity)
            adapter = myAddressAdapter.apply {
                showEmptyView(address.isEmpty())
                setItems(address)
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

    private fun showEmptyView(isShow: Boolean) {
        binding.llEmptyView.isVisible = isShow
        binding.flAddAddress.isVisible = !isShow
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