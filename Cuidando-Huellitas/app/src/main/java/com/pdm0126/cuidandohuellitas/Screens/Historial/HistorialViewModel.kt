package com.pdm0126.cuidandohuellitas.Screens.Historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.cuidandohuellitas.CuidandoHuellitasApplication
import com.pdm0126.cuidandohuellitas.Data.Model.ListVaccines
import com.pdm0126.cuidandohuellitas.Data.Model.Pet
import com.pdm0126.cuidandohuellitas.Data.Model.Vaccine
import com.pdm0126.cuidandohuellitas.Data.repository.ListVaccinesRepository
import com.pdm0126.cuidandohuellitas.Data.repository.ListVaccinesRepositoryImpl
import com.pdm0126.cuidandohuellitas.Data.repository.PetInterface
import com.pdm0126.cuidandohuellitas.Data.repository.TipRepository
import com.pdm0126.cuidandohuellitas.Data.repository.TipRepositoryImpl
import com.pdm0126.cuidandohuellitas.Data.repository.VaccineRepository
import com.pdm0126.cuidandohuellitas.Data.repository.VaccineRepositoryImpl
import com.pdm0126.cuidandohuellitas.Screens.Pet_Info.Pet_Info_ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistorialViewModel(
    private val petInterface: PetInterface,
    private val VaccineRepository: VaccineRepository
) : ViewModel() {


    val listVaccineRepository: ListVaccinesRepository = ListVaccinesRepositoryImpl()
    private val _pet = MutableStateFlow<Pet?>(null)
    val pet = _pet.asStateFlow()

    private val _vaccines = MutableStateFlow<List<Vaccine>>(emptyList())
    val vaccines = _vaccines.asStateFlow()

    private val _listVaccines = MutableStateFlow<List<ListVaccines>>(emptyList())
    val listVaccines = _listVaccines.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getPetHistorial(petId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            petInterface.getPetById(petId)
                .onSuccess { petFound ->
                    _pet.value = petFound
                    _isLoading.value = false
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al cargar los datos"
                    _isLoading.value = false
                }
            VaccineRepository.getVaccines(petId).collect {
                _vaccines.value = it
            }

        }
    }
    fun getlistVaccines(){
        viewModelScope.launch {
            listVaccineRepository.getListVaccines().onSuccess { res ->
                _listVaccines.value = res
            }.onFailure { e ->
                _error.value = e.toString()
            }
        }
    }
    fun addVaccine(
        petId: String,
        nameVac: String,
        startDate: Long,
        interval: Int
    ) {
        viewModelScope.launch {
            VaccineRepository.addVaccine(
                petId,
                nameVac,
                startDate,
                interval
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as CuidandoHuellitasApplication
                HistorialViewModel(app.appProvider.providePetRepository(), app.appProvider.ProvideVaccineRepository())
            }
        }
    }
}
