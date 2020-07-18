package org.kl.smartbuy

import android.app.Application
import androidx.room.Room
import org.kl.smartbuy.db.PurchaseDatabase

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    private lateinit var database: PurchaseDatabase

    override fun onCreate() {
        super.onCreate()

        instance = this
        database = Room.databaseBuilder(this, PurchaseDatabase::class.java, "smartbuy.db")
                       .build()
    }
}