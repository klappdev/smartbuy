package org.kl.smartbuy.event.purchase

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.MainActivity
import org.kl.smartbuy.view.fragment.PurchaseFragment

class DeletePurchaseListener(private val activity: MainActivity) {

    operator fun invoke(): Boolean {
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Delete purchase")
            .setMessage("Do you want delete purchase?")
            .setCancelable(false)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yes", ::positiveAction)
            .setNegativeButton("No",  ::negativeAction)
        dialog.show()

        return true
    }

    private fun positiveAction(dialog: DialogInterface, value: Int) {
        val purchaseFragment: PurchaseFragment? = activity.purchaseFragment
        val purchase: Purchase? = purchaseFragment?.purchaseAdapter?.getCurrentItem()

        if (purchase != null) {
            purchaseFragment.purchasesViewModel.removePurchase(purchase)
        }
    }

    private fun negativeAction(dialog: DialogInterface, value: Int) {
        dialog.cancel()
    }
}