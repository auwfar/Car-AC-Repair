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
    private var onItemClickListener: ((HistoryItem?) -> Unit)? = null

    fun setItems(items: List<HistoryItem>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    fun setOnItemClickListener(listener: (HistoryItem?) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItem?) {
            with(binding) {
                val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(16))
                val requestBuilder = Glide.with(root).load(R.drawable.img_placeholder).apply(requestOptions)
                Glide.with(root)
                    .load(item?.repairShopImage)
                    .apply(requestOptions)
                    .thumbnail(requestBuilder)
                    .into(ivCar)

                tvOrderDate.text = item?.getOrderDate()
                tvCarName.text = item?.carName
                tvServiceDate.text = item?.getServiceDate()
                tvStatus.text = item?.status

                root.setOnClickListener {
                    item?.let { onItemClickListener?.invoke(it) }
                }
            }
        }
    }
}