package org.kl.smartbuy.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.R
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.util.Injector
import org.kl.smartbuy.view.MainActivity
import org.kl.smartbuy.view.adapter.PurchaseAdapter
import org.kl.smartbuy.viewmodel.PurchaseListViewModel
import org.kl.smartbuy.databinding.FragmentPurchaseBinding

class PurchaseFragment : Fragment(R.layout.fragment_purchase) {
    private lateinit var emptyTextView: TextView
    private lateinit var purchaseRecycleView: RecyclerView
    internal lateinit var purchaseAdapter: PurchaseAdapter
    private var binding: FragmentPurchaseBinding? = null

    internal val purchasesViewModel: PurchaseListViewModel by viewModels {
        Injector.providePurchaseListViewModelFactory(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val listPurchases = listOf(
            Purchase(1, "first purchase",  "01.01.2020"),
            Purchase(2, "second purchase", "02.02.2020"),
            Purchase(3, "third purchase",  "04.04.2020"),
            Purchase(4, "fourth purchase", "08.08.2020"),
            Purchase(5, "fifth purchase",  "12.12.2020")
        )

        purchasesViewModel.addPurchases(listPurchases, 5)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val innerBinding = FragmentPurchaseBinding.bind(view)
        this.binding = innerBinding

        val mainActivity = (activity as MainActivity)
        mainActivity.purchaseFragment = this

        this.emptyTextView = innerBinding.purchaseEmptyTextView
        this.purchaseRecycleView = innerBinding.purchaseRecycleView

        purchaseAdapter = PurchaseAdapter()
        purchaseAdapter.notifyAction = mainActivity::notifyItemSelected
        purchaseRecycleView.adapter = purchaseAdapter

        subscribeUi(purchaseAdapter)
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }

    private fun subscribeUi(adapter: PurchaseAdapter) {
        purchasesViewModel.purchases.observe(viewLifecycleOwner) { list: List<Purchase> ->
            switchVisibility(list.isNotEmpty())
            adapter.submitList(list)
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
}