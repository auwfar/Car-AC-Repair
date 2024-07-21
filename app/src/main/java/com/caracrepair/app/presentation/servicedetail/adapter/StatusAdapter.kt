package com.caracrepair.app.presentation.servicedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ItemStatusBinding
import com.caracrepair.app.presentation.servicedetail.viewparam.StatusItem

class StatusAdapter : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    private var items = listOf<StatusItem>()

    fun setItems(items: List<StatusItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)

        with(holder.binding) {
            if (position == 0) {
                ivDot.setColorFilter(ContextCompat.getColor(root.context, R.color.alizarin_crimson))
                viewLine.setBackgroundResource(R.color.alizarin_crimson)
            } else {
                ivDot.setColorFilter(ContextCompat.getColor(root.context, R.color.nobel))
                viewLine.setBackgroundResource(R.color.nobel)
            }
        }
    }

    class ViewHolder(val binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        private val feeDetailAdapter by lazy { FeeDetailAdapter() }
        fun bind(item: StatusItem?) {
            with(binding) {
                tvStatusTitle.text = item?.statusTitle
                tvStatusDescription.text = item?.statusDescription
                tvStatusTime.text = item?.statusTime
                if (item?.fee != null) {
                    groupFee.isVisible = true
                    tvFeeTotal.text = item.fee.feeTotal
                    rvFeeDetail.layoutManager = LinearLayoutManager(itemView.context)
                    rvFeeDetail.adapter = feeDetailAdapter.apply {
                        setItems(item.fee.fees)
                    }
                } else {
                    groupFee.isVisible = false
                }
            }
        }
    }
}