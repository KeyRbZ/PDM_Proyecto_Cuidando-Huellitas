package com.pdm0126.cuidandohuellitas.Data.repository

data class ProfileData(
    val name: String = "",
    val email: String = "",
    val avatar: String = "",
    val totalMascotas: Int = 0
)

interface ProfileInterface {
    suspend fun getProfile(): Result<ProfileData>
    suspend fun updateAvatar(avatar: String): Result<Unit>
}