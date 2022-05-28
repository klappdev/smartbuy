/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2021 https://github.com/klappdev
 *
 * Permission is hereby  granted, free of charge, to any  person obtaining a copy
 * of this software and associated  documentation files (the "Software"), to deal
 * in the Software  without restriction, including without  limitation the rights
 * to  use, copy,  modify, merge,  publish, distribute,  sublicense, and/or  sell
 * copies  of  the Software,  and  to  permit persons  to  whom  the Software  is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE  IS PROVIDED "AS  IS", WITHOUT WARRANTY  OF ANY KIND,  EXPRESS OR
 * IMPLIED,  INCLUDING BUT  NOT  LIMITED TO  THE  WARRANTIES OF  MERCHANTABILITY,
 * FITNESS FOR  A PARTICULAR PURPOSE AND  NONINFRINGEMENT. IN NO EVENT  SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS  BE  LIABLE FOR  ANY  CLAIM,  DAMAGES OR  OTHER
 * LIABILITY, WHETHER IN AN ACTION OF  CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE  OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kl.smartbuy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import org.kl.smartbuy.event.db.LoadInitDBCallback
import org.kl.smartbuy.db.dao.*
import org.kl.smartbuy.db.entity.Category
import org.kl.smartbuy.db.entity.Product
import org.kl.smartbuy.db.entity.Purchase
import org.kl.smartbuy.db.entity.PurchaseProduct

@Database(
    entities = [
        Product::class, Category::class,
        Purchase::class, PurchaseProduct::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PurchaseDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun categoryProductsDao(): CategoryProductsDao
    abstract fun purchaseProductsDao(): PurchaseProductsDao

    companion object {
        @Volatile @JvmStatic
        private var instance: PurchaseDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): PurchaseDatabase {
            if (instance == null) {
                synchronized(PurchaseDatabase::class) {
                    if (instance == null) {
                        instance = buildDatabase(context)
                    }
                }
            }

            return instance!!
        }

        @JvmStatic
        private fun buildDatabase(context: Context): PurchaseDatabase {
            return Room.databaseBuilder(context, PurchaseDatabase::class.java, "smartbuy.db")
                       .addCallback(LoadInitDBCallback(context))
                       .build()
        }
    }
}