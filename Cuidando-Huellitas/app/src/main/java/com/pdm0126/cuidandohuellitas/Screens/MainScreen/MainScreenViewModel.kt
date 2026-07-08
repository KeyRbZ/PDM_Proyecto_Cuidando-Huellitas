package com.pdm0126.cuidandohuellitas.Screens.MainScreen

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

class MainScreenViewModel(
    private val petInterface: PetInterface
) : ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            petInterface.syncPets()//sincroniza
            _isLoading.value = false //falso el loading
        }
        getPets()
    }

    fun getPets() {
        viewModelScope.launch {
            _error.value = null
            petInterface.getPets()
                .collect { list ->
                    _pets.value = list
                }
        }
    }

    fun refreshPets() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _error.value = null
            petInterface.syncPets()
                .onFailure { e ->
                    _error.value = "Error al sincronizar: ${e.message}"
                }
            _isRefreshing.value = false
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as CuidandoHuellitasApplication
                MainScreenViewModel(app.appProvider.providePetRepository())
            }
        }
    }
}