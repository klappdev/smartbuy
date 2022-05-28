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
package org.kl.smartbuy.viewmodel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.db.entity.Purchase

@HiltViewModel
class PurchaseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val purchaseRepository: PurchaseRepository,
) : ViewModel() {
    private val purchaseId: Long = savedStateHandle.get<Long>("purchaseId") ?: -1L

    private var _name = MutableStateFlow("")
    public val name: StateFlow<String> = _name.asStateFlow()

    private var _date = MutableStateFlow("")
    public val date: StateFlow<String> = _date.asStateFlow()

    init {
        loadPurchase()
    }

    private fun loadPurchase() {
        viewModelScope.launch {
            purchaseRepository.getPurchase(purchaseId).collect { purchase ->
                _name.value = purchase.name
                _date.value = purchase.date

                Log.i("TEST", "LOAD NAME: ${_name.value}")
                Log.i("TEST", "LOAD DATE: ${_date.value}")
            }
        }
    }

    fun storePurchase() {
        viewModelScope.launch {
            Log.i("TEST", "STORE NAME: ${_name.value}")
            Log.i("TEST", "STORE DATE: ${_date.value}")

            val purchase = Purchase(purchaseId, _name.value, _date.value)
            purchaseRepository.updatePurchase(purchase)
        }
    }

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onDateChange(date: String) {
        _date.value = date
    }
}