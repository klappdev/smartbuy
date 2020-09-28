package org.kl.smartbuy.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.kl.smartbuy.R

import org.kl.smartbuy.databinding.PurchaseItemBinding
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.util.toast

class PurchaseViewHolder(
    private val binding: PurchaseItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private var rootView: View? = null

    init {
        this.rootView = binding.root
        this.rootView?.setOnClickListener { view: View? ->
            view?.context?.toast("SELECT ONE PURCHASE")
        }

        rootView?.setOnLongClickListener { view: View? ->
            this.bindingAdapter?.notifyDataSetChanged()

            true
        }
    }

    fun bind(item: Purchase, position: Int) {
		val binding = holder.binding

        if (this.absoluteAdapterPosition == position) {
            binding.itemPurchaseImage.setImageResource(R.drawable.purchase_selected_icon)
            binding.editPurchaseImage.visibility = View.VISIBLE
            binding.deletePurchaseImage.visibility = View.VISIBLE
        } else {
            binding.itemPurchaseImage.setImageResource(R.drawable.purchase_icon)
            binding.editPurchaseImage.visibility = View.GONE
            binding.deletePurchaseImage.visibility = View.GONE
        }
		
        with(binding) {
            purchase = item
            executePendingBindings()
        }
    }
}