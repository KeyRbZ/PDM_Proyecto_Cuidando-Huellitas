package com.pdm0126.cuidandohuellitas.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.cuidandohuellitas.CuidandoHuellitasApplication
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecoveryUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmailSent: Boolean = false
)

class RecoveryViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _state = MutableStateFlow(RecoveryUiState())
    val state: StateFlow<RecoveryUiState> = _state.asStateFlow()

    fun onEmailChange(value: String) = _state.update { it.copy(email = value, errorMessage = null) }

    fun onSendClick() {
        val current = _state.value
        if (current.email.isBlank()) {
            _state.update { it.copy(errorMessage = "Ingresa tu correo electrónico") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            authRepository.sendPasswordReset(current.email).fold(
                onSuccess = { _state.update { it.copy(isLoading = false, isEmailSent = true) } },
                onFailure = { e -> _state.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al enviar instrucciones") } }
            )
        }
    }

    companion object {
        fun factory(repository: AuthRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = RecoveryViewModel(repository) as T
        }
    }
}

@Composable
fun RecoveryScreen(onGoToLogin: () -> Unit) {
    val context = LocalContext.current
    val app = context.applicationContext as CuidandoHuellitasApplication
    val viewModel: RecoveryViewModel = viewModel(factory = RecoveryViewModel.factory(app.authRepository))
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { onGoToLogin() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(4.dp))
                Text("Volver", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(48.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Recuperar Contraseña",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Ingresa tu correo electrónico y te enviaremos instrucciones para restablecer tu contraseña.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        ),
                        lineHeight = 20.sp
                    )

                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        placeholder = { Text("Correo electrónico", color = Color.Gray) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEFF5F7),
                            unfocusedContainerColor = Color(0xFFEFF5F7),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    state.errorMessage?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                    if (state.isEmailSent) {
                        Spacer(Modifier.height(8.dp))
                        Text("¡Instrucciones enviadas! Revisa tu correo.", color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(Modifier.height(24.dp))

                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Button(
                            onClick = viewModel::onSendClick,
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Enviar Instrucciones", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}