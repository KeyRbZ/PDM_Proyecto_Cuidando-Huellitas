package com.pdm0126.cuidandohuellitas.Data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPet(pet: PetEntity)

    @Query("SELECT * FROM pets WHERE userId = :userId")
    fun getPetsByUser(userId: String): Flow<List<PetEntity>>

    @Query("SELECT * FROM pets WHERE id = :petId")
    suspend fun getPetById(petId: String): PetEntity?

}