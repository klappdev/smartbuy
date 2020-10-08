package org.kl.smartbuy.db.repo

import androidx.lifecycle.LiveData

import org.kl.smartbuy.db.dao.PurchaseDao
import org.kl.smartbuy.model.Purchase

class PurchaseRepository private constructor(private val purchaseDao: PurchaseDao) {

    fun getPurchase(id: Int): LiveData<Purchase> = purchaseDao.getById(id)

    fun getPurchases(): LiveData<List<Purchase>> = purchaseDao.getAll()

    suspend fun insertPurchases(listPurchases: List<Purchase>) {
        purchaseDao.insertAll(listPurchases)
    }

    suspend fun deletePurchase(purchase: Purchase) {
        purchaseDao.delete(purchase)
    }

    companion object {
        @Volatile @JvmStatic
        private var instance: PurchaseRepository? = null

        @JvmStatic
        fun getInstance(purchaseDao: PurchaseDao): PurchaseRepository {
            if (instance == null) {
                synchronized(PurchaseRepository::class) {
                    if (instance == null) {
                        instance = PurchaseRepository(purchaseDao)
                    }
                }
            }

            return instance!!
        }
    }
}