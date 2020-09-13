package org.kl.smartbuy.db.repo

import androidx.lifecycle.LiveData
import org.kl.smartbuy.db.dao.CategoryDao
import org.kl.smartbuy.model.Category

class CategoryRepository private constructor(private val categoryDao: CategoryDao) {

    fun getCategory(id: Int): LiveData<Category> = categoryDao.getById(id)

    fun getCategories(): LiveData<List<Category>> = categoryDao.getAll()

    suspend fun createCategories(listCategories: List<Category>) {
        categoryDao.insertAll(listCategories)
    }

    companion object {
        @Volatile @JvmStatic
        private var instance: CategoryRepository? = null

        @JvmStatic
        fun getInstance(categoryDao: CategoryDao): CategoryRepository {
            if (instance == null) {
                synchronized(CategoryRepository::class) {
                    if (instance == null) {
                        instance = CategoryRepository(categoryDao)
                    }
                }
            }

            return instance!!
        }
    }
}