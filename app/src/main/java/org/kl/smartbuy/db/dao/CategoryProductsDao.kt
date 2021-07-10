package org.kl.smartbuy.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

import org.kl.smartbuy.model.CategoryProducts

@Dao
interface CategoryProductsDao {

    @Transaction
    @Query("SELECT * FROM category WHERE id_category = :id")
    fun getById(id: Long): LiveData<CategoryProducts>

    @Transaction
    @Query("SELECT * FROM category")
    fun getAll(): LiveData<List<CategoryProducts>>
}