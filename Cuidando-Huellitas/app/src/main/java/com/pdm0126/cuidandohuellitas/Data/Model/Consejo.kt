package com.pdm0126.cuidandohuellitas.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class Consejo(
    val id: Int,
    val title: String,
    val description: String,
    val type: List<String>,
    val category: String,
)
