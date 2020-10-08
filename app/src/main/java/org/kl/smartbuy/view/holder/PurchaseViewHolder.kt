package org.kl.smartbuy.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import org.kl.smartbuy.databinding.PurchaseItemBinding
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.util.toast
import org.kl.smartbuy.R

class PurchaseViewHolder(
    val binding: PurchaseItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener(::navigateToPurchase)
    }

    companion object {
        @JvmStatic
        internal var currentPosition: Int = -1
    }

    fun bind(item: Purchase, position: Int) {
        if (currentPosition == position) {
            binding.itemPurchaseImage.setImageResource(R.drawable.purchase_selected_icon)
        } else {
            binding.itemPurchaseImage.setImageResource(R.drawable.purchase_icon)
        }
		
        with(binding) {
            purchase = item
            executePendingBindings()
        }
    }

    private fun navigateToPurchase(view: View?) {
        view?.context?.toast("SELECT CURRENT PURCHASE")
    }
}