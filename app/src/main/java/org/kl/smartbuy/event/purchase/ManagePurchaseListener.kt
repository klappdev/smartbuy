package org.kl.smartbuy.event.purchase

import android.view.View
import org.kl.smartbuy.view.holder.PurchaseViewHolder

class ManagePurchaseListener(
    private val purchaseViewHolder: PurchaseViewHolder,
    var notifyAction: ((Boolean) -> Unit)?
) : View.OnLongClickListener {

    override fun onLongClick(view: View?): Boolean {
        PurchaseViewHolder.currentPosition = purchaseViewHolder.absoluteAdapterPosition

        purchaseViewHolder.bindingAdapter?.notifyDataSetChanged()

        notifyAction?.invoke(true)
        return true
    }
}