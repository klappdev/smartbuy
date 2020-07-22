package org.kl.smartbuy.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.kl.smartbuy.model.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM product WHERE id = :id")
    fun getById(id: Int): LiveData<Product>

    @Query("SELECT * FROM product")
    fun getAll(): LiveData<List<Product>>
}