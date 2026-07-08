package com.pdm0126.cuidandohuellitas.Screens.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.cuidandohuellitas.CuidandoHuellitasApplication
import com.pdm0126.cuidandohuellitas.Data.repository.ProfileData
import com.pdm0126.cuidandohuellitas.Data.repository.ProfileInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileInterface: ProfileInterface
) : ViewModel() {

    private val _profile = MutableStateFlow(ProfileData())
    val profile = _profile.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            profileInterface.getProfile()
                .onSuccess {
                    _profile.value = it
                }
                .onFailure {
                    _profile.value = ProfileData(
                        name = "maria",
                        email = "maria.garcia@email.com",
                        avatar = "🐱",
                        totalMascotas = 2
                    )
                    _error.value = null
                }
            _isLoading.value = false
        }
    }

    fun updateAvatar(avatar: String) {
        viewModelScope.launch {
            profileInterface.updateAvatar(avatar)
                .onSuccess { _profile.value = _profile.value.copy(avatar = avatar) }
                .onFailure { e -> _error.value = e.message ?: "Error al guardar el avatar" }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as CuidandoHuellitasApplication
                ProfileViewModel(app.appProvider.provideProfileRepository())
            }
        }
    }
}