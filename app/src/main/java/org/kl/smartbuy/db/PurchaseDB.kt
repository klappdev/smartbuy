package org.kl.smartbuy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.kl.smartbuy.db.dao.CategoryDao
import org.kl.smartbuy.db.dao.ProductDao
import org.kl.smartbuy.db.dao.PurchaseDao
import org.kl.smartbuy.event.db.LoadInitDBCallback
import org.kl.smartbuy.model.Category
import org.kl.smartbuy.model.Product
import org.kl.smartbuy.model.Purchase

@Database(entities = [Product::class, Category::class, Purchase::class], version = 1)
abstract class PurchaseDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun purchaseDao(): PurchaseDao

    companion object {
        @Volatile @JvmStatic
        private var instance: PurchaseDB? = null

        @JvmStatic
        fun getInstance(context: Context): PurchaseDB {
            if (instance == null) {
                synchronized(PurchaseDB::class) {
                    if (instance == null) {
                        instance = buildDatabase(context)
                    }
                }
            }

            return instance!!
        }

        private fun buildDatabase(context: Context): PurchaseDB {
            return Room.databaseBuilder(context, PurchaseDB::class.java, "smartbuy.db")
                       .addCallback(LoadInitDBCallback(context))
                       .build()
        }
    }
}