package com.caracrepair.app.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caracrepair.app.databinding.ItemOnProgressServiceBinding
import com.caracrepair.app.presentation.main.home.viewparam.OnProgressServiceItem

class OnProgressServiceAdapter: RecyclerView.Adapter<OnProgressServiceAdapter.ViewHolder>() {
    private var items = listOf<OnProgressServiceItem>()
    private var setOnClickItemListener: ((OnProgressServiceItem?) -> Unit)? = null

    fun setItems(items: List<OnProgressServiceItem>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    fun setOnClickItemListener(listener: (OnProgressServiceItem?) -> Unit) {
        setOnClickItemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOnProgressServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemOnProgressServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnProgressServiceItem?) {
            with(binding) {
                tvCar.text = item?.carName
                tvStatus.text = item?.status

                root.setOnClickListener {
                    setOnClickItemListener?.invoke(item)
                }
            }
        }
    }
}