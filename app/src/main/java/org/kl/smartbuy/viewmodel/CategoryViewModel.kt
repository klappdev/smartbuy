package org.kl.smartbuy.viewmodel

import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import org.kl.smartbuy.model.Category
import org.kl.smartbuy.db.repo.CategoryRepository

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val categories: LiveData<List<Category>> = categoryRepository.getCategories()

    fun addCategories(listCategories: List<Category>, limit: Int) {
        val size: Int = categories.value?.size ?: 0

        if (size <= limit) {
            viewModelScope.launch {
                categoryRepository.createCategories(listCategories)
            }
        }
    }
}