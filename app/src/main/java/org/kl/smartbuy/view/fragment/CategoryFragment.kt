package org.kl.smartbuy.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kl.smartbuy.R
import org.kl.smartbuy.db.PurchaseDatabase
import org.kl.smartbuy.model.Category
import org.kl.smartbuy.view.adapter.CategoryAdapter

class CategoryFragment : Fragment() {
    private lateinit var emptyTextView: TextView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_category, container, false)
        setHasOptionsMenu(true)

        this.emptyTextView = rootView.findViewById(R.id.category_empty_text_view)
        this.categoryRecyclerView = rootView.findViewById(R.id.category_recycle_view)

        val layoutManager = GridLayoutManager(context, 2)
        categoryRecyclerView.layoutManager = layoutManager


        this.categoryAdapter = CategoryAdapter(rootView.context, listCategories(rootView.context))
        this.categoryRecyclerView.adapter = categoryAdapter

        return rootView
    }

    private fun listCategories(context: Context): List<Category> {
        val db = PurchaseDatabase.getInstance(context)
        val data: LiveData<List<Category>> = db.categoryDao().getAll()

        return data.value ?: emptyList()
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