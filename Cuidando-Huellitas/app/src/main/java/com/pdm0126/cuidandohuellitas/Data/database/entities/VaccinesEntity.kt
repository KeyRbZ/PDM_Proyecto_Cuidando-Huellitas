package com.pdm0126.cuidandohuellitas.Data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.Model.Vaccine
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
@Entity(tableName = "vaccines")
data class VaccinesEntity(
    @PrimaryKey val id : String,
    val userId : String,
    val idMascot: String,
    val nameVac: String,
    val startDate: Long? = null,
    val interval: Int? = null
)
fun VaccinesEntity.toDomain() = Vaccine(
    id = id,
    userId = userId,
    idMascot = idMascot,
    nameVac = nameVac,
    startDate=startDate,
    interval= interval
)

fun Vaccine.toEntity() = VaccinesEntity(
    id = id,
    userId = userId,
    idMascot = idMascot,
    nameVac = nameVac,
    startDate=startDate,
    interval= interval
)