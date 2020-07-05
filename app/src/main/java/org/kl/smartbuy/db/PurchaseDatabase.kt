package org.kl.smartbuy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.kl.smartbuy.model.Category
import org.kl.smartbuy.model.Product
import org.kl.smartbuy.model.Purchase

@Database(entities = [Product::class, Category::class, Purchase::class], version = 1)
abstract class PurchaseDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun categoryDao(): CategoryDao

    abstract fun purchaseDao(): PurchaseDao
}