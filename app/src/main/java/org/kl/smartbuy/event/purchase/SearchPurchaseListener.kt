package org.kl.smartbuy.event.purchase

import androidx.appcompat.widget.SearchView
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.MainActivity
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.viewmodel.PurchaseViewModel

class SearchPurchaseListener(
    private val activity: MainActivity
): SearchView.OnQueryTextListener {

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
            val purchaseFragment: PurchaseFragment? = activity.purchaseFragment
            val purchaseViewModel: PurchaseViewModel? = purchaseFragment?.purchaseViewModel
            val searchedPurchases: List<Purchase>? = purchaseViewModel?.searchPurchases(newText)

            with(purchaseFragment?.purchaseAdapter) {
                this?.submitList(searchedPurchases)
                this?.notifyDataSetChanged()
            }
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?) = true
}