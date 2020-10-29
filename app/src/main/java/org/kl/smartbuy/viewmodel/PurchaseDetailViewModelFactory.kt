package org.kl.smartbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kl.smartbuy.db.repo.PurchaseRepository

class PurchaseDetailViewModelFactory(
    private val purchaseRepository: PurchaseRepository,
    private val purchaseId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PurchaseDetailViewModel(purchaseRepository, purchaseId) as T
    }
}