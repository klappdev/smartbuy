package org.kl.smartbuy.view.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.databinding.FragmentPurchaseBinding
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.util.Injector
import org.kl.smartbuy.util.toast
import org.kl.smartbuy.view.MainActivity
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
            Purchase(1, "first purchase",  "01.01.2020"),
            Purchase(2, "second purchase", "02.02.2020"),
            Purchase(3, "third purchase",  "04.04.2020"),
            Purchase(4, "fourth purchase", "08.08.2020"),
            Purchase(5, "fifth purchase",  "12.12.2020")
        )

        purchaseViewModel.addPurchases(listPurchases, 5)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        val binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        if (context == null) {
            return binding.root
        }

        val mainActivity = (activity as MainActivity)
        mainActivity.purchaseFragment = this

        this.emptyTextView = binding.purchaseEmptyTextView
        this.purchaseRecycleView = binding.purchaseRecycleView

        purchaseAdapter = PurchaseAdapter()
        purchaseAdapter.notifyAction = mainActivity::notifyItemSelected
        purchaseRecycleView.adapter = purchaseAdapter

        subscribeUi(purchaseAdapter)

        return binding.root
    }

    private fun subscribeUi(adapter: PurchaseAdapter) {
        purchaseViewModel.purchases.observe(viewLifecycleOwner) { list: List<Purchase> ->
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

    internal fun removeAction() {
        val positiveAction: (DialogInterface, Int) -> Unit = { _, _ ->
            val purchase: Purchase = purchaseAdapter.getCurrentItem()
            purchaseViewModel.removePurchase(purchase)
        }

        val negativeAction: (DialogInterface, Int) -> Unit = { dialog, _ ->
            dialog.cancel()
        }

        val dialog = AlertDialog.Builder(context!!)
        dialog.setTitle("Delete purchase")
            .setMessage("Do you want delete purchase?")
            .setCancelable(false)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yes", positiveAction)
            .setNegativeButton("No",  negativeAction)
        dialog.show()
    }

    internal fun sortAction() {
        val sortedPurchases: List<Purchase>? = purchaseViewModel.sortPurchases()

        purchaseAdapter.submitList(sortedPurchases)
        purchaseAdapter.notifyDataSetChanged()
    }
}