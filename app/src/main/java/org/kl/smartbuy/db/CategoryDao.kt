package org.kl.smartbuy.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.kl.smartbuy.model.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: Int): LiveData<Category>

    @Query("SELECT * FROM category")
    fun getAll(): LiveData<List<Category>>
}