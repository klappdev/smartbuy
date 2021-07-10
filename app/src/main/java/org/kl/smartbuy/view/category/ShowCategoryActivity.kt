package org.kl.smartbuy.view.category

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.databinding.ActivityShowCategoryBinding
import org.kl.smartbuy.model.Product
import org.kl.smartbuy.view.product.ProductAdapter
import org.kl.smartbuy.viewmodel.ProductListViewModel

@AndroidEntryPoint
class ShowCategoryActivity : AppCompatActivity() {
    private lateinit var emptyTextView: TextView
    private lateinit var productRecyclerView: RecyclerView

    private lateinit var productAdapter: ProductAdapter
    private val productsViewModel: ProductListViewModel by viewModels()

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
        productRecyclerView.adapter = productAdapter
    }

    private fun subscribeUi() {
        productsViewModel.products.observe(this) { list: List<Product> ->
            switchVisibility(list.isNotEmpty())
            productAdapter.submitList(list)
        }
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