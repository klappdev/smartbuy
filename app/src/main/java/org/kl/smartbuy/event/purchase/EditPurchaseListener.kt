package org.kl.smartbuy.event.purchase

import android.view.View
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.EditPurchaseActivity
import org.kl.smartbuy.event.validate.ViewValidator
import org.kl.smartbuy.util.toast

class EditPurchaseListener(
    private val activity: EditPurchaseActivity
) : View.OnClickListener {
    private val nameField = activity.nameTextView
    private val dateField = activity.dateTextView

    override fun onClick(view: View?) {
        if (!ViewValidator.validate(nameField, "Name is empty") ||
            !ViewValidator.validate(dateField, "Date is empty")) {
            return
        }

        val viewModel = activity.purchaseDetailViewModel

        val newPurchase = Purchase(
            viewModel.purchase.value?.id ?: -1,
            nameField.text.toString(),
            dateField.text.toString()
        )

        viewModel.editPurchase(newPurchase)

        activity.toast("Purchase ${newPurchase.name} was edited")
    }
}