package com.pdm0126.cuidandohuellitas.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.entryProvider
import com.google.firebase.auth.FirebaseAuth
import com.pdm0126.cuidandohuellitas.ui.*
import com.pdm0126.cuidandohuellitas.Screens.MainScreen.MainScreen
import com.pdm0126.cuidandohuellitas.Screens.Profile.ProfileScreen

@Composable
fun App() {
    val backStack = rememberNavBackStack(Routes.Splash)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Routes.Splash> {
                SplashScreen(onNavigateToLogin = {
                    backStack.clear()
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        backStack.add(Routes.MainScreen)
                    } else {
                        backStack.add(Routes.Login)
                    }
                })
            }
            entry<Routes.Login> { LoginScreen(onLoginSuccess = { backStack.clear(); backStack.add(Routes.MainScreen) }, onGoToRegister = { backStack.add(Routes.Register) }, onGoToRecovery = { backStack.add(Routes.Recovery) }) }
            entry<Routes.MainScreen> { MainScreen(navToPetInfo = {}, navToAddPet = {}, navToProfile = { backStack.add(Routes.Profile) }) }
            entry<Routes.Profile> {
                ProfileScreen(
                    navToHome = { backStack.removeLastOrNull() },
                    onLogOut = {
                        FirebaseAuth.getInstance().signOut()
                        backStack.clear()
                        backStack.add(Routes.Login)
                    }
                )
            }
        }
    )
}