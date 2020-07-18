package org.kl.smartbuy.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.R
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.holder.PurchaseViewHolder

class PurchaseAdapter : RecyclerView.Adapter<PurchaseViewHolder> {
    private var context: Context
    private var listPurchases: List<Purchase>

    constructor(context: Context, list: List<Purchase>) {
        this.context = context
        this.listPurchases = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.purchase_item, parent, false)
        return PurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.nameTextView?.text = listPurchases[position].name
        holder.dateTextView?.text = listPurchases[position].date
        holder.itemImage?.setImageResource(listPurchases[position].icon)

    }

    override fun getItemCount() = listPurchases.size
}