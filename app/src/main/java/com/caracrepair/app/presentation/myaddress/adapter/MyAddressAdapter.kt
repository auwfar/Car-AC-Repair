package com.caracrepair.app.presentation.myaddress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemMyAddressBinding
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem

class MyAddressAdapter : RecyclerView.Adapter<MyAddressAdapter.ViewHolder>() {

    private var items = listOf<MyAddressItem>()

    fun setItems(items: List<MyAddressItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMyAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemMyAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyAddressItem?) {
            with(binding) {
                tvAddressLabel.text = item?.addressLabel
                tvAddress.text = item?.address
            }
        }
    }
}