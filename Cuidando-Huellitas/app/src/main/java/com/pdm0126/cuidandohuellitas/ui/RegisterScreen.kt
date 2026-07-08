package com.pdm0126.cuidandohuellitas.ui

import androidx.activity.result.launch
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.cuidandohuellitas.CuidandoHuellitasApplication
import com.pdm0126.cuidandohuellitas.data.auth.AuthRepository
import com.pdm0126.cuidandohuellitas.data.auth.UsersFirestoreDao
import com.pdm0126.cuidandohuellitas.ui.theme.AmarilloAvatar
import com.pdm0126.cuidandohuellitas.ui.theme.BordeTextField
import androidx.lifecycle.viewModelScope
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterSuccess: Boolean = false
)

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _state = MutableStateFlow(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state.asStateFlow()

    fun onNameChange(value: String) = _state.update { it.copy(name = value, errorMessage = null) }
    fun onEmailChange(value: String) = _state.update { it.copy(email = value, errorMessage = null) }
    fun onPasswordChange(value: String) = _state.update { it.copy(password = value, errorMessage = null) }
    fun onConfirmPasswordChange(value: String) = _state.update { it.copy(confirmPassword = value, errorMessage = null) }

    fun onRegisterClick(avatarValue: String) {
        val current = _state.value
        if (current.name.isBlank() || current.email.isBlank() || current.password.isBlank()) {
            _state.update { it.copy(errorMessage = "Completa todos los campos") }
            return
        }
        if (current.password != current.confirmPassword) {
            _state.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            return
        }
        val passwordError = validatePasswordStrength(current.password)
        if (passwordError != null) {
            _state.update { it.copy(errorMessage = passwordError) }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            authRepository.register(current.email, current.password).fold(
                onSuccess = {
                    val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
                    if (uid != null) {
                        try {
                            UsersFirestoreDao().saveUser(uid, current.name, avatarValue, current.email)
                        } catch (e: Exception) {
                        }
                    }
                    _state.update { it.copy(isLoading = false, isRegisterSuccess = true) }
                },
                onFailure = { e -> _state.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al registrarse") } }
            )
        }
    }

    private fun validatePasswordStrength(password: String): String? {
        if (password.length < 6) return "La contraseña debe tener mínimo 6 caracteres"
        if (!password.any { it.isUpperCase() }) return "La contraseña debe tener al menos una mayúscula"
        if (!password.any { it.isDigit() }) return "La contraseña debe tener al menos un número"
        if (!password.any { !it.isLetterOrDigit() }) return "La contraseña debe tener al menos un carácter especial (ej. ! ? # @)"
        return null
    }

    companion object {
        fun factory(repository: AuthRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = RegisterViewModel(repository) as T
        }
    }
}

private fun bitmapToBase64(bitmap: Bitmap): String {
    val resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
    val stream = ByteArrayOutputStream()
    resized.compress(Bitmap.CompressFormat.JPEG, 40, stream)
    return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onGoToLogin: () -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as CuidandoHuellitasApplication
    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.factory(app.authRepository))
    val state by viewModel.state.collectAsStateWithLifecycle()

    val avatars = listOf("🐶", "🐱", "🐰", "🐦", "🐹", "🐟")
    var selectedAvatarIndex by remember { mutableStateOf(0) }

    var selectedImage by remember { mutableStateOf<Any?>(null) }
    var showImageMenu by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> if (uri != null) selectedImage = uri }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap -> if (bitmap != null) selectedImage = bitmap }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> if (isGranted) cameraLauncher.launch() }

    LaunchedEffect(state.isRegisterSuccess) {
        if (state.isRegisterSuccess) onRegisterSuccess()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
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

            Spacer(Modifier.height(24.dp))

            Text(
                "Crear Cuenta",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(1.dp, BordeTextField, CircleShape),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        if (selectedImage != null) {
                            AsyncImage(
                                model = selectedImage,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(avatars[selectedAvatarIndex], fontSize = 44.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Selecciona tu avatar o sube tu foto", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(avatars) { index, avatar ->
                    val isSelected = selectedImage == null && index == selectedAvatarIndex
                    Surface(
                        modifier = Modifier
                            .size(50.dp)
                            .border(
                                width = if (isSelected) 3.dp else 0.dp,
                                color = if (isSelected) AmarilloAvatar else Color.Transparent,
                                shape = CircleShape
                            ),
                        shape = CircleShape,
                        color = Color.White
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.clickable {
                                selectedAvatarIndex = index
                                selectedImage = null
                            }
                        ) {
                            Text(avatar, fontSize = 24.sp)
                        }
                    }
                }

                item {
                    Box {
                        Surface(
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = if (selectedImage != null) 3.dp else 0.dp,
                                    color = if (selectedImage != null) AmarilloAvatar else Color.Transparent,
                                    shape = CircleShape
                                ),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.clickable { showImageMenu = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CameraAlt,
                                    contentDescription = "Tomar o elegir foto",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showImageMenu,
                            onDismissRequest = { showImageMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Tomar foto") },
                                leadingIcon = { Icon(Icons.Outlined.CameraAlt, contentDescription = null) },
                                onClick = {
                                    showImageMenu = false
                                    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                        cameraLauncher.launch()
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Elegir de galería") },
                                leadingIcon = { Icon(Icons.Outlined.PhotoLibrary, contentDescription = null) },
                                onClick = {
                                    showImageMenu = false
                                    galleryLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                placeholder = { Text("Nombre completo", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = BordeTextField,
                    unfocusedBorderColor = BordeTextField
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                placeholder = { Text("Correo electrónico", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = BordeTextField,
                    unfocusedBorderColor = BordeTextField
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = { Text("Contraseña (mínimo 6 caracteres)", color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = BordeTextField,
                    unfocusedBorderColor = BordeTextField
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))
            Text(
                "Debe incluir mayúscula, número y símbolo (ej. ?!#@)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                placeholder = { Text("Confirmar contraseña", color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = BordeTextField,
                    unfocusedBorderColor = BordeTextField
                ),
                modifier = Modifier.fillMaxWidth()
            )

            state.errorMessage?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = {
                        val avatarValue: String = when (val image = selectedImage) {
                            is Uri -> {
                                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, image)
                                bitmapToBase64(bitmap)
                            }
                            is Bitmap -> bitmapToBase64(image)
                            else -> avatars[selectedAvatarIndex]
                        }
                        viewModel.onRegisterClick(avatarValue)
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(16.dp))
            TextButton(
                onClick = onGoToLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}