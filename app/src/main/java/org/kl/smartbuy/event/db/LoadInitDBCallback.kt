package org.kl.smartbuy.event.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.kl.smartbuy.work.LoadInitDBWorker

class LoadInitDBCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val request = OneTimeWorkRequestBuilder<LoadInitDBWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }
}