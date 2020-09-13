package org.kl.smartbuy.view.holder

import androidx.recyclerview.widget.RecyclerView
import org.kl.smartbuy.databinding.PurchaseItemBinding
import org.kl.smartbuy.model.Purchase

class PurchaseViewHolder(
    private val binding: PurchaseItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Purchase) {
        with(binding) {
            purchase = item
            executePendingBindings()
        }
    }
}