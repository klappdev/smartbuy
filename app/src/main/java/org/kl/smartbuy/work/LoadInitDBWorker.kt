package org.kl.smartbuy.work

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.kl.smartbuy.db.PurchaseDatabase

import org.kl.smartbuy.model.Category

class LoadInitDBWorker(context: Context,
                       workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("categories.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val categoryType = object : TypeToken<List<Category>>(){}.type
                    val categories: List<Category> = Gson().fromJson(jsonReader, categoryType)

                    val database = PurchaseDatabase.getInstance(applicationContext)
                    database.categoryDao().insertAll(categories)

                    Result.success()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error preload data to db from json", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "PDBW-TAG"
    }
}