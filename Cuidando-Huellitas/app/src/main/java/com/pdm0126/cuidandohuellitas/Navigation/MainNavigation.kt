package com.pdm0126.cuidandohuellitas.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.entryProvider
import com.pdm0126.cuidandohuellitas.ui.LoginScreen
import com.pdm0126.cuidandohuellitas.ui.RecoveryScreen
import com.pdm0126.cuidandohuellitas.ui.RegisterScreen
import com.pdm0126.cuidandohuellitas.ui.SplashScreen

@Composable
fun App() {
    val backStack = rememberNavBackStack(Routes.Splash)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {

        }
    )
}