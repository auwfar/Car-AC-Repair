package com.caracrepair.app.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ItemRepairShopSliderBinding
import com.caracrepair.app.presentation.main.home.viewparam.RepairShopSliderItem

class RepairShopSliderAdapter : RecyclerView.Adapter<RepairShopSliderAdapter.ViewHolder>() {

    private var items = listOf<RepairShopSliderItem>()

    fun setItems(items: List<RepairShopSliderItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRepairShopSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemRepairShopSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepairShopSliderItem?) {
            with(binding) {
                val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(16))
                val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
                Glide.with(root)
                    .load(item?.image)
                    .apply(requestOptions)
                    .thumbnail(requestBuilder)
                    .into(ivRepairShop)
                tvDescription.text = item?.description
            }
        }
    }
}