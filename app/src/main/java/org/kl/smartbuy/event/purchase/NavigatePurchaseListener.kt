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

import androidx.navigation.Navigation

import org.kl.smartbuy.R
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.view.fragment.TabPagerFragmentDirections

class NavigatePurchaseListener(fragment: PurchaseFragment) {
    private val activity = fragment.parentActivity
    private val adapter = fragment.purchaseAdapter

    fun navigateEditPurchase(): Boolean {
        val purchaseId = adapter.getCurrentItemId().toInt()

        if (purchaseId != -1) {
            val direction = TabPagerFragmentDirections
                .actionTabPagerFragmentToEditPurchaseActivity(purchaseId)

            val navigationController = Navigation.findNavController(activity, R.id.navigation_host_fragment)
            navigationController.navigate(direction)

            return true
        }

        return false
    }
}