package org.kl.smartbuy.view.holder

import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.databinding.CategoryItemBinding
import org.kl.smartbuy.model.Category

class CategoryViewHolder(
    private val binding: CategoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Category) {
        with(binding) {
            category = item
            executePendingBindings()
        }
    }
}