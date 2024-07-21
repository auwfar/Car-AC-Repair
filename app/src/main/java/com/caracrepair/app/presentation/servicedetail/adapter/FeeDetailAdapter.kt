package com.caracrepair.app.presentation.servicedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemFeeDetailBinding
import com.caracrepair.app.presentation.servicedetail.viewparam.FeeDetailItem

class FeeDetailAdapter : RecyclerView.Adapter<FeeDetailAdapter.ViewHolder>() {
    private var items = listOf<FeeDetailItem>()

    fun setItems(items: List<FeeDetailItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFeeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemFeeDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeeDetailItem?) {
            with(binding) {
                tvFeeName.text = item?.feeName
                tvFee.text = item?.feeTotal
            }
        }
    }
}