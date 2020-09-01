package org.kl.smartbuy.util

import android.content.Context
import org.kl.smartbuy.db.PurchaseDB
import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.viewmodel.PurchaseViewModelFactory


object Injector {

    private fun getPurchaseRepository(context: Context) : PurchaseRepository {
        val purchaseDao = PurchaseDB.getInstance(context.applicationContext).purchaseDao()

        return PurchaseRepository.getInstance(purchaseDao)
    }

    fun providePurchaseViewModelFactory(context: Context): PurchaseViewModelFactory {
        return PurchaseViewModelFactory(getPurchaseRepository(context))
    }
}