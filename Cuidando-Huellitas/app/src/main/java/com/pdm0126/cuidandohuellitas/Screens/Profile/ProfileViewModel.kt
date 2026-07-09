package com.pdm0126.cuidandohuellitas.Screens.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.auth.FirebaseAuth
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

    private var authListener: FirebaseAuth.AuthStateListener? = null

    init {
        getProfile()
        setupAuthListener()
    }

    private fun setupAuthListener() {
        authListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                getProfile()
            } else {
                _profile.value = ProfileData()
                _isLoading.value = false
            }
        }
        FirebaseAuth.getInstance().addAuthStateListener(authListener!!)
    }

    fun getProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null


            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                _profile.value = ProfileData()
                _isLoading.value = false
                return@launch
            }

            profileInterface.getProfile()
                .onSuccess {
                    _profile.value = it
                }
                .onFailure {
                    _profile.value = ProfileData(
                        name = "Usuario",
                        email = currentUser.email ?: "",
                        avatar = "🐾",
                        totalMascotas = 0
                    )
                    _error.value = null
                }
            _isLoading.value = false
        }
    }

    fun updateAvatar(avatar: String) {
        viewModelScope.launch {
            profileInterface.updateAvatar(avatar)
                .onSuccess {
                    _profile.value = _profile.value.copy(avatar = avatar)
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al guardar el avatar"
                }
        }
    }


    override fun onCleared() {
        super.onCleared()
        authListener?.let {
            FirebaseAuth.getInstance().removeAuthStateListener(it)
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