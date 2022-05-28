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
package org.kl.smartbuy.ui.purchase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

import org.kl.smartbuy.db.entity.Purchase
import org.kl.smartbuy.databinding.PurchaseItemBinding
import org.kl.smartbuy.event.diff.PurchaseDifferenceCallback
import org.kl.smartbuy.event.purchase.ChoosePurchaseListener

class PurchaseAdapter : PagingDataAdapter<Purchase, PurchaseViewHolder>(PurchaseDifferenceCallback()) {
    var notifyAction: ((Boolean) -> Boolean)? = null
    var position: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PurchaseItemBinding.inflate(inflater, parent, false)

        return PurchaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val purchase = getItem(position)

        if (purchase != null) {
            holder.bind(purchase, getCurrentItemId())
            holder.binding.root.setOnLongClickListener(ChoosePurchaseListener(holder))
        }
    }

    fun getCurrentItem(): Purchase? = getItem(position)
    fun getCurrentItemId(): Long = getItem(position)?.id ?: -1
}