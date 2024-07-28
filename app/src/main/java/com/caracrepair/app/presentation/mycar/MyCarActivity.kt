package com.caracrepair.app.presentation.mycar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ActivityMyCarBinding
import com.caracrepair.app.models.viewparam.ButtonParam
import com.caracrepair.app.models.viewparam.ConfirmationDialogParam
import com.caracrepair.app.presentation.mycar.adapter.MyCarAdapter
import com.caracrepair.app.presentation.mycar.viewmodel.MyCarViewModel
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem
import com.caracrepair.app.presentation.mycarform.MyCarFormActivityContract
import com.caracrepair.app.utils.dialog.ConfirmationDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCarActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MY_CAR_ITEM = "extra_my_car_item"
        fun createIntent(context: Context): Intent {
            return Intent(context, MyCarActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMyCarBinding
    private val viewModel by viewModels<MyCarViewModel>()
    private val myCarAdapter by lazy { MyCarAdapter() }

    private val myCarFormLauncher = registerForActivityResult(MyCarFormActivityContract()) { isUpdated ->
        if (isUpdated) viewModel.getCars()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            btnAddCar.setOnClickListener {
                myCarFormLauncher.launch(null)
            }
        }

        viewModel.getCars()
    }

    private fun observeViewModel() {
        viewModel.carsResult.observe(this) { car ->
            if (car.isEmpty()) {
                binding.llErrorView.isVisible = true
                binding.tvErrorTitle.text = getString(R.string.title_no_car_yet)
                binding.tvErrorDescription.text = getString(R.string.desc_no_car)
                binding.btnErrorAction.text = getString(R.string.title_add_car)
                binding.btnErrorAction.setOnClickListener {
                    myCarFormLauncher.launch(null)
                }
            } else {
                binding.llErrorView.isVisible = false
            }
            myCarAdapter.setItems(car)
        }
        viewModel.removeCarResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            viewModel.getCars()
        }
        viewModel.loadingState.observe(this) {
            binding.flLoading.isVisible = it
        }
        viewModel.errorPageMessage.observe(this) { message ->
            binding.llErrorView.isVisible = true
            binding.tvErrorTitle.text = getString(R.string.title_oops_there_is_problem)
            binding.tvErrorDescription.text = message
            binding.btnErrorAction.text = getString(R.string.title_reload)
            binding.btnErrorAction.setOnClickListener {
                viewModel.getCars()
            }
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvMyCar) {
            layoutManager = LinearLayoutManager(this@MyCarActivity)
            adapter = myCarAdapter.apply {
                setOnClickRemoveDataListener {
                    showRemoveCarDialog(it)
                }
                setOnClickChangeDataListener {
                    myCarFormLauncher.launch(it)
                }
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

    private fun showRemoveCarDialog(carId: String) {
        val dialog = ConfirmationDialog.newInstance(ConfirmationDialogParam(
            title = getString(R.string.title_remove_data),
            message = getString(R.string.desc_remove_data),
            positiveButton = ButtonParam(
                text = getString(R.string.title_dont_remove),
                action = {}
            ),
            negativeButton = ButtonParam(
                text = getString(R.string.title_yes_remove_now),
                action = {
                    viewModel.removeCar(carId)
                }
            )
        ))
        dialog.show(supportFragmentManager, null)
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