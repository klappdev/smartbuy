package org.kl.smartbuy.util

import android.content.Context
import org.kl.smartbuy.db.PurchaseDB
import org.kl.smartbuy.db.repo.CategoryRepository
import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.viewmodel.CategoryViewModelFactory
import org.kl.smartbuy.viewmodel.PurchaseViewModelFactory

object Injector {

    private fun getPurchaseRepository(context: Context): PurchaseRepository {
        val purchaseDao = PurchaseDB.getInstance(context.applicationContext).purchaseDao()

        return PurchaseRepository.getInstance(purchaseDao)
    }

    private fun getCategoryRepository(context: Context): CategoryRepository {
        val categoryDao = PurchaseDB.getInstance(context.applicationContext).categoryDao()

        return CategoryRepository.getInstance(categoryDao)
    }

    fun providePurchaseViewModelFactory(context: Context): PurchaseViewModelFactory {
        return PurchaseViewModelFactory(getPurchaseRepository(context))
    }

    fun provideCategoryViewModelFactory(context: Context): CategoryViewModelFactory {
        return CategoryViewModelFactory(getCategoryRepository(context))
    }
}