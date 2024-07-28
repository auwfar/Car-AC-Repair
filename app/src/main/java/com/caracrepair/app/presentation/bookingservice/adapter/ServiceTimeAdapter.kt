package com.caracrepair.app.presentation.bookingservice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.R
import com.caracrepair.app.databinding.ItemServiceTimeBinding
import com.caracrepair.app.presentation.bookingservice.viewparam.ServiceTimeItem

class ServiceTimeAdapter : RecyclerView.Adapter<ServiceTimeAdapter.ViewHolder>() {
    private var items = listOf<ServiceTimeItem>()
    private var selectedPosition = -1

    fun setItems(items: List<ServiceTimeItem>) {
        this.items = items
        selectedPosition = -1
        notifyItemRangeChanged(0, items.size)
    }

    fun getSelectedServiceTime() = items.getOrNull(selectedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemServiceTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemServiceTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ServiceTimeItem?) {
            with(binding.btnOption) {
                when {
                    item?.isEnabled == false -> {
                        backgroundTintList = context.getColorStateList(R.color.boulder)
                        setStrokeColorResource(R.color.gallery)
                        setTextColor(ContextCompat.getColor(context, R.color.gallery))
                    }
                    isSelected(adapterPosition) -> {
                        setStrokeColorResource(R.color.mine_shaft)
                        setTextColor(ContextCompat.getColor(context, R.color.mine_shaft))
                    }
                    else -> {
                        setStrokeColorResource(R.color.gallery)
                        setTextColor(ContextCompat.getColor(context, R.color.gallery))
                    }
                }

                text = item?.time
                isEnabled = item?.isEnabled ?: false
                setOnClickListener {
                    setItemSelected(adapterPosition)
                }
            }
        }

        private fun isSelected(position: Int) = selectedPosition == position

        private fun setItemSelected(position: Int) {
            notifyItemChanged(selectedPosition)
            selectedPosition = position
            notifyItemChanged(position)
        }
    }
}