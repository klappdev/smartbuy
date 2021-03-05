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
package org.kl.smartbuy.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingData
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.Job

import org.kl.smartbuy.R
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.activity.MainActivity
import org.kl.smartbuy.view.adapter.PurchaseAdapter
import org.kl.smartbuy.viewmodel.PurchaseListViewModel
import org.kl.smartbuy.databinding.FragmentPurchaseBinding

@AndroidEntryPoint
class PurchaseFragment : Fragment(R.layout.fragment_purchase) {
    private lateinit var emptyTextView: TextView
    private lateinit var purchaseRecycleView: RecyclerView

    public lateinit var purchaseAdapter: PurchaseAdapter
    public lateinit var parentActivity: MainActivity

    public val purchasesViewModel: PurchaseListViewModel by viewModels()
    private var binding: FragmentPurchaseBinding? = null
    private var purchasesJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initPurchases()
        initView(view)

        subscribeUi()
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }

    private fun initView(rootView: View) {
        val innerBinding = FragmentPurchaseBinding.bind(rootView)
        this.binding = innerBinding

        this.emptyTextView = innerBinding.purchaseEmptyTextView
        this.purchaseRecycleView = innerBinding.purchaseRecycleView

        purchaseAdapter = PurchaseAdapter()

        parentActivity = (activity as MainActivity)
        parentActivity.initPurchaseListeners(this)

        purchaseAdapter.notifyAction = parentActivity::notifyMenuItemSelected
        purchaseRecycleView.adapter = purchaseAdapter
    }

    private fun subscribeUi() {
        purchasesJob?.cancel()

        purchasesViewModel.getPurchases { data ->
            switchVisibility(data != PagingData.empty<Purchase>())
            purchaseAdapter.submitData(data)
        }
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

    /*FIXME: method use only for test*/
    private fun initPurchases() {
        val listPurchases = mutableListOf<Purchase>()

        for (i in 0L..50L) {
            listPurchases += Purchase(i, "$i purchase",  "$i.$i.2021")
        }

        purchasesViewModel.addPurchases(listPurchases)
    }
}