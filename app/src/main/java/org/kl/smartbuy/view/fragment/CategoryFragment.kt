package org.kl.smartbuy.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.R
import org.kl.smartbuy.model.Category
import org.kl.smartbuy.util.Injector
import org.kl.smartbuy.view.adapter.CategoryAdapter
import org.kl.smartbuy.viewmodel.CategoryListViewModel
import org.kl.smartbuy.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var emptyTextView: TextView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private var binding: FragmentCategoryBinding? = null

    private val categoriesViewModel: CategoryListViewModel by viewModels {
        Injector.provideCategoryListViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val innerBinding = FragmentCategoryBinding.bind(view)
        this.binding = innerBinding

        this.emptyTextView = innerBinding.categoryEmptyTextView
        this.categoryRecyclerView = innerBinding.categoryRecycleView

        categoryAdapter = CategoryAdapter()
        categoryRecyclerView.adapter = categoryAdapter

        subscribeUi(categoryAdapter)
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
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