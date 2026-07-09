package com.pdm0126.cuidandohuellitas.Navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
    // Rutas de la rama auth-screens
    @Serializable data object Splash : Routes()
    @Serializable data object Login : Routes()
    @Serializable data object Register : Routes()
    @Serializable data object Recovery : Routes()

    // Rutas de la rama profile y flujo principal
    @Serializable data object MainScreen : Routes()
    @Serializable data object AddPet : Routes()
    @Serializable data object PetType : Routes()
    @Serializable data class PetInfo(val petId: String) : Routes()
    @Serializable data object Recordatorio : Routes()
    @Serializable data object Profile : Routes()
}