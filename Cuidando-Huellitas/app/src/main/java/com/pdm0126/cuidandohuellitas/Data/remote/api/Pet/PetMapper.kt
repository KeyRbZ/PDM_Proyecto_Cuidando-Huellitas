package com.pdm0126.cuidandohuellitas.Data.remote.api.Pet

import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.remote.api.Pet.dto.PetDto

fun PetDto.toDomain() = Pet(
    id = id,
    userId = userId,
    photoUrl= photoUrl,
    name = name,
    type = type,
    age = age,
    weight = weight
)


fun PetDto.toDto() = PetDto(
    id = id,
    userId = userId,
    photoUrl = photoUrl,
    name = name,
    type = type,
    age = age,
    weight = weight
)