package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.Model.Consejo
import com.pdm0126.cuidandohuellitas.Data.remote.api.Consejo.ConsejoDto
import com.pdm0126.cuidandohuellitas.Data.remote.api.Consejo.toModel
import com.pdm0126.cuidandohuellitas.Data.remote.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ConsejoRepositoryImpl: ConsejoRepository {
    override suspend fun getConsejos(): Result<List<Consejo>> {
        try{
            val response: List<ConsejoDto> = KtorClient.client.get { "Consejos" }.body()
            return Result.success(response.map{resDTO -> resDTO.toModel()})
        }catch(e: Exception){
            return Result.failure(e)
        }
    }
}