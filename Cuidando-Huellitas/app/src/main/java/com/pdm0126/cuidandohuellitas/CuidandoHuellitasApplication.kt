package com.pdm0126.cuidandohuellitas

import android.app.Application
import androidx.room.Room
import com.pdm0126.cuidandohuellitas.Data.AppProvider
import com.pdm0126.cuidandohuellitas.Data.database.AppDatabase
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepository
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepositoryImpl

class CuidandoHuellitasApplication : Application() {
    lateinit var appProvider: AppProvider

    val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }


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