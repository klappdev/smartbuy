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
package org.kl.smartbuy.di

import android.content.Context
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import org.kl.smartbuy.db.PurchaseDatabase
import org.kl.smartbuy.db.dao.*

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePurchaseDB(@ApplicationContext context: Context): PurchaseDatabase {
        return PurchaseDatabase.getInstance(context)
    }

    @Provides
    fun provideCategoryDao(purchaseDatabase: PurchaseDatabase): CategoryDao {
        return purchaseDatabase.categoryDao()
    }

    @Provides
    fun provideProductDao(purchaseDatabase: PurchaseDatabase): ProductDao {
        return purchaseDatabase.productDao()
    }

    @Provides
    fun providePurchaseDao(purchaseDatabase: PurchaseDatabase): PurchaseDao {
        return purchaseDatabase.purchaseDao()
    }

    @Provides
    fun provideCategoryProductsDao(purchaseDatabase: PurchaseDatabase) : CategoryProductsDao {
        return purchaseDatabase.categoryProductsDao()
    }

    @Provides
    fun providePurchaseProductsDao(purchaseDatabase: PurchaseDatabase) : PurchaseProductsDao {
        return purchaseDatabase.purchaseProductsDao()
    }
}