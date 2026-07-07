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


@Composable
fun Cuidando_Huellitas_App(){
    val backStack = rememberNavBackStack(Routes.MainScreen)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Routes.MainScreen> {
                MainScreen(
                    navToPetInfo = { petId ->  //petId llega del callback
                        backStack.add(Routes.PetInfo(petId = petId))
                    },
                    navToAddPet = { backStack.add(Routes.AddPet) }
                )
            }
            entry<Routes.PetInfo> { key ->
                Pet_Info(
                    navBack = { backStack.removeLastOrNull() },
                    navToHistorial = { backStack.add(Routes.Recordatorio) },
                    petId = key.petId
                )
            }
            entry<Routes.AddPet> { key ->
                AddPet(
                    navBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Routes.Recordatorio> { key ->
                HistorialScreen(
                    navBack = { backStack.removeLastOrNull() },
                    userId = "",
                    navToHome = { backStack.removeLastOrNull() }
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