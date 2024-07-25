package com.caracrepair.app.presentation.mycar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.ActivityMyCarBinding
import com.caracrepair.app.presentation.mycar.adapter.MyCarAdapter
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.presentation.mycarform.MyCarFormActivity

class MyCarActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MY_CAR_ITEM = "extra_my_car_item"
        fun createIntent(context: Context): Intent {
            return Intent(context, MyCarActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyCarBinding
    private val myCarAdapter by lazy { MyCarAdapter() }
    private val emptyCar = listOf<MyCarItem>()
    private val car = listOf(
        MyCarItem(
            id = 1,
            carName = "Toyota Avanza",
            carLicenseNumber = "B 1234 ABC"
        ),
        MyCarItem(
            id = 2,
            carName = "Honda Jazz",
            carLicenseNumber = "B 5678 DEF"
        ),
        MyCarItem(
            id = 3,
            carName = "Suzuki Ertiga",
            carLicenseNumber = "B 9101 GHI"
        ),
        MyCarItem(
            id = 4,
            carName = "Daihatsu Xenia",
            carLicenseNumber = "B 1121 JKL"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnAddCar.setOnClickListener {
                startActivity(MyCarFormActivity.createIntent(this@MyCarActivity))
            }
            btnEmptyAddCar.setOnClickListener {
                startActivity(MyCarFormActivity.createIntent(this@MyCarActivity))
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.rvMyCar) {
            layoutManager = LinearLayoutManager(this@MyCarActivity)
            adapter = myCarAdapter.apply {
                showEmptyView(car.isEmpty())
                setItems(car)
                if (callingActivity != null) {
                    setOnClickItemListener { myCarItem ->
                        setResult(RESULT_OK, Intent().apply {
                            putExtra(EXTRA_MY_CAR_ITEM, myCarItem)
                        })
                        finish()
                    }
                }
            }
        }
    }

    private fun showEmptyView(isShow: Boolean) {
        binding.llEmptyView.isVisible = isShow
        binding.flAddCar.isVisible = !isShow
    }
}

class MyCarActivityContract : ActivityResultContract<Unit?, MyCarItem?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, MyCarActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): MyCarItem? {
        return intent?.getParcelableExtra(MyCarActivity.EXTRA_MY_CAR_ITEM)
    }
}