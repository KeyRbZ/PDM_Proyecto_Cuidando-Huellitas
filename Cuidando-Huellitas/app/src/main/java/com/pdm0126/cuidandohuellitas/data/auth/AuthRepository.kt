package com.pdm0126.cuidandohuellitas.data.auth

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun sendPasswordReset(email: String): Result<Unit>
    fun logout()
    fun isUserLoggedIn(): Boolean
}