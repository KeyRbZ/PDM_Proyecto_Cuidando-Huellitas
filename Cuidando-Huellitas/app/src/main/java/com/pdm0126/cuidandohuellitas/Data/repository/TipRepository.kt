package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.Model.Tip

interface TipRepository {
    suspend fun getTips(): Result<List<Tip>>
}