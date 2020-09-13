package org.kl.smartbuy.viewmodel

import kotlinx.coroutines.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.model.Purchase

class PurchaseViewModel(
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {
    val purchases: LiveData<List<Purchase>> = purchaseRepository.getPurchases()

    fun addPurchases(listPurchases: List<Purchase>, limit: Int) {
        val size: Int = purchases.value?.size ?: 0

        if (size <= limit) {
            viewModelScope.launch {
                purchaseRepository.createPurchases(listPurchases)
            }
        }
    }
}