package com.pdm0126.cuidandohuellitas.Data.remote.api.Pet.dto

data class PetDto(
    val id: String,
    val userId: String,
    val photoUrl: String,
    val name: String,
    val type: String,
    val age: String,
    val weight: String
)
