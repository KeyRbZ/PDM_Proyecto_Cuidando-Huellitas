package com.pdm0126.cuidandohuellitas.Data.remote.api

import com.pdm0126.cuidandohuellitas.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    val BASE_URL = "https://aywbxgcetkxtoypkeqtf.supabase.co/rest/v1/"
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        defaultRequest {
            url(BASE_URL)
            header("apikey", BuildConfig.API_Key)
            header(HttpHeaders.Accept, "application/json")
        }
    }
}