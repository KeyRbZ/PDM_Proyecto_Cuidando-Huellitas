package com.pdm0126.cuidandohuellitas.Data.remote.api.Tips

import com.pdm0126.cuidandohuellitas.Data.Model.Tip
import kotlinx.serialization.Serializable

@Serializable
data class TipsDto(
    val id: Int,
    val title: String,
    val description: String,
    val type: List<String>,
    val category: String,
    )

fun TipsDto.toModel(): Tip {
    return Tip(
        id = id,
        title = title,
        description = description,
        type = type,
        category = category,
    )
}
