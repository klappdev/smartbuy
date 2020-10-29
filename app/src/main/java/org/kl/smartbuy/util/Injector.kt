package org.kl.smartbuy.util

import android.content.Context
import org.kl.smartbuy.db.PurchaseDB
import org.kl.smartbuy.db.repo.CategoryRepository
import org.kl.smartbuy.db.repo.PurchaseRepository
import org.kl.smartbuy.viewmodel.CategoryListViewModelFactory
import org.kl.smartbuy.viewmodel.PurchaseDetailViewModelFactory
import org.kl.smartbuy.viewmodel.PurchaseListViewModelFactory

object Injector {

    private fun getPurchaseRepository(context: Context): PurchaseRepository {
        val purchaseDao = PurchaseDB.getInstance(context.applicationContext).purchaseDao()

        return PurchaseRepository.getInstance(purchaseDao)
    }

    private fun getCategoryRepository(context: Context): CategoryRepository {
        val categoryDao = PurchaseDB.getInstance(context.applicationContext).categoryDao()

        return CategoryRepository.getInstance(categoryDao)
    }

    fun providePurchaseDetailViewModelFactory(context: Context, id: Int) : PurchaseDetailViewModelFactory {
        return PurchaseDetailViewModelFactory(getPurchaseRepository(context), id)
    }


    fun providePurchaseListViewModelFactory(context: Context): PurchaseListViewModelFactory {
        return PurchaseListViewModelFactory(getPurchaseRepository(context))
    }

    fun provideCategoryListViewModelFactory(context: Context): CategoryListViewModelFactory {
        return CategoryListViewModelFactory(getCategoryRepository(context))
    }
}