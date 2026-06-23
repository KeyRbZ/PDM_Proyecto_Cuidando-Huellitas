package com.pdm0126.cuidandohuellitas.Data

import android.content.Context
import com.pdm0126.cuidandohuellitas.Data.database.AppDatabase
import com.pdm0126.cuidandohuellitas.Data.remote.api.Pet.PetApiDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.Pet.PetsFirestoreDao
import com.pdm0126.cuidandohuellitas.Data.remote.firebase.storage.StorageDao
import com.pdm0126.cuidandohuellitas.Data.repository.PetInterface
import com.pdm0126.cuidandohuellitas.Data.repository.PetsRepositoryImpl

class AppProvider(private val db: AppDatabase,
                  private val context: Context
) {

    fun providePetRepository(): PetInterface {
        return PetsRepositoryImpl(
            petsDao = db.petsDao(),
            petsFirestoreDao = PetsFirestoreDao(),
            petsApiDao = PetApiDao(),
            storageDao = StorageDao(),
            context = context
        )
    }
}