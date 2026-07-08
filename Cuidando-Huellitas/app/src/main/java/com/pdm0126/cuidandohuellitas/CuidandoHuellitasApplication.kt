package com.pdm0126.cuidandohuellitas

import android.app.Application
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepository
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepositoryImpl

class CuidandoHuellitasApp : Application() {
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }
}