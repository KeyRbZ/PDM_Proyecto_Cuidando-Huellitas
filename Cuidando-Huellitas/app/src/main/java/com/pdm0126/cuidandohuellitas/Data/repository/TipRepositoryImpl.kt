package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.Model.Tip
import com.pdm0126.cuidandohuellitas.Data.remote.api.Tips.TipsDto
import com.pdm0126.cuidandohuellitas.Data.remote.api.Tips.toModel
import com.pdm0126.cuidandohuellitas.Data.remote.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TipRepositoryImpl: TipRepository {
    override suspend fun getTips(): Result<List<Tip>> {
        try{
            val response: List<TipsDto> = KtorClient.client.get("Consejos").body()
            return Result.success(response.map{resDTO -> resDTO.toModel()})
        }catch(e: Exception){
            return Result.failure(e)
        }
    }
}