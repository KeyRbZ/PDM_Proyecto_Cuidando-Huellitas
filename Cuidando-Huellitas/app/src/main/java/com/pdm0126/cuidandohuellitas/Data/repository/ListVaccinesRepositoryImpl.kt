package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.Model.ListVaccines
import com.pdm0126.cuidandohuellitas.Data.remote.api.KtorClient
import com.pdm0126.cuidandohuellitas.Data.remote.api.ListVaccines.ListVaccinesDto
import com.pdm0126.cuidandohuellitas.Data.remote.api.ListVaccines.toModel
import io.ktor.client.call.body
import io.ktor.client.request.get

class ListVaccinesRepositoryImpl: ListVaccinesRepository {
    override suspend fun getListVaccines(): Result<List<ListVaccines>> {
        try{
            val response: List<ListVaccinesDto> = KtorClient.client.get("Vacunas").body()
            return Result.success(response.map{resDTO -> resDTO.toModel()})
        }catch(e: Exception){
            return Result.failure(e)
        }
    }
}