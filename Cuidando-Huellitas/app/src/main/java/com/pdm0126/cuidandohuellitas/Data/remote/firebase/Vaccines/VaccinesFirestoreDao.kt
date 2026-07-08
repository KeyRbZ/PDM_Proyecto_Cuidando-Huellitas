package com.pdm0126.cuidandohuellitas.Data.remote.firebase.Vaccines

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.Model.Vaccine
import kotlinx.coroutines.tasks.await

class VaccinesFirestoreDao {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val tempUserId = "usuario_temporal_123"

    suspend fun addVaccine(vaccine: Vaccine, idMascot:String) {
        val vaccineMap = hashMapOf(
            "id" to vaccine.id,
            "idMascot" to idMascot,
            "userId" to (auth.currentUser?.uid ?: tempUserId),
            "nameVaccine" to vaccine.nameVac,
            "interval" to vaccine.interval,
        )

        db.collection("vaccine")
            .document(vaccine.id)
            .set(vaccineMap)
            .await()
    }
    suspend fun getvaccines(idMascot:String): List<Vaccine> {
        val userId = auth.currentUser?.uid ?: tempUserId
        return db.collection("vaccine")
            .whereEqualTo("userId", userId)
            .whereEqualTo("idMascot", idMascot)
            .get()
            .await()
            .documents.map { doc ->
                Vaccine(
                    id = doc.getString("id") ?: "",
                    userId = doc.getString("userId") ?: "",
                    nameVac = doc.getString("nameVaccine") ?: "",
                    startDate = doc.getLong("startDate"),
                    interval = doc.getLong("interval")?.toInt(),
                )
            }
    }
}