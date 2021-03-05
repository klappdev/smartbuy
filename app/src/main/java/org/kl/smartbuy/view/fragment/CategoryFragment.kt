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
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.R
import org.kl.smartbuy.model.Category
import org.kl.smartbuy.view.adapter.CategoryAdapter
import org.kl.smartbuy.view.activity.MainActivity
import org.kl.smartbuy.viewmodel.CategoryListViewModel
import org.kl.smartbuy.databinding.FragmentCategoryBinding

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var emptyTextView: TextView
    private lateinit var categoryRecyclerView: RecyclerView

    private lateinit var categoryAdapter: CategoryAdapter
    public lateinit var parentActivity: MainActivity

    private val categoriesViewModel: CategoryListViewModel by viewModels()
    private var binding: FragmentCategoryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initView(view)
        subscribeUi(categoryAdapter)
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }

    private fun initView(rootView: View) {
        val innerBinding = FragmentCategoryBinding.bind(rootView)
        this.binding = innerBinding

        this.emptyTextView = innerBinding.categoryEmptyTextView
        this.categoryRecyclerView = innerBinding.categoryRecycleView

        categoryAdapter = CategoryAdapter()
        categoryRecyclerView.adapter = categoryAdapter

        parentActivity = (activity as MainActivity)
        parentActivity.initCategoryListeners(this)
    }

    private fun subscribeUi(adapter: CategoryAdapter) {
        categoriesViewModel.categories.observe(viewLifecycleOwner) { list: List<Category> ->
            switchVisibility(list.isNotEmpty())
            adapter.submitList(list)
        }
    }

    private fun switchVisibility(flag: Boolean) {
        if (flag) {
            categoryRecyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        } else {
            categoryRecyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        }
    }
}