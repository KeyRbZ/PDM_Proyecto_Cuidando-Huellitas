package com.pdm0126.cuidandohuellitas.Data.repository

import com.pdm0126.cuidandohuellitas.Data.remote.firebase.User.UsersFirestoreDao

class ProfileRepositoryImpl(
    private val usersFirestoreDao: UsersFirestoreDao
) : ProfileInterface {

    override suspend fun getProfile(): Result<ProfileData> {
        return try {
            val userMap = usersFirestoreDao.getUser()
            val totalPets = usersFirestoreDao.countPetsByUser()
            Result.success(
                ProfileData(
                    name = userMap?.get("name") as? String ?: "",
                    email = userMap?.get("email") as? String ?: "",
                    avatar = userMap?.get("avatar") as? String ?: "",
                    totalMascotas = totalPets
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAvatar(avatar: String): Result<Unit> {
        return try {
            usersFirestoreDao.updateAvatar(avatar)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}