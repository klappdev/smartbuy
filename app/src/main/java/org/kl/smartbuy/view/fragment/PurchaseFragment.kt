package org.kl.smartbuy.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.R
import org.kl.smartbuy.databinding.FragmentPurchaseBinding
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.util.Injector
import org.kl.smartbuy.view.adapter.PurchaseAdapter
import org.kl.smartbuy.viewmodel.PurchaseViewModel

class PurchaseFragment : Fragment() {
    private lateinit var emptyTextView: TextView
    private lateinit var purchaseRecycleView: RecyclerView
    private lateinit var purchaseAdapter: PurchaseAdapter

    private val purchaseViewModel: PurchaseViewModel by viewModels {
        Injector.providePurchaseViewModelFactory(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val listPurchases = listOf(
            Purchase(1, R.drawable.purchase_icon, "first purchase",  "01.01.2020", false),
            Purchase(2, R.drawable.purchase_icon, "second purchase", "01.01.2020", false),
            Purchase(3, R.drawable.purchase_icon, "third purchase",  "01.01.2020", false),
            Purchase(4, R.drawable.purchase_icon, "fourth purchase", "01.01.2020", false),
            Purchase(5, R.drawable.purchase_icon, "fifth purchase",  "01.01.2020", false)
        )

        purchaseViewModel.addPurchases(listPurchases, 5)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        val binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        if (context == null) {
            return binding.root
        }

        this.emptyTextView = binding.purchaseEmptyTextView
        this.purchaseRecycleView = binding.purchaseRecycleView

        purchaseAdapter = PurchaseAdapter()
        purchaseRecycleView.adapter = purchaseAdapter

        subscribeUi(purchaseAdapter)

        return binding.root
    }

    private fun subscribeUi(adapter: PurchaseAdapter) {
        purchaseViewModel.purchases.observe(viewLifecycleOwner, adapter::submitList)
    }
}