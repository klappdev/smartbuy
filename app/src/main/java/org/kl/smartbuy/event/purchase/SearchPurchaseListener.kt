/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2021 https://github.com/klappdev
 *
 * Permission is hereby  granted, free of charge, to any  person obtaining a copy
 * of this software and associated  documentation files (the "Software"), to deal
 * in the Software  without restriction, including without  limitation the rights
 * to  use, copy,  modify, merge,  publish, distribute,  sublicense, and/or  sell
 * copies  of  the Software,  and  to  permit persons  to  whom  the Software  is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE  IS PROVIDED "AS  IS", WITHOUT WARRANTY  OF ANY KIND,  EXPRESS OR
 * IMPLIED,  INCLUDING BUT  NOT  LIMITED TO  THE  WARRANTIES OF  MERCHANTABILITY,
 * FITNESS FOR  A PARTICULAR PURPOSE AND  NONINFRINGEMENT. IN NO EVENT  SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS  BE  LIABLE FOR  ANY  CLAIM,  DAMAGES OR  OTHER
 * LIABILITY, WHETHER IN AN ACTION OF  CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE  OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kl.smartbuy.event.purchase

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView

import org.kl.smartbuy.R
import org.kl.smartbuy.view.purchase.PurchaseFragment

class SearchPurchaseListener(
    private val purchaseFragment: PurchaseFragment
) : View.OnClickListener, MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {
    private val purchaseAdapter = purchaseFragment.purchaseAdapter
    private val purchasesViewModel = purchaseFragment.purchasesViewModel

    private var searchView: SearchView? = null
    private var searchInput: TextView? = null
    private var closeIcon: ImageView? = null
    private var currentSize: Int = -1

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
            purchasesViewModel.searchPurchases(newText) { data ->
                purchaseAdapter.submitData(data)
                purchaseAdapter.notifyDataSetChanged()

                currentSize = purchaseAdapter.itemCount
            }
        } else {
            refreshPurchases()
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onMenuItemActionExpand(view: MenuItem?): Boolean {
        if (searchView == null) {
            searchView = view?.actionView as SearchView
            searchView?.queryHint = purchaseFragment.getString(R.string.search_hint)
            searchView?.setOnQueryTextListener(this)

            this.searchInput = searchView?.findViewById(androidx.appcompat.R.id.search_src_text)
            this.closeIcon = searchView?.findViewById(androidx.appcompat.R.id.search_close_btn)
            closeIcon?.setOnClickListener(this)
        }

        return true
    }

    override fun onMenuItemActionCollapse(view: MenuItem?): Boolean {
        purchaseAdapter.position = -1
        purchaseFragment.notifyMenuItemSelected(false)
        refreshPurchases()

        return true
    }

    override fun onClick(view: View?) {
        refreshPurchases()
        searchInput?.text = ""
    }

    private fun refreshPurchases() {
        purchasesViewModel.getPurchases { data ->
            if (currentSize != -1) {
                purchaseAdapter.submitData(data)
                purchaseAdapter.notifyDataSetChanged()

                currentSize = -1
            }

        }
    }
}