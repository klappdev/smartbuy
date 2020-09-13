package org.kl.smartbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kl.smartbuy.db.repo.CategoryRepository

class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository) as T
    }
}