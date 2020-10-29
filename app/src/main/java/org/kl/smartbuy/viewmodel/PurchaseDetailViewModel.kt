package org.kl.smartbuy.viewmodel

import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.model.Purchase

class PurchaseDetailViewModel(
    private val purchaseRepository: PurchaseRepository,
    private val purchaseId: Int
) : ViewModel() {
    val purchase: LiveData<Purchase> = purchaseRepository.getPurchase(purchaseId)

    fun editPurchase(purchase: Purchase) {
        viewModelScope.launch {
            purchaseRepository.updatePurchase(purchase)
        }
    }
}