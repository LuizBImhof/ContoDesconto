package com.unidavi.tc.conto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unidavi.tc.conto.database.Discount
import com.unidavi.tc.conto.database.Product
import com.unidavi.tc.conto.databinding.ListItemDiscountBinding
import com.unidavi.tc.conto.databinding.ListItemProductBinding

class ProductAdapter() : ListAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (position > 0 && getItem(position - 1).categoryString == item.categoryString) {
            holder.binding.textMenuCategory.visibility = View.GONE
        } else {
            holder.binding.textMenuCategory.visibility = View.VISIBLE
        }
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}