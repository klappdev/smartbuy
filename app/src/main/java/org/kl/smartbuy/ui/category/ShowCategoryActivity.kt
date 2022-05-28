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
package org.kl.smartbuy.ui.category

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.databinding.ActivityShowCategoryBinding
import org.kl.smartbuy.event.product.NavigateProductListener
import org.kl.smartbuy.db.entity.Product
import org.kl.smartbuy.ui.product.ProductAdapter
import org.kl.smartbuy.viewmodel.ProductListViewModel

@AndroidEntryPoint
class ShowCategoryActivity : AppCompatActivity() {
    private lateinit var emptyTextView: TextView
    private lateinit var productRecyclerView: RecyclerView

    private lateinit var productAdapter: ProductAdapter

    private val productsViewModel: ProductListViewModel by viewModels()
    private lateinit var navigateProductListener: NavigateProductListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityShowCategoryBinding.inflate(layoutInflater)

        initView(binding)
        subscribeUi()

        setContentView(binding.root)
    }

    private fun initView(binding: ActivityShowCategoryBinding) {
        this.emptyTextView = binding.productEmptyTextView
        this.productRecyclerView = binding.productRecycleView

        productAdapter = ProductAdapter()

        initListeners()

        productAdapter.navigateAction = navigateProductListener::navigateShowProduct
        productRecyclerView.adapter = productAdapter
    }

    private fun subscribeUi() {
        productsViewModel.products.observe(this) { products: List<Product> ->
            switchVisibility(products.isNotEmpty())
            productAdapter.submitList(products)
        }
    }

    private fun initListeners() {
        this.navigateProductListener = NavigateProductListener(this)
    }

    private fun switchVisibility(flag: Boolean) {
        if (flag) {
            productRecyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        } else {
            productRecyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        }
    }
}