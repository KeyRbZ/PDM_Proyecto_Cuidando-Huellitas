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
            entry<Routes.Splash> {
                SplashScreen(onNavigateToLogin = {
                    backStack.removeLastOrNull()
                    backStack.add(Routes.Login)
                })
            }
            entry<Routes.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.removeLastOrNull()
                        backStack.add(Routes.MainScreen)
                    },
                    onGoToRegister = { backStack.add(Routes.Register) },
                    onGoToRecovery = { backStack.add(Routes.Recovery) }
                )
            }
            entry<Routes.Register> {
                RegisterScreen(
                    onRegisterSuccess = {
                        backStack.removeLastOrNull()
                        backStack.add(Routes.MainScreen)
                    },
                    onGoToLogin = { backStack.removeLastOrNull() }
                )
            }
            entry<Routes.Recovery> {
                RecoveryScreen(onGoToLogin = { backStack.removeLastOrNull() })
            }
            entry<Routes.MainScreen> {
                androidx.compose.material3.Text("Login Exitoso")
            }
        }
    )
}