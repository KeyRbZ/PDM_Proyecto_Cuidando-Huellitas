package com.pdm0126.cuidandohuellitas

import android.app.Application
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepository
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepositoryImpl

class CuidandoHuellitasApplication : Application() {
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }
}