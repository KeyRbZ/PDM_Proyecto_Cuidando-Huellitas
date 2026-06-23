package com.pdm0126.cuidandohuellitas.Data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pdm0126.cuidandohuellitas.Data.Model.Pet

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val photoUrl: String,
    val name: String,
    val type: String,
    val age: String,
    val weight: String
)
fun PetEntity.toDomain() = Pet(
    id = id,
    userId = userId,
    name = name,
    type = type,
    age = age,
    weight = weight,
    photoUrl = photoUrl
)

fun Pet.toEntity() = PetEntity(
    id = id,
    userId = userId,
    name = name,
    type = type,
    age = age,
    weight = weight,
    photoUrl = photoUrl
)