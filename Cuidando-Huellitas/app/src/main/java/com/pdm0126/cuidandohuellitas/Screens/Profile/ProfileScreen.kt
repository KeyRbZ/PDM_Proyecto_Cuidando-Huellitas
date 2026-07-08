package com.pdm0126.cuidandohuellitas.Screens.Profile

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.pdm0126.cuidandohuellitas.Components.BottomNavigationBar
import java.io.ByteArrayOutputStream

private val avatarOptions = listOf("🐶", "🐱", "🐰", "🐦", "🐹", "🐟")

private fun bitmapToBase64(bitmap: Bitmap): String {
    val resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
    val stream = ByteArrayOutputStream()
    resized.compress(Bitmap.CompressFormat.JPEG, 40, stream)
    return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
}

private fun decodeAvatarBitmap(avatar: String): Bitmap? {
    if (avatar.length < 100) return null
    return try {
        val bytes = Base64.decode(avatar, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun ProfileScreen(
    navToHome: () -> Unit,
    onLogOut: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var editingAvatar by remember { mutableStateOf(false) }
    var pendingImage by remember { mutableStateOf<Any?>(null) }
    var showImageMenu by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> if (uri != null) pendingImage = uri }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap -> if (bitmap != null) pendingImage = bitmap }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> if (isGranted) cameraLauncher.launch() }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = 3,
                navToHome = { navToHome() },
                navToReminders = {},
                navToTips = {},
                navToProfile = {}
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            // Avatar tocable para editar
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable { editingAvatar = !editingAvatar },
                contentAlignment = Alignment.Center
            ) {
                if (pendingImage != null) {
                    AsyncImage(
                        model = pendingImage,
                        contentDescription = "Nueva foto",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    val bitmap = remember(profile.avatar) { decodeAvatarBitmap(profile.avatar) }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = if (profile.avatar.isNotEmpty()) profile.avatar else "🐾",
                            fontSize = 40.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))
            Text("Toca para cambiar", fontSize = 11.sp, color = Color.Gray)

            Spacer(Modifier.height(8.dp))
            Text(profile.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Amante de mascotas 🐾", color = Color.Gray)

            // Selector de avatar/foto
            if (editingAvatar) {
                Spacer(Modifier.height(16.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    itemsIndexed(avatarOptions) { _, emoji ->
                        Surface(
                            modifier = Modifier.size(44.dp),
                            shape = CircleShape,
                            color = Color.White,
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.clickable {
                                    viewModel.updateAvatar(emoji)
                                    pendingImage = null
                                    editingAvatar = false
                                }
                            ) {
                                Text(emoji, fontSize = 20.sp)
                            }
                        }
                    }
                    item {
                        Box {
                            Surface(
                                modifier = Modifier.size(44.dp),
                                shape = CircleShape,
                                color = Color.White,
                                border = BorderStroke(1.dp, Color.Gray)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.clickable { showImageMenu = true }
                                ) {
                                    Icon(Icons.Outlined.CameraAlt, contentDescription = "Foto", modifier = Modifier.size(20.dp))
                                }
                            }
                            DropdownMenu(expanded = showImageMenu, onDismissRequest = { showImageMenu = false }) {
                                DropdownMenuItem(
                                    text = { Text("Tomar foto") },
                                    leadingIcon = { Icon(Icons.Outlined.CameraAlt, contentDescription = null) },
                                    onClick = {
                                        showImageMenu = false
                                        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                                        if (granted) cameraLauncher.launch() else permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Elegir de galería") },
                                    leadingIcon = { Icon(Icons.Outlined.PhotoLibrary, contentDescription = null) },
                                    onClick = {
                                        showImageMenu = false
                                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                    }
                                )
                            }
                        }
                    }
                }

                if (pendingImage != null) {
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = {
                        val avatarValue: String = when (val image = pendingImage) {
                            is Uri -> bitmapToBase64(MediaStore.Images.Media.getBitmap(context.contentResolver, image))
                            is Bitmap -> bitmapToBase64(image)
                            else -> profile.avatar
                        }
                        viewModel.updateAvatar(avatarValue)
                        pendingImage = null
                        editingAvatar = false
                    }) {
                        Text("Guardar nueva foto")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Información Personal", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Nombre", fontSize = 12.sp, color = Color.Gray)
                    Text(profile.name)
                    Spacer(Modifier.height(8.dp))
                    Text("Correo", fontSize = 12.sp, color = Color.Gray)
                    Text(profile.email)
                }
            }

            Spacer(Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estadísticas", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(profile.totalMascotas.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Mascotas", fontSize = 12.sp, color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("0", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Vacunas", fontSize = 12.sp, color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("0", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Pendientes", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onLogOut() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}