package com.pdm0126.cuidandohuellitas.Data.repository

import android.net.Uri
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.Model.Vaccine
import kotlinx.coroutines.flow.Flow

interface VaccineRepository {
    suspend fun addVaccine(idMascot: String, nameVac: String,
                           startDate: Long, interval: Int): Result<Unit>
    fun getVaccinebyUser(): Flow<List<Vaccine>>
    fun getVaccines(idMascot: String): Flow<List<Vaccine>>
    suspend fun syncVaccines(idMascot: String): Result<Unit>
}