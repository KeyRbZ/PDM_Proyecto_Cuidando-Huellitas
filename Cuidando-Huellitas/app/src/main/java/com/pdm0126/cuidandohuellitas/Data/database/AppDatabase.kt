package com.pdm0126.cuidandohuellitas.Data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pdm0126.cuidandohuellitas.Data.database.dao.PetsDao
import com.pdm0126.cuidandohuellitas.Data.database.dao.VaccinesDao
import com.pdm0126.cuidandohuellitas.Data.database.entities.PetEntity

@Database(entities = [PetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petsDao(): PetsDao
    abstract fun vaccineDao(): VaccinesDao
}