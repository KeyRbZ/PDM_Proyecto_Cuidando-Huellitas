package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.Model.Consejo

interface ConsejoRepository {
    suspend fun getConsejos(): Result<List<Consejo>>
}