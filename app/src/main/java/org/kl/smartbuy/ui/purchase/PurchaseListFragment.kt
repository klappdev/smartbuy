/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2022 https://github.com/klappdev
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
package org.kl.smartbuy.ui.purchase

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingData
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.R
import org.kl.smartbuy.db.entity.Purchase
import org.kl.smartbuy.ui.MainActivity
import org.kl.smartbuy.viewmodel.PurchaseListViewModel
import org.kl.smartbuy.databinding.FragmentPurchaseListBinding
import org.kl.smartbuy.event.purchase.*
import org.kl.smartbuy.ui.tabs.TabPagerFragmentDirections

@AndroidEntryPoint
class PurchaseListFragment : Fragment(R.layout.fragment_purchase_list) {
    private lateinit var emptyTextView: TextView
    private lateinit var purchaseRecycleView: RecyclerView

    public lateinit var purchaseAdapter: PurchaseAdapter
    public lateinit var parentActivity: MainActivity

    public val purchasesViewModel: PurchaseListViewModel by viewModels()
    private var binding: FragmentPurchaseListBinding? = null

    public lateinit var sortPurchaseListener: SortPurchaseListener
    public lateinit var deletePurchaseListener: DeletePurchaseListener
    public lateinit var resetPurchaseListener: ResetPurchaseListener

    private var searchMenuItem: MenuItem? = null
    private var menuItemSelected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //initPurchases()
        initView(view)

        subscribeUi()
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        searchMenuItem = menu.findItem(R.id.action_search)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> resetPurchaseListener()
        R.id.action_edit -> navigateToEditPurchase()
        R.id.action_sort -> sortPurchaseListener()
        R.id.action_delete -> deletePurchaseListener()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(menuItemSelected)
        parentActivity.supportActionBar?.setDisplayShowHomeEnabled(menuItemSelected)

        menu.findItem(R.id.action_search)?.isVisible = !menuItemSelected
        menu.findItem(R.id.action_sort)?.isVisible = !menuItemSelected
        menu.findItem(R.id.action_edit)?.isVisible = menuItemSelected
        menu.findItem(R.id.action_delete)?.isVisible = menuItemSelected
    }

    private fun initView(rootView: View) {
        val innerBinding = FragmentPurchaseListBinding.bind(rootView)
        this.binding = innerBinding

        this.emptyTextView = innerBinding.purchaseEmptyTextView
        this.purchaseRecycleView = innerBinding.purchaseRecycleView

        purchaseAdapter = PurchaseAdapter()

        parentActivity = (activity as MainActivity)
        initListeners()

        purchaseAdapter.notifyAction = ::notifyMenuItemSelected
        purchaseRecycleView.adapter = purchaseAdapter
    }

    private fun subscribeUi() {
        purchasesViewModel.getPurchases { data ->
            switchVisibility(data != PagingData.empty<Purchase>())
            purchaseAdapter.submitData(data)
        }
    }

    private fun initListeners() {
        this.sortPurchaseListener = SortPurchaseListener(this)
        this.deletePurchaseListener = DeletePurchaseListener(this)
        this.resetPurchaseListener = ResetPurchaseListener(this)

        searchMenuItem?.setOnActionExpandListener(SearchPurchaseListener(this))
    }

    fun notifyMenuItemSelected(selected: Boolean): Boolean {
        this.menuItemSelected = selected
        parentActivity.invalidateOptionsMenu()

        return true
    }

    private fun switchVisibility(flag: Boolean) {
        if (flag) {
            purchaseRecycleView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        } else {
            purchaseRecycleView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        }
    }

    private fun navigateToEditPurchase(): Boolean {
        val purchaseId = purchaseAdapter.getCurrentItemId()

        if (purchaseId != -1L) {
            val direction = TabPagerFragmentDirections
                .actionTabPagerFragmentToEditPurchaseActivity(purchaseId)
            findNavController().navigate(direction)

            return true
        }

        return false
    }

    /*FIXME: method use only for stubs*/
    private fun initPurchases() {
        val listPurchases = mutableListOf<Purchase>()

        for (i in 1L..5L) {
            listPurchases += Purchase(i, "$i purchase",  "$i.$i.2021")
        }

        purchasesViewModel.addPurchases(listPurchases)
    }
}