package org.kl.smartbuy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.holder.PurchaseViewHolder
import org.kl.smartbuy.databinding.PurchaseItemBinding
import org.kl.smartbuy.event.diff.PurchaseDifferenceCallback
import org.kl.smartbuy.event.purchase.ManagePurchaseListener

class PurchaseAdapter : ListAdapter<Purchase, PurchaseViewHolder>(PurchaseDifferenceCallback()) {
    var notifyAction: ((Boolean) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PurchaseItemBinding.inflate(inflater, parent, false)

        return PurchaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val purchase = getItem(position)
        holder.bind(purchase, position)

        holder.binding.root.setOnLongClickListener(ManagePurchaseListener(holder, notifyAction))
    }

    fun getCurrentItem(): Purchase = super.getItem(PurchaseViewHolder.currentPosition)
}