package com.pdm0126.cuidandohuellitas.Navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes: NavKey {

    @Serializable
    data object AddPet : Routes()
    @Serializable
    data object PetType : Routes()
    @Serializable
    data class PetInfo(val petId: String) : Routes()
    @Serializable
    data object MainScreen : Routes()
    @Serializable
    data object Historial : Routes()
    @Serializable
    data object Tips: Routes()
}