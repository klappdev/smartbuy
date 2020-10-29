package org.kl.smartbuy.event.purchase

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView

import org.kl.smartbuy.R
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.MainActivity
import org.kl.smartbuy.viewmodel.PurchaseListViewModel
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.view.holder.PurchaseViewHolder

class SearchPurchaseListener(
    private val activity: MainActivity
) : View.OnClickListener, MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {
    private var searchView: SearchView? = null
    private var searchInput: TextView? = null
    private var closeIcon: ImageView? = null
    private var currentSize: Int = -1

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
            val purchaseFragment: PurchaseFragment? = activity.purchaseFragment
            val purchasesViewModel: PurchaseListViewModel? = purchaseFragment?.purchasesViewModel
            val searchedPurchases: List<Purchase>? = purchasesViewModel?.searchPurchases(newText)

            with(purchaseFragment?.purchaseAdapter) {
                this?.submitList(searchedPurchases)
                this?.notifyDataSetChanged()
            }

            this.currentSize = searchedPurchases?.size ?: 0
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onMenuItemActionExpand(view: MenuItem?): Boolean {
        if (searchView == null) {
            searchView = view?.actionView as SearchView
            searchView?.queryHint = activity.getString(R.string.search_hint)
            searchView?.setOnQueryTextListener(this)

            this.searchInput = searchView?.findViewById(androidx.appcompat.R.id.search_src_text)
            this.closeIcon = searchView?.findViewById(androidx.appcompat.R.id.search_close_btn)
            closeIcon?.setOnClickListener(this)
        }

        return true
    }

    override fun onMenuItemActionCollapse(view: MenuItem?): Boolean {
        PurchaseViewHolder.currentPosition = -1
        activity.notifyItemSelected(false)
        restorePurchases()

        return true
    }

    override fun onClick(view: View?) {
        restorePurchases()
        this.searchInput?.text = ""
    }

    private fun restorePurchases() {
        val purchaseFragment: PurchaseFragment? = activity.purchaseFragment
        val purchases: List<Purchase>? = purchaseFragment?.purchasesViewModel?.purchases?.value

        if (currentSize == -1 || currentSize == purchases?.size) {
            return
        }

        with(purchaseFragment?.purchaseAdapter) {
            this?.submitList(purchases)
            this?.notifyDataSetChanged()
        }

        this.currentSize = -1
    }
}