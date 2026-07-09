// C:/Users/Sandra Garcia/Desktop/PDM_Proyecto_Cuidando-Huellitas/Cuidando-Huellitas/app/src/main/java/com/pdm0126/cuidandohuellitas/Data/remote/firebase/Pet/PetFirestoreDao.kt

package com.pdm0126.cuidandohuellitas.Data.remote.firebase.Pet

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class PetsFirestoreDao {
private val db = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun addPet(pet: Pet) { //recibe Pet en lugar de parámetros separados
        val petMap = hashMapOf(
            "id" to pet.id,
            "userId" to pet.userId,
            "name" to pet.name,
            "type" to pet.type,
            "age" to pet.age,
            "weight" to pet.weight,
            "photoUrl" to pet.photoUrl
        )

        db.collection("pets")
            .document(pet.id)  // usa el id del Pet para el documento
            .set(petMap)
            .await()
    }

    suspend fun getPets(): List<Pet> {
        val userId = auth.currentUser?.uid ?: return emptyList()
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

    suspend fun getPetsByUser(): List<PetEntity> {
        val userId = auth.currentUser?.uid ?: return emptyList()

        return db.collection("pets")
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects(PetEntity::class.java)
    }
}