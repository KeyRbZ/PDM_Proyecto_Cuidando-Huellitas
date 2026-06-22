package com.pdm0126.cuidandohuellitas.Navigation

import com.pdm0126.cuidandohuellitas.Navigation.Routes
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

import com.pdm0126.cuidandohuellitas.Screens.AddPets.AddPet


@Composable
fun Cuidando_Huellitas_App(){
    val backStack = rememberNavBackStack(Routes.AddPet)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Routes.AddPet> { key ->
                AddPet()
                //(
                    //navBack = { backStack.removeLastOrNull() },
                //)
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