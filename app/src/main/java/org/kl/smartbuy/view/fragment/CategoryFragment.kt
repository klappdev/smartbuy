package org.kl.smartbuy.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.model.Category
import org.kl.smartbuy.util.Injector
import org.kl.smartbuy.view.adapter.CategoryAdapter
import org.kl.smartbuy.viewmodel.CategoryViewModel
import org.kl.smartbuy.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private lateinit var emptyTextView: TextView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    private val categoryViewModel: CategoryViewModel by viewModels {
        Injector.provideCategoryViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        val binding = FragmentCategoryBinding.inflate(inflater, container, false)

        if (context == null) {
            return binding.root
        }

        this.emptyTextView = binding.categoryEmptyTextView
        this.categoryRecyclerView = binding.categoryRecycleView

        categoryAdapter = CategoryAdapter()
        categoryRecyclerView.adapter = categoryAdapter

        subscribeUi(categoryAdapter)

        return binding.root
    }

    private fun subscribeUi(adapter: CategoryAdapter) {
        categoryViewModel.categories.observe(viewLifecycleOwner) { list: List<Category> ->
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