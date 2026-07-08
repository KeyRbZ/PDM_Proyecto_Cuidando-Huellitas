package com.pdm0126.cuidandohuellitas.Data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.database.dao.PetsDao
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import com.pdm0126.cuidandohuellitas.Data.database.entities.toDomain
import com.pdm0126.cuidandohuellitas.Data.database.entities.toEntity
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.Pet.PetsFirestoreDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.storage.StorageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class PetsRepositoryImpl(
    private val petsDao: PetsDao,
    private val petsFirestoreDao: PetsFirestoreDao,
    private val storageDao: StorageDao,
    private val context: Context
) : PetInterface {

    //id Temporal
    private val tempUserId = "usuario_temporal_123"

    //Firebase.auth.currentUser?.uid devolverá el ID real.
    private fun getCurrentUserId(): String {
        return Firebase.auth.currentUser?.uid ?: tempUserId
    }

    override suspend fun addPet(
        photoUri: Uri?,
        name: String,
        type: String,
        age: String,
        weight: String
    ): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
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
            petsDao.addPet(newPet.toEntity())
            Log.d(
                "FirebaseSuccess",
                "Mascota enviada correctamente a Firestore con ID de usuario: $userId"
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error al añadir mascota: ${e.message}", e)
            Result.failure(e)
        }
    }

    override fun getPets(): Flow<List<Pet>> {
        val userId = getCurrentUserId()
        return petsDao.getPetsByUser(userId)
            .map { list: List<PetEntity> -> list.map { it.toDomain() } }
    }

    override suspend fun getPetById(petId: String): Result<Pet> {
        return try {
            val pet = petsDao.getPetById(petId)?.toDomain()
                ?: return Result.failure(Exception("Mascota no encontrada"))
            Result.success(pet)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePet(petId: String): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            petsFirestoreDao.deletePet(petId)
            Log.d(
                "FirebaseSuccess",
                "Mascota eliminada correctamente de Firestore con ID de usuario: $userId"
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error al eliminar mascota: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun syncPets(): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            Log.d("FirebaseSync", "Iniciando sincronización para el usuario: $userId")

            //obtener los datos más recientes de Firestore
            val remotePets = petsFirestoreDao.getPets()
            Log.d("FirebaseSync", "Se encontraron ${remotePets.size} mascotas en la nube")

            //se eliminan las mascotas locales de este usuario antes de re-insertar.
            //si algo se borró en Firebase, al sincronizar se borrará de la App.
            petsDao.clearPetsByUser(userId)

            //Re-poblar la base de datos local con lo que hay en Firestore
            remotePets.forEach { petsDao.addPet(it.toEntity()) }

            Log.d("FirebaseSync", "Sincronización completada con éxito")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error en syncPets: ${e.message}", e)
            Result.failure(e)
        }
    }
}
