package com.caracrepair.app.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemLastServiceBinding
import com.caracrepair.app.presentation.main.home.viewparam.LastServiceItem

class LastServiceAdapter: RecyclerView.Adapter<LastServiceAdapter.ViewHolder>() {

    private var items = listOf<LastServiceItem>()

    fun setItems(items: List<LastServiceItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemLastServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemLastServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LastServiceItem?) {
            with(binding) {
                tvCar.text = item?.carName
                tvServiceDate.text = item?.serviceDate
                tvDistance.text = item?.distance
            }
        }
    }
}