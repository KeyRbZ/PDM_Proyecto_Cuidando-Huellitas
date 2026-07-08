package com.pdm0126.cuidandohuellitas.Screens.AddPets

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.cuidandohuellitas.CuidandoHuellitasApplication
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.repository.PetInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddPetViewModel(
    private val petInterface: PetInterface
) : ViewModel() {

    private val _pet = MutableStateFlow<Pet?>(null)
    val pet = _pet.asStateFlow()


    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso = _guardadoExitoso.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()

    fun addPet(
        name: String,
        type: String,
        age: String,
        weight: String,
        photoUri: Uri?) {
        viewModelScope.launch {
            _error.value = null
            _cargando.value = true
            petInterface.addPet(photoUri, name, type, age, weight)
                .onSuccess {
                    _cargando.value = false
                    _guardadoExitoso.value = true
                    petInterface.syncPets()
                }
                .onFailure { e ->
                    _cargando.value = false
                    _error.value = e.message
                }
        }
    }

    fun resetState() {
        _guardadoExitoso.value = false
        _error.value = null
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as CuidandoHuellitasApplication
                AddPetViewModel(app.appProvider.providePetRepository())
            }
        }
    }
}