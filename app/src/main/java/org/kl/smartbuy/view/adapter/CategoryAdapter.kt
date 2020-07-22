package org.kl.smartbuy.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kl.smartbuy.R
import org.kl.smartbuy.model.Category
import org.kl.smartbuy.view.holder.CategoryViewHolder

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder> {
    private var context: Context
    private var listCategories: List<Category>

    constructor(context: Context, list: List<Category>) {
        this.context = context
        this.listCategories = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryImage?.setImageResource(R.drawable.purchase_icon)
        holder.nameTextView?.text = listCategories[position].name
    }

    override fun getItemCount() = listCategories.size
}