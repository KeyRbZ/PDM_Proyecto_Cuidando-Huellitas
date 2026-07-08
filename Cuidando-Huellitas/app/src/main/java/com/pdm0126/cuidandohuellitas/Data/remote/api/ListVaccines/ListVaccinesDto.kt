package com.pdm0126.cuidandohuellitas.Data.remote.api.ListVaccines

import com.pdm0126.cuidandohuellitas.Data.Model.ListVaccines
import kotlin.Int

data class ListVaccinesDto(
    val id: Int,
    val nameVac: String,
    val mascotType: List<String>
)

fun ListVaccinesDto.toModel(): ListVaccines {
    return ListVaccines (
        id = id,
        nameVac = nameVac,
        mascotType = mascotType
    )
}