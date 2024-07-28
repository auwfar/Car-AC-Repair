package com.caracrepair.app.presentation.chooserepairshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ItemRepairShopOptionBinding
import com.caracrepair.app.presentation.chooserepairshop.viewparam.RepairShopOptionItem

class RepairShopOptionAdapter : RecyclerView.Adapter<RepairShopOptionAdapter.ViewHolder>() {
    private var items = listOf<RepairShopOptionItem>()
    private var selectedPosition = RecyclerView.NO_POSITION
    private var onCheckLocationClickListener: ((RepairShopOptionItem?) -> Unit)? = null

    fun setItems(items: List<RepairShopOptionItem>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    fun setOnCheckLocationClickListener(listener: (RepairShopOptionItem?) -> Unit) {
        onCheckLocationClickListener = listener
    }

    fun getSelectedItem() = items.getOrNull(selectedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRepairShopOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemRepairShopOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepairShopOptionItem?) {
            with(binding) {
                clContainer.setBackgroundResource(if (isSelected(adapterPosition)) {
                    R.color.gallery
                } else {
                    R.color.white
                })
                tvRepairShopName.text = item?.name
                tvRepairShopAddress.text = item?.address
                btnCheckRepairShopLocation.setOnClickListener {
                    onCheckLocationClickListener?.invoke(item)
                }
                root.setOnClickListener {
                    setSelectedItem(adapterPosition)
                }
            }
        }

        private fun isSelected(position: Int) = selectedPosition == position

        private fun setSelectedItem(position: Int) {
            notifyItemChanged(selectedPosition)
            selectedPosition = position
            notifyItemChanged(selectedPosition)
        }
    }
}