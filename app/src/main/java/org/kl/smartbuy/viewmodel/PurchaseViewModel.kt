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
    var isAsc: Boolean = false
    val purchases: LiveData<List<Purchase>> = purchaseRepository.getPurchases()

    fun addPurchases(listPurchases: List<Purchase>, limit: Int) {
        val size: Int = purchases.value?.size ?: 0

        if (size <= limit) {
            viewModelScope.launch {
                purchaseRepository.insertPurchases(listPurchases)
            }
        }
    }

    fun removePurchase(purchase: Purchase) {
        viewModelScope.launch {
            purchaseRepository.deletePurchase(purchase)
        }
    }

    fun sortPurchases(): List<Purchase>? {
        var sortedPurchases: List<Purchase>? = null

        viewModelScope.launch {
            sortedPurchases = if (isAsc) {
                purchases.value?.sortedBy(Purchase::date)
            } else {
                purchases.value?.sortedByDescending(Purchase::date)
            }
        }

        isAsc = !isAsc

        return sortedPurchases
    }

    fun searchPurchases(text: String): List<Purchase>? {
        var searchedPurchases: List<Purchase>? = null

        viewModelScope.launch {
            searchedPurchases = purchases.value?.filter { text in it.name }
        }

        return searchedPurchases
    }
}