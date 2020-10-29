package org.kl.smartbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import org.kl.smartbuy.db.repo.PurchaseRepository

class PurchaseListViewModelFactory(
    private val purchaseRepository: PurchaseRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PurchaseListViewModel(purchaseRepository) as T
    }
}