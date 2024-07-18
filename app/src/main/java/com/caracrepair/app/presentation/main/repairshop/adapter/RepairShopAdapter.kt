package com.caracrepair.app.presentation.main.repairshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ItemRepairShopBinding
import com.caracrepair.app.presentation.main.repairshop.viewparam.RepairShopItem

class RepairShopAdapter : RecyclerView.Adapter<RepairShopAdapter.ViewHolder>() {

    private var items = listOf<RepairShopItem>()
    private var onItemClickListener: ((RepairShopItem?) -> Unit)? = null

    fun setItems(items: List<RepairShopItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    fun setOnItemClickListener(listener: (RepairShopItem?) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRepairShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemRepairShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepairShopItem?) {
            with(binding) {
                Glide.with(root)
                    .load(item?.image)
                    .placeholder(R.drawable.img_placeholder)
                    .into(ivRepairShop)

                tvName.text = item?.name
                tvAddress.text = item?.address
                tvPhone.text = item?.phone

                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }
}