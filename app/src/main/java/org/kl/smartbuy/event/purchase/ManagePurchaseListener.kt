package org.kl.smartbuy.event.purchase

import android.view.View
import org.kl.smartbuy.view.holder.PurchaseViewHolder

class ManagePurchaseListener(
    private val purchaseViewHolder: PurchaseViewHolder
) : View.OnLongClickListener {

    override fun onLongClick(view: View?): Boolean {
        PurchaseViewHolder.currentPosition = purchaseViewHolder.absoluteAdapterPosition

        purchaseViewHolder.bindingAdapter?.notifyDataSetChanged()
        return true
    }
}