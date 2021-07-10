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
package org.kl.smartbuy.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import timber.log.Timber

import org.kl.smartbuy.db.PurchaseDatabase
import org.kl.smartbuy.db.convert.ProductDeserializer
import org.kl.smartbuy.model.Product

class LoadProductWorker(
    context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        val idLessons: LongArray? = inputData.getLongArray("id_lessons")

        try {
            if (idLessons != null && idLessons.isNotEmpty()) {
                val products = loadProducts()
                storeProducts(idLessons, products)

                Result.success()
            } else {
                Timber.e("Error get input id lessons")
                Result.failure()
            }
        } catch (e: Exception) {
            Timber.e(e, "Error preload products to db from json")
            Result.failure()
        }
    }

    @Throws(Exception::class)
    private fun loadProducts(): List<Product> {
        val products = mutableListOf<Product>()

        applicationContext.assets.open("products.json").use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val productType = object : TypeToken<List<Product>>(){}.type
                val gson = GsonBuilder()
                        .registerTypeAdapter(Product::class.java, ProductDeserializer())
                        .create()
                products.addAll(gson.fromJson(jsonReader, productType))
            }
        }

        return products
    }

    private suspend fun storeProducts(idLessons: LongArray, products: List<Product>) {
        val listProducts = mutableListOf<Product>()

        for (product in products) {
            val newIdCategory = idLessons[(product.idCategory - 1).toInt()]
            listProducts += product.copy(idCategory = newIdCategory)
        }

        val database = PurchaseDatabase.getInstance(applicationContext)
        database.productDao().insertAll(listProducts)
    }
}