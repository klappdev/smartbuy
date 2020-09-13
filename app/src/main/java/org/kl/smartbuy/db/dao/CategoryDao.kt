package org.kl.smartbuy.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.kl.smartbuy.model.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: Int): LiveData<Category>

    @Query("SELECT * FROM category")
    fun getAll(): LiveData<List<Category>>
}