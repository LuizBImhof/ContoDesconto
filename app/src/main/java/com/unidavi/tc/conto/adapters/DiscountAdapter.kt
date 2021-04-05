package com.unidavi.tc.conto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unidavi.tc.conto.database.Discount
import com.unidavi.tc.conto.databinding.ListItemDiscountBinding

class DiscountAdapter(val clickListener: DiscountListener): ListAdapter<Discount, DiscountAdapter.ViewHolder>(DiscountDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemDiscountBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Discount, clickListener: DiscountListener) {
            binding.discount = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemDiscountBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DiscountDiffCallback : DiffUtil.ItemCallback<Discount>() {
    override fun areItemsTheSame(oldItem: Discount, newItem: Discount): Boolean {
        return oldItem.discountId == newItem.discountId
    }

    override fun areContentsTheSame(oldItem: Discount, newItem: Discount): Boolean {
        return oldItem == newItem
    }
}

class DiscountListener(val clickListener: (discount: Discount) -> Unit) {
    fun onClick (discount:Discount) = clickListener(discount)
}