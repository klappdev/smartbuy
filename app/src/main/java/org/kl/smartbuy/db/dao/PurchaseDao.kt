package org.kl.smartbuy.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.kl.smartbuy.model.Purchase

@Dao
interface PurchaseDao {

    @Insert
    suspend fun insert(purchase: Purchase)

    @Update
    suspend fun update(purchase: Purchase)

    @Delete
    suspend fun delete(purchase: Purchase)

    @Query("SELECT * FROM purchase WHERE id = :id")
    fun getById(id: Int): LiveData<Purchase>

    @Query("SELECT * FROM purchase")
    fun getAll(): LiveData<List<Purchase>>
}