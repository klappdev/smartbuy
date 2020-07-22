package org.kl.smartbuy.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kl.smartbuy.R
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.adapter.PurchaseAdapter

class PurchaseFragment : Fragment() {
    private lateinit var emptyTextView: TextView
    private lateinit var purchaseRecycleView: RecyclerView
    private lateinit var purchaseAdapter: PurchaseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_purchase, container, false)
        setHasOptionsMenu(true)

        this.emptyTextView = rootView.findViewById(R.id.purchase_empty_text_view)
        this.purchaseRecycleView = rootView.findViewById(R.id.purchase_recycle_view)

        val layoutManager = LinearLayoutManager(context)
        purchaseRecycleView.layoutManager = layoutManager


        purchaseAdapter = PurchaseAdapter(rootView.context, listPurchases())
        purchaseRecycleView.adapter = purchaseAdapter

        return rootView
    }

    private fun listPurchases(): List<Purchase> {
        return listOf(
            Purchase(1, R.drawable.purchase_icon, "first purchase",  "01.01.2020", false),
            Purchase(2, R.drawable.purchase_icon, "second purchase", "01.01.2020", false),
            Purchase(3, R.drawable.purchase_icon, "third purchase",  "01.01.2020", false),
            Purchase(4, R.drawable.purchase_icon, "fourth purchase", "01.01.2020", false),
            Purchase(5, R.drawable.purchase_icon, "fifth purchase",  "01.01.2020", false)
        )
    }
}