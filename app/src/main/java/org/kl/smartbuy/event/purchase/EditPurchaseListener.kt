/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2021 https://github.com/klappdev
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

import android.view.View
import org.kl.smartbuy.model.Purchase
import org.kl.smartbuy.view.activity.EditPurchaseActivity
import org.kl.smartbuy.util.toast

class EditPurchaseListener(private val activity: EditPurchaseActivity) : View.OnClickListener {
    private val nameField = activity.nameTextView
    private val dateField = activity.dateTextView
    private val viewValidator = activity.viewValidator

    override fun onClick(view: View?) {
        if (viewValidator.validate(nameField, "Name is empty") &&
            viewValidator.validate(dateField, "Date is empty")) {

            val viewModel = activity.purchaseDetailViewModel

            val newPurchase = Purchase(
                    viewModel.purchase.value.id,
                    nameField.text.toString(),
                    dateField.text.toString()
            )

            viewModel.editPurchase(newPurchase)

            activity.toast("Purchase ${newPurchase.name} was updated")
        }
    }
}