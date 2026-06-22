package com.pdm0126.cuidandohuellitas.Navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes: NavKey {

    @Serializable
    data object AddPet : Routes()
    @Serializable
    data object PetType : Routes()

}