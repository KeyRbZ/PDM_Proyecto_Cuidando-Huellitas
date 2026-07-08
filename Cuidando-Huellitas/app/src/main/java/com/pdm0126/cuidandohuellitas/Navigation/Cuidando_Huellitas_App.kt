package com.pdm0126.cuidandohuellitas.Navigation

import com.pdm0126.cuidandohuellitas.Navigation.Routes
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

import com.pdm0126.cuidandohuellitas.Screens.AddPets.AddPet
import com.pdm0126.cuidandohuellitas.Screens.Historial.HistorialScreen
import com.pdm0126.cuidandohuellitas.Screens.MainScreen.MainScreen
import com.pdm0126.cuidandohuellitas.Screens.Pet_Info.Pet_Info
import com.pdm0126.cuidandohuellitas.Screens.Profile.ProfileScreen
import com.pdm0126.cuidandohuellitas.ui.LoginScreen
import com.pdm0126.cuidandohuellitas.ui.RecoveryScreen
import com.pdm0126.cuidandohuellitas.ui.RegisterScreen
import com.pdm0126.cuidandohuellitas.ui.SplashScreen


@Composable
fun Cuidando_Huellitas_App(){
    val backStack = rememberNavBackStack(Routes.MainScreen)
    val currentRoute = backStack.last()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Routes.MainScreen> {
                MainScreen(
                    currentRoute = currentRoute as Routes,
                    navToPetInfo = { petId -> backStack.add(Routes.PetInfo(petId = petId)) },
                    navToAddPet = { backStack.add(Routes.AddPet) },
                    //navToReminders = { backStack.add(Routes.Reminders) },
                    //navToTips = { backStack.add(Routes.Tips) },
                    navToProfile = { backStack.add(Routes.Profile) }
                )
            }
            entry<Routes.PetInfo> { key ->
                Pet_Info(
                    navToHome = { backStack.add(Routes.MainScreen) },
                    navBack = { backStack.removeLastOrNull() },
                    navToHistorial = { backStack.add(Routes.Historial) },
                    petId = key.petId
                )
            }
            entry<Routes.PetTips> {
//                PetTipsScreen(
//                    navBack = { backStack.removeLastOrNull() },
//                    navToHome = {
//                        backStack.add(Routes.MainScreen)
//                    }
//                )
            }
            entry<Routes.AddPet> { key ->
                AddPet(
                    navBack = { backStack.removeLastOrNull() },
                    navToHome = {
                        backStack.add(Routes.MainScreen)
                    }
                )
            }
            entry<Routes.Historial> { key ->
                HistorialScreen(
                    currentRoute = currentRoute as Routes,
                    navBack = { backStack.removeLastOrNull() },
                    petId = "",
                    navToHome = {
                        backStack.add(Routes.MainScreen)
                    }
                )
            }
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
                        backStack.add(Routes.Home)
                    },
                    onGoToRegister = { backStack.add(Routes.Register) },
                    onGoToRecovery = { backStack.add(Routes.Recovery) }
                )
            }
            entry<Routes.Register> {
                RegisterScreen(
                    onRegisterSuccess = {
                        backStack.removeLastOrNull()
                        backStack.add(Routes.Home)
                    },
                    onGoToLogin = { backStack.removeLastOrNull() }
                )
            }
            entry<Routes.Recovery> {
                RecoveryScreen(onGoToLogin = { backStack.removeLastOrNull() })
            }
            entry<Routes.Profile>{
                ProfileScreen(
                    currentRoute = currentRoute as Routes,
                    navToHome = { backStack.add(Routes.MainScreen) }
                )
            }

        }
    )
}
//
//
//fun RankeUCA_App() {
//    val backStack = rememberNavBackStack(Routes.Question)
//
//    NavDisplay(
//        backStack = backStack,
//        onBack = { backStack.removeLastOrNull() },
//        entryProvider = entryProvider {
//            entry<Routes.Question> {
//                QuestionsScreen(
//                    onQuestionClick = { questionId ->
//                        backStack.add(Routes.Options(questionId))
//                    }
//                )
//            }
//            entry<Routes.Options> { key ->
//                OptionsScreen(
//                    questionId = key.questionId,
//                    navigateBack = { backStack.removeLastOrNull() }
//                )
//            }
//        }
//    )
//}
//
//class RankeUCA_Application : Application(){
//    val appProvider by lazy { AppProvider(this) }
//}