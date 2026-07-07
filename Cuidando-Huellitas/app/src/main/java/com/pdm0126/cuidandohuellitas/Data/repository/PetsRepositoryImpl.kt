package com.pdm0126.cuidandohuellitas.Data.repository

import android.content.Context
import android.net.Uri
import android.util.Log.e
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.database.dao.PetsDao
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import com.pdm0126.cuidandohuellitas.Data.database.entities.toDomain
import com.pdm0126.cuidandohuellitas.Data.database.entities.toEntity
import com.pdm0126.cuidandohuellitas.Data.remote.api.Pet.PetApiDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.Pet.PetsFirestoreDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.storage.StorageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class PetsRepositoryImpl(
    private val petsDao: PetsDao,
    private val petsFirestoreDao: PetsFirestoreDao,
    private val petsApiDao: PetApiDao,
    private val storageDao: StorageDao,
    private val context: Context
) : PetInterface {

    override suspend fun addPet(
        photoUri: Uri?,
        name: String,
        type: String,
        age: String,
        weight: String
    ): Result<Unit> {
        return try {
            val userId = Firebase.auth.currentUser?.uid ?: ""
            val petId = UUID.randomUUID().toString()

            val photoUrl: String = if (photoUri != null) {
                storageDao.uploadPhoto(uri = photoUri, context = context)
            } else ""

            val newPet = Pet(
                id = petId,
                userId = userId,
                name = name,
                type = type,
                age = age,
                weight = weight,
                photoUrl = photoUrl
            )

            petsFirestoreDao.addPet(newPet)
            petsDao.addPet(newPet.toEntity()) //guarda también en Room
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //Room , sincroniza desde Firestore primero
    override suspend fun getPets(): Flow<List<Pet>> {
        val userId = Firebase.auth.currentUser?.uid ?: ""
        return petsDao.getPetsByUser(userId)
            .map { list :List<PetEntity> -> list.map { it.toDomain() } }
    }

    // NUEVO - para Pet_Info_ViewModel
    override suspend fun getPetById(petId: String): Result<Pet> {
        return try {
            val pet = petsDao.getPetById(petId)?.toDomain()
                ?: return Result.failure(Exception("Mascota no encontrada"))
            Result.success(pet)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //sincroniza Firestore y Room al abrir la app
    override suspend fun syncPets(): Result<Unit> {
        return try {
            val remotePets = petsFirestoreDao.getPets()
            remotePets.forEach { petsDao.addPet(it.toEntity()) }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
