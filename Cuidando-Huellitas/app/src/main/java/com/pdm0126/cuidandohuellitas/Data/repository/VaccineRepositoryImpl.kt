package com.pdm0126.cuidandohuellitas.Data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.Model.Vaccine
import com.pdm0126.cuidandohuellitas.Data.database.dao.VaccinesDao
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import com.pdm0126.cuidandohuellitas.Data.database.entities.VaccinesEntity
import com.pdm0126.cuidandohuellitas.Data.database.entities.toDomain
import com.pdm0126.cuidandohuellitas.Data.database.entities.toEntity
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.Vaccines.VaccinesFirestoreDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class VaccineRepositoryImpl(
    private val vaccinesDao: VaccinesDao,
    private val VaccinesFirestoreDao: VaccinesFirestoreDao,
    private val context: Context
): VaccineRepository
{
    private val tempUserId = "usuario_temporal_123"

    //Firebase.auth.currentUser?.uid devolverá el ID real.
    private fun getCurrentUserId(): String {
        return Firebase.auth.currentUser?.uid ?: tempUserId
    }

    override suspend fun addVaccine(
        idMascot: String,
        nameVac: String,
        startDate: Long,
        interval: Int
    ): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            val vaccineId = UUID.randomUUID().toString()

            val newVaccine = Vaccine(
                id = vaccineId,
                idMascot = idMascot,
                userId = userId,
                nameVac = nameVac,
                startDate = startDate,
                interval = interval
            )

            VaccinesFirestoreDao.addVaccine(newVaccine,idMascot)
            vaccinesDao.addVaccine(newVaccine.toEntity())
            Log.d("FirebaseSuccess", "Vacuna almacenada correctamente a Firestore con ID de mascota: $idMascot")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error al añadir vacuna: ${e.message}", e)
            Result.failure(e)
        }
    }

    override fun getVaccinebyUser(): Flow<List<Vaccine>> {
        val userId = getCurrentUserId()
        return vaccinesDao.getVaccinesByUser(userId)
            .map { list: List<VaccinesEntity> -> list.map { it.toDomain() } }
    }
    override fun getVaccines(idMascot: String): Flow<List<Vaccine>> {
        return vaccinesDao.getVaccinesByUser(idMascot)
            .map { list: List<VaccinesEntity> -> list.map { it.toDomain() } }
    }

    override suspend fun syncVaccines(idMascot: String): Result<Unit> {
        return try {
            val userId = getCurrentUserId()
            Log.d("FirebaseSync", "Iniciando sincronización para el usuario: $userId")

            //obtener los datos más recientes de Firestore
            val remoteVaccine = VaccinesFirestoreDao.getvaccines(idMascot)
            Log.d("FirebaseSync", "Se encontraron ${remoteVaccine.size} vacunas en la nube")

            //se eliminan las mascotas locales de este usuario antes de re-insertar.
            //si algo se borró en Firebase, al sincronizar se borrará de la App.
            vaccinesDao.clearVaccinesByMascot(idMascot)

            //Re-poblar la base de datos local con lo que hay en Firestore
            remoteVaccine.forEach { vaccinesDao.addVaccine(it.toEntity()) }

            Log.d("FirebaseSync", "Sincronización completada con éxito")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseError", "Error en syncPets: ${e.message}", e)
            Result.failure(e)
        }
    }
}