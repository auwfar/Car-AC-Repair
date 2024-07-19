package com.caracrepair.app.presentation.mycar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.databinding.ActivityMyCarBinding
import com.caracrepair.app.presentation.mycar.adapter.MyCarAdapter
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem

class MyCarActivity : AppCompatActivity() {
    companion object {
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
            carName = "Rumah",
            carLicenseNumber = "Jl. Raya Bogor No. 1, Jakarta"
        ),
        MyCarItem(
            id = 2,
            carName = "Kantor",
            carLicenseNumber = "Jl. Raya Bogor No. 2, Jakarta"
        ),
        MyCarItem(
            id = 3,
            carName = "Toko",
            carLicenseNumber = "Jl. Raya Bogor No. 3, Jakarta"
        ),
        MyCarItem(
            id = 4,
            carName = "Apartemen",
            carLicenseNumber = "Jl. Raya Bogor No. 4, Jakarta"
        ),
        MyCarItem(
            id = 5,
            carName = "Gudang",
            carLicenseNumber = "Jl. Raya Bogor No. 5, Jakarta"
        ),
        MyCarItem(
            id = 6,
            carName = "Pabrik",
            carLicenseNumber = "Jl. Raya Bogor No. 6, Jakarta"
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
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.rvMyCar) {
            layoutManager = LinearLayoutManager(this@MyCarActivity)
            adapter = myCarAdapter.apply {
                showEmptyView(car.isEmpty())
                setItems(car)
            }
        }
    }

    private fun showEmptyView(isShow: Boolean) {
        binding.llEmptyView.isVisible = isShow
        binding.flAddCar.isVisible = !isShow
    }
}