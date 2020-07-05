package org.kl.smartbuy

import android.app.Application
import androidx.room.Room
import org.kl.smartbuy.db.PurchaseDatabase

object MainApplication : Application() {
    lateinit var database: PurchaseDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, PurchaseDatabase::class.java, "smartbuy.db")
                       .build()
    }
}