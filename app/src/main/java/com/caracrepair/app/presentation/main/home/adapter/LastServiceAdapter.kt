package com.caracrepair.app.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemLastServiceBinding
import com.caracrepair.app.presentation.main.home.viewparam.LastServiceItem

class LastServiceAdapter: RecyclerView.Adapter<LastServiceAdapter.ViewHolder>() {
    private var items = listOf<LastServiceItem>()
    private var setOnClickItemListener: ((LastServiceItem?) -> Unit)? = null

    fun setItems(items: List<LastServiceItem>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    fun setOnClickItemListener(listener: (LastServiceItem?) -> Unit) {
        setOnClickItemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemLastServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemLastServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LastServiceItem?) {
            with(binding) {
                tvCar.text = item?.carName
                tvServiceDate.text = item?.serviceDate
                tvDistance.text = item?.distance

                root.setOnClickListener {
                    setOnClickItemListener?.invoke(item)
                }
            }
        }
    }
}