package org.kl.smartbuy.event.purchase

import android.view.View
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.util.toast

class DeletePurchaseListener(
    private val purchase: Purchase
) : View.OnClickListener {
    private lateinit var context: Context

    override fun onClick(view: View?) {
        this.context = view?.context!!

        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Delete purchase")
            .setMessage("Do you want delete purchase?")
            .setCancelable(false)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yes", ::clickPositiveButton)
            .setNegativeButton("No",  ::clickNegativeButton)
        dialog.show()
    }

    private fun clickPositiveButton(dialog: DialogInterface, id: Int) {
        context.toast("Delete purchase: ${purchase.name} - ${purchase.id} - $id")
    }

    private fun clickNegativeButton(dialog: DialogInterface, id: Int) {
        dialog.cancel()
    }
}