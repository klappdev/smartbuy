package org.kl.smartbuy.event.purchase

import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.MainActivity
import org.kl.smartbuy.view.fragment.PurchaseFragment

class SortPurchaseListener(private val activity: MainActivity) {

    operator fun invoke(): Boolean {
        val purchaseFragment: PurchaseFragment? = activity.purchaseFragment
        val sortedPurchases: List<Purchase>? = purchaseFragment?.purchasesViewModel?.sortPurchases()

        with(purchaseFragment?.purchaseAdapter) {
            this?.submitList(sortedPurchases)
            this?.notifyDataSetChanged()
        }

        return true
    }
}