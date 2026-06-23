package com.pdm0126.cuidandohuellitas.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    val id: String = "",
    val userId: String = "",
    val photoUrl: String = "",
    val name: String = "",
    val type: String = "",
    val age: String = "",
    val weight: String = ""
)
