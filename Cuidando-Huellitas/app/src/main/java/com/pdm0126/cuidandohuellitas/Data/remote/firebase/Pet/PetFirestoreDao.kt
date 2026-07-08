package com.pdm0126.cuidandohuellitas.Data.remote.firebase.Pet

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import kotlinx.coroutines.tasks.await

class PetsFirestoreDao {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    // Temporal hasta implementar Auth
    private val tempUserId = "usuario_temporal_123"

    suspend fun addPet(pet: Pet) {
        val petMap = hashMapOf(
            "id" to pet.id,
            "userId" to (auth.currentUser?.uid ?: tempUserId),
            "name" to pet.name,
            "type" to pet.type,
            "age" to pet.age,
            "weight" to pet.weight,
            "photoUrl" to pet.photoUrl
        )

        db.collection("pets")
            .document(pet.id)
            .set(petMap)
            .await()
    }

    suspend fun getPets(): List<Pet> {
        val userId = auth.currentUser?.uid ?: tempUserId
        return db.collection("pets")
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .documents.map { doc ->
                Pet(
                    id = doc.getString("id") ?: "",
                    userId = doc.getString("userId") ?: "",
                    name = doc.getString("name") ?: "",
                    type = doc.getString("type") ?: "",
                    age = doc.getString("age")?: "",
                    weight = doc.getString("weight") ?: "",
                    photoUrl = doc.getString("photoUrl") ?: ""
                )
            }
    }

    suspend fun deletePet(petId: String) {
        db.collection("pets")
            .document(petId)
            .delete()
            .await()
    }

    suspend fun getPetsByUser(): List<PetEntity> {
        val userId = auth.currentUser?.uid ?: tempUserId

        return db.collection("pets")
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects(PetEntity::class.java)
    }
}