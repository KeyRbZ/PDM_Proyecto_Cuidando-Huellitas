package com.pdm0126.cuidandohuellitas.Screens.TipScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.cuidandohuellitas.Data.repository.TipRepository
import com.pdm0126.cuidandohuellitas.Data.repository.TipRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import com.pdm0126.cuidandohuellitas.Data.Model.Tip
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TipViewModel: ViewModel() {
    val tipsRepository : TipRepository = TipRepositoryImpl()

    private val _tips = MutableStateFlow<List<Tip>>(emptyList())
    val tips = _tips.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        getTips()
    }

    fun getTips(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            tipsRepository.getTips().onSuccess { list ->
                _tips.value = list
            }.onFailure {
                _error.value = "Hola corazon bello, parece que hay un error, vuelve a intentarlo porfavor"
            }
            _loading.value = false
        }
    }
}