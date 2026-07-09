package com.pdm0126.cuidandohuellitas.Data.remote.api.Consejo

import com.pdm0126.cuidandohuellitas.Data.Model.Consejo
import kotlinx.serialization.Serializable

@Serializable
data class ConsejoDto(
    val id: Int,
    val title: String,
    val description: String,
    val type: List<String>,
    val category: String,
    )

fun ConsejoDto.toModel(): Consejo {
    return Consejo(
        id = id,
        title = title,
        description = description,
        type = type,
        category = category,
    )
}
