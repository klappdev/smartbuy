package org.kl.smartbuy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import org.kl.smartbuy.model.Category
import org.kl.smartbuy.view.holder.CategoryViewHolder
import org.kl.smartbuy.event.diff.CategoryDifferenceCallback
import org.kl.smartbuy.databinding.CategoryItemBinding

class CategoryAdapter : ListAdapter<Category, CategoryViewHolder>(CategoryDifferenceCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}