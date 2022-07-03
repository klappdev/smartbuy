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
package org.kl.smartbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.db.entity.Purchase

@HiltViewModel
class PurchaseListViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {
    private var isAsc: Boolean = false
    private var _purchases: Flow<PagingData<Purchase>>? = null

    public val purchases
        get() = purchaseRepository.getPurchases().cachedIn(viewModelScope)

    fun addPurchases(listPurchases: List<Purchase>) {
        viewModelScope.launch {
            purchaseRepository.insertPurchases(listPurchases)
        }
    }

    fun removePurchase(purchase: Purchase) {
        viewModelScope.launch {
            purchaseRepository.deletePurchase(purchase)
        }
    }

    fun getPurchases(action: suspend (PagingData<Purchase>) -> Unit) {
        val result = purchaseRepository.getPurchases().cachedIn(viewModelScope)

        _purchases = result

        viewModelScope.launch {
            result.collectLatest { data ->
                action(data)
            }
        }
    }

    fun sortPurchases(action: suspend (PagingData<Purchase>) -> Unit) {
        val result = purchaseRepository.sortByDatePurchases(isAsc).cachedIn(viewModelScope)

        isAsc = !isAsc
        _purchases = result

        viewModelScope.launch {
            result.collectLatest { data ->
                action(data)
            }
        }
    }

    fun searchPurchases(text: String, action: suspend (PagingData<Purchase>) -> Unit) {
        val result = purchaseRepository.searchByNamePurchases("%$text%").cachedIn(viewModelScope)

        _purchases = result

        viewModelScope.launch {
            result.collectLatest { data ->
                action(data)
            }
        }
    }
}