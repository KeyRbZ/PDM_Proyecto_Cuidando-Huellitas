package com.pdm0126.cuidandohuellitas.Data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdm0126.cuidandohuellitas.Data.database.entities.VaccinesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVaccine(vaccine: VaccinesEntity)

    @Query("SELECT * FROM vaccines WHERE userId = :userId")
    fun getVaccinesByUser(userId: String): Flow<List<VaccinesEntity>>

    @Query("SELECT * FROM vaccines WHERE idMascot = :mascotId")
    fun getVaccinesByMascot(mascotId: String): Flow<List<VaccinesEntity>>

    @Query("SELECT * FROM vaccines WHERE id = :vaccineId")
    suspend fun getVaccineById(vaccineId: String): VaccinesEntity?

    @Delete
    suspend fun deleteVaccine(vaccine: VaccinesEntity)

    @Query("DELETE FROM vaccines WHERE idMascot = :mascotId")
    suspend fun clearVaccinesByMascot(mascotId: String)
}