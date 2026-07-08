package com.pdm0126.cuidandohuellitas.Data.Model


data class Vaccine(
    val id : String = "",
    val userId : String = "",
    val idMascot: String = "",
    val nameVac: String = "",
    val startDate: Long?,
    val interval: Int?
)