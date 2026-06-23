package com.pdm0126.cuidandohuellitas

import androidx.room.Room
import com.pdm0126.cuidandohuellitas.Data.AppProvider
import com.pdm0126.cuidandohuellitas.Data.database.AppDatabase
import okhttp3.internal.platform.PlatformRegistry.applicationContext
import android.app.Application

class CuidandoHuellitasApplication : Application() {
    lateinit var appProvider: AppProvider

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "cuidando_huellitas_db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        appProvider = AppProvider(db, applicationContext)
    }
}