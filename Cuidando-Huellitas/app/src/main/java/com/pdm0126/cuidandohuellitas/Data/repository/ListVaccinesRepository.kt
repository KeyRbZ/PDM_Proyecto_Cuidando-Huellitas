package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.Model.ListVaccines

interface ListVaccinesRepository {
    suspend fun getListVaccines(): Result<List<ListVaccines>>
}