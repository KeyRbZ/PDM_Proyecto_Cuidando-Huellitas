package com.pdm0126.cuidandohuellitas.Data.repository

import android.net.Uri
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import kotlinx.coroutines.flow.Flow

interface PetInterface {
    suspend fun addPet(photoUri: Uri?, name: String, type: String, age: String, weight: String): Result<Unit>
    fun getPets(): Flow<List<Pet>>
    suspend fun getPetById(petId: String): Result<Pet>
    suspend fun syncPets(): Result<Unit>
    suspend fun deletePet(petId: String): Result<Unit>

}