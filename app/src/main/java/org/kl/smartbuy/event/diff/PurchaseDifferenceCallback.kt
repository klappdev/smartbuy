package org.kl.smartbuy.event.diff

import androidx.recyclerview.widget.DiffUtil
import org.kl.smartbuy.model.Purchase

class PurchaseDifferenceCallback : DiffUtil.ItemCallback<Purchase>() {

    override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
        return oldItem == newItem
    }
}