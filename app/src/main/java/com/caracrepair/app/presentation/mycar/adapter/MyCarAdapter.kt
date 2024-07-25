package com.caracrepair.app.presentation.mycar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemMyCarBinding
import com.caracrepair.app.presentation.mycar.viewparam.MyCarItem

class MyCarAdapter : RecyclerView.Adapter<MyCarAdapter.ViewHolder>() {
    private var items = listOf<MyCarItem>()
    private var onClickItemListener: ((MyCarItem?) -> Unit)? = null

    fun setItems(items: List<MyCarItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    fun setOnClickItemListener(listener: (MyCarItem?) -> Unit) {
        onClickItemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMyCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemMyCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyCarItem?) {
            with(binding) {
                tvCarName.text = item?.carName
                tvCarLicenseNumber.text = item?.carLicenseNumber

                root.setOnClickListener {
                    onClickItemListener?.invoke(item)
                }
            }
        }
    }
}