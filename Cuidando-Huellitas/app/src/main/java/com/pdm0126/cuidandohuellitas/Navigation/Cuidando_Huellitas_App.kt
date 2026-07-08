package com.pdm0126.cuidandohuellitas.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.cuidandohuellitas.Screens.AddPets.AddPet
import com.pdm0126.cuidandohuellitas.Screens.Historial.HistorialScreen
import com.pdm0126.cuidandohuellitas.Screens.MainScreen.MainScreen
import com.pdm0126.cuidandohuellitas.Screens.Pet_Info.Pet_Info
import com.pdm0126.cuidandohuellitas.Screens.Profile.ProfileScreen
import com.pdm0126.cuidandohuellitas.Screens.Profile.ProfileViewModel

@Composable
fun Cuidando_Huellitas_App() {
    val backStack = rememberNavBackStack(Routes.MainScreen)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Routes.MainScreen> {
                MainScreen(
                    navToPetInfo = { petId ->
                        backStack.add(Routes.PetInfo(petId = petId))
                    },
                    navToAddPet = { backStack.add(Routes.AddPet) },
                    navToProfile = { backStack.add(Routes.Profile) }
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
                    navToHome = {
                        backStack.add(Routes.MainScreen)
                    }
                )
            }
            entry<Routes.Profile> {
                ProfileScreen(
                    navToHome = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}