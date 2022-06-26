/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2022 https://github.com/klappdev
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
import androidx.work.Data
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import timber.log.Timber

import org.kl.smartbuy.db.PurchaseDatabase
import org.kl.smartbuy.db.dao.CategoryDao
import org.kl.smartbuy.db.entity.Category

class LoadCategoryWorker(
    context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val database = PurchaseDatabase.getInstance(applicationContext)
        val categoryDao = database.categoryDao()

        try {
            loadCategories(categoryDao)

            val idLessons = categoryDao.getAllIds()

            val outputData = Data.Builder()
                    .putLongArray("id_lessons", idLessons.toLongArray())
                    .build()

            Result.success(outputData)
        } catch (e: Exception) {
            Timber.e(e, "Error preload categories to db from json file")
            Result.failure()
        }
    }

    @Throws(Exception::class)
    private suspend fun loadCategories(categoryDao: CategoryDao) {
        applicationContext.assets.open("categories.json").use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val categoryType = object : TypeToken<List<Category>>(){}.type
                val categories: List<Category> = Gson().fromJson(jsonReader, categoryType)

                categoryDao.insertAll(categories)
            }
        }
    }
}
