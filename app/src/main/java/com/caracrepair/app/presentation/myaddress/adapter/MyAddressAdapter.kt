package com.caracrepair.app.presentation.myaddress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemMyAddressBinding
import com.caracrepair.app.presentation.myaddress.viewparam.MyAddressItem

class MyAddressAdapter : RecyclerView.Adapter<MyAddressAdapter.ViewHolder>() {
    private var items = listOf<MyAddressItem>()
    private var onClickItemListener: ((MyAddressItem?) -> Unit)? = null
    private var onClickChangeDataListener: ((MyAddressItem?) -> Unit)? = null

    fun setItems(items: List<MyAddressItem>) {
        this.items = items
        notifyItemRangeInserted(0, items.size)
    }

    fun setOnClickItemListener(listener: (MyAddressItem?) -> Unit) {
        this.onClickItemListener = listener
    }

    fun setOnClickChangeDataListener(listener: (MyAddressItem?) -> Unit) {
        onClickChangeDataListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMyAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemMyAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyAddressItem?) {
            with(binding) {
                tvAddressLabel.text = item?.addressLabel
                tvAddress.text = item?.address

                btnChangeData.setOnClickListener {
                    onClickChangeDataListener?.invoke(item)
                }
                root.setOnClickListener {
                    onClickItemListener?.invoke(item)
                }
            }
        }
    }
}