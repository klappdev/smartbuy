/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2022 https://github.com/klappdev
 *
 * Permission is hereby  granted, free of charge, to any  person obtaining a copy
 * of this software and associated  documentation files (the "Software"), to deal
 * in the Software  without restriction, including without  limitation the rights
 * to  use, copy,  modify, merge,  publish, distribute,  sublicense, and/or  sell
 * copies  of  the Software,  and  to  permit persons  to  whom  the Software  is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE  IS PROVIDED "AS  IS", WITHOUT WARRANTY  OF ANY KIND,  EXPRESS OR
 * IMPLIED,  INCLUDING BUT  NOT  LIMITED TO  THE  WARRANTIES OF  MERCHANTABILITY,
 * FITNESS FOR  A PARTICULAR PURPOSE AND  NONINFRINGEMENT. IN NO EVENT  SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS  BE  LIABLE FOR  ANY  CLAIM,  DAMAGES OR  OTHER
 * LIABILITY, WHETHER IN AN ACTION OF  CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE  OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kl.smartbuy.event.purchase

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

import org.kl.smartbuy.db.entity.Purchase
import org.kl.smartbuy.ui.purchase.PurchaseListFragment

class DeletePurchaseListener(fragment: PurchaseListFragment) {
    private val activity = fragment.parentActivity
    private val purchaseAdapter = fragment.purchaseAdapter
    private val purchaseViewModel = fragment.purchasesViewModel

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
        val purchase: Purchase? = purchaseAdapter.getCurrentItem()

        if (purchase != null) {
            purchaseViewModel.removePurchase(purchase)
        }
    }

    private fun negativeAction(dialog: DialogInterface, value: Int) {
        dialog.cancel()
    }
}