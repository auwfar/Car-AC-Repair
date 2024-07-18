package com.caracrepair.app.presentation.main.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ItemHistoryBinding
import com.caracrepair.app.presentation.main.history.viewparam.HistoryItem

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var items = listOf<HistoryItem>()

    fun setItems(items: List<HistoryItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItem?) {
            with(binding) {
                val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(16))
                val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
                Glide.with(root)
                    .load(item?.carImage)
                    .apply(requestOptions)
                    .thumbnail(requestBuilder)
                    .into(ivCar)

                tvOrderDate.text = item?.orderDate
                tvCarName.text = item?.carName
                tvServiceDate.text = item?.serviceDate
                tvStatus.text = item?.status
            }
        }
    }
}