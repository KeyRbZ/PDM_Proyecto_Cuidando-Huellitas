package com.pdm0126.cuidandohuellitas.Data

import android.content.Context
import com.pdm0126.cuidandohuellitas.Data.database.AppDatabase
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.Pet.PetsFirestoreDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.Vaccines.VaccinesFirestoreDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.storage.StorageDao
import com.pdm0126.cuidandohuellitas.Data.repository.PetInterface
import com.pdm0126.cuidandohuellitas.Data.repository.PetsRepositoryImpl
import com.pdm0126.cuidandohuellitas.Data.repository.VaccineRepository
import com.pdm0126.cuidandohuellitas.Data.repository.VaccineRepositoryImpl

class AppProvider(private val db: AppDatabase,
                  private val context: Context
) {
    fun ProvideVaccineRepository(): VaccineRepository{
        return VaccineRepositoryImpl(
            vaccinesDao = db.vaccineDao(),
            VaccinesFirestoreDao = VaccinesFirestoreDao(),
            context = context
        )
    }
    fun providePetRepository(): PetInterface {
        return PetsRepositoryImpl(
            petsDao = db.petsDao(),
            petsFirestoreDao = PetsFirestoreDao(),
            storageDao = StorageDao(),
            context = context
        )
    }
}