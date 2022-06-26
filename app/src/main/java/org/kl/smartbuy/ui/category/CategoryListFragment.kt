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

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.R
import org.kl.smartbuy.db.entity.Category
import org.kl.smartbuy.viewmodel.CategoryListViewModel
import org.kl.smartbuy.databinding.FragmentCategoryListBinding
import org.kl.smartbuy.ui.tabs.TabPagerFragmentDirections

@AndroidEntryPoint
class CategoryListFragment : Fragment(R.layout.fragment_category_list) {
    private lateinit var emptyTextView: TextView
    private lateinit var categoryRecyclerView: RecyclerView

    private lateinit var categoryAdapter: CategoryAdapter

    private val categoriesViewModel: CategoryListViewModel by viewModels()
    private var binding: FragmentCategoryListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initView(view)
        subscribeUi()
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }

    private fun initView(rootView: View) {
        val innerBinding = FragmentCategoryListBinding.bind(rootView)
        this.binding = innerBinding

        this.emptyTextView = innerBinding.categoryEmptyTextView
        this.categoryRecyclerView = innerBinding.categoryRecycleView

        categoryAdapter = CategoryAdapter()
        categoryAdapter.navigateAction = this::navigateToCategoryDetail
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun subscribeUi() {
        categoriesViewModel.categories.observe(viewLifecycleOwner) { list: List<Category> ->
            switchVisibility(list.isNotEmpty())
            categoryAdapter.submitList(list)
        }
    }

    private fun navigateToCategoryDetail() {
        val categoryId = categoryAdapter.getCurrentItemId()

        if (categoryId != -1L) {
            val direction = TabPagerFragmentDirections
                .actionTabPagerFragmentToCategoryDetailFragment(categoryId)
            findNavController().navigate(direction)
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