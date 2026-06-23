package com.pdm0126.cuidandohuellitas.Screens.Pet_Info

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

class Pet_Info_ViewModel(
    private val petInterface: PetInterface
) : ViewModel() {

    private val _pet = MutableStateFlow<Pet?>(null)
    val pet = _pet.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getPetDetails(petId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            // Llamada al repositorio que devuelve un Result<Pet>
            petInterface.getPetById(petId)
                .onSuccess { petFound ->
                    _pet.value = petFound
                    _isLoading.value = false
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al cargar los datos"
                    _isLoading.value = false
                }
        }
    }
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as CuidandoHuellitasApplication
                Pet_Info_ViewModel(app.appProvider.providePetRepository())
            }
        }
    }
}
