package com.pdm0126.cuidandohuellitas.Data.Model

import androidx.compose.ui.text.font.FontVariation.weight
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import kotlinx.serialization.Serializable

data class Pet(
    val id: String = "",
    val userId: String = "",
    val photoUrl: String = "",
    val name: String = "",
    val type: String = "",
    val age: String = "",
    val weight: String = ""
)