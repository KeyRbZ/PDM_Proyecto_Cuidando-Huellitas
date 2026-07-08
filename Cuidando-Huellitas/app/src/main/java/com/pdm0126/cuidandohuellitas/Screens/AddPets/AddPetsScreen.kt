package com.pdm0126.cuidandohuellitas.Screens.AddPets

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.pdm0126.cuidandohuellitas.Components.DatePickerDocked
import com.pdm0126.cuidandohuellitas.Components.DatePickerFieldToModal
import com.pdm0126.cuidandohuellitas.Components.LoadingScreen
import com.pdm0126.cuidandohuellitas.Components.PetType
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.BlancoFocused
import com.pdm0126.cuidandohuellitas.ui.theme.BordeTextField
import com.pdm0126.cuidandohuellitas.ui.theme.Celeste
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
import com.pdm0126.cuidandohuellitas.ui.theme.NegroFocused
import com.pdm0126.cuidandohuellitas.ui.theme.Verde
import com.pdm0126.cuidandohuellitas.ui.theme.VerdeDisabled
import com.pdm0126.cuidandohuellitas.ui.theme.VerdeOscuro
import com.pdm0126.cuidandohuellitas.utils.ImageUtils.bitmapToUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPet(navBack: () -> Unit,
           navToHome: () -> Unit,
           viewModel: AddPetViewModel = viewModel(factory = AddPetViewModel.Factory)) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val guardadoExitoso by viewModel.guardadoExitoso.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.isLoading.collectAsState()

    if (loading){
        LoadingScreen()
        return
    }

// Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(guardadoExitoso, error) {
        when {
            guardadoExitoso -> {
                snackbarHostState.showSnackbar("¡Mascota agregada correctamente!")
                viewModel.resetState()
            }
            error != null -> {
                snackbarHostState.showSnackbar("Error: $error")
                viewModel.resetState()
            }
        }
    }

    //estados para la imagen de perfil
    var selectedImage by rememberSaveable { mutableStateOf<Any?>(null) }
    var showImageMenu by rememberSaveable { mutableStateOf(false) }

    //galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) selectedImage = uri
    }

    //Camara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) selectedImage = bitmap
    }
    //permisos de camara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch()
        }
    }

    // Estados para el menú de unidades de peso
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedUnit by rememberSaveable { mutableStateOf("kg") }
    var selectedDate by rememberSaveable { mutableStateOf("año") }

    // Estados de los campos
    var petName by rememberSaveable { mutableStateOf("") }
    var petType by rememberSaveable { mutableStateOf("") }
    var petAge by rememberSaveable { mutableStateOf("") }
    var petAgeByDate by rememberSaveable { mutableStateOf("") }
    var petWeight by rememberSaveable { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agregar Mascota",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Blanco,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Arrow back",
                        modifier = Modifier.clickable { navBack() },
                        tint = Blanco
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Verde
                )
            )
        },
        containerColor = Celeste
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
                .fillMaxSize()
                // Al tocar cualquier parte vacía, quitamos el foco y el cursor
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
                .verticalScroll(rememberScrollState())
                .padding(15.dp)
        ) {
            // Perfil Pet
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Card(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .clickable { showImageMenu = true }
                            .border(1.dp, BordeTextField, CircleShape),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = Blanco),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            if (selectedImage != null) {
                                AsyncImage(
                                    model = selectedImage,
                                    contentDescription = "Foto seleccionada",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.CameraAlt,
                                    contentDescription = "Icono cámara",
                                    modifier = Modifier.size(60.dp),
                                    tint = VerdeOscuro
                                )
                            }
                        }
                    }

                    //opciones de imagen de perfil
                    DropdownMenu(
                        expanded = showImageMenu,
                        onDismissRequest = { showImageMenu = false },
                        modifier = Modifier.background(Blanco)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tomar foto") },
                            leadingIcon = { Icon(Icons.Outlined.CameraAlt, contentDescription = null) },
                            onClick = {
                                showImageMenu = false
                                //pedir permiso
                                val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                    cameraLauncher.launch()
                                } else {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            colors = MenuItemColors(
                                textColor = NegroFocused,
                                leadingIconColor = NegroFocused,
                                trailingIconColor = NegroFocused,
                                disabledTextColor = NegroFocused,
                                disabledLeadingIconColor = NegroFocused,
                                disabledTrailingIconColor = NegroFocused,
                            )
                        )
                        DropdownMenuItem(
                            text = { Text("Elegir de galería") },
                            leadingIcon = { Icon(Icons.Outlined.PhotoLibrary, contentDescription = null) },
                            onClick = {
                                showImageMenu = false
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            colors = MenuItemColors(
                                textColor = NegroFocused,
                                leadingIconColor = NegroFocused,
                                trailingIconColor = NegroFocused,
                                disabledTextColor = NegroFocused,
                                disabledLeadingIconColor = NegroFocused,
                                disabledTrailingIconColor = NegroFocused,
                            )
                        )
                    }
                }
            }

            // Nombre
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "Nombre de la mascota: ", fontWeight = FontWeight.Medium, color = Negro)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = petName,
                    onValueChange = { petName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, BordeTextField, RoundedCornerShape(15.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BlancoFocused,
                        unfocusedContainerColor = Blanco,
                        focusedIndicatorColor = BordeTextField,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = NegroFocused,
                        // Si tiene texto, lo dejamos en NegroFocused aunque no tenga el foco
                        unfocusedTextColor = if (petName.isNotEmpty()) NegroFocused else Color.Transparent,
                        cursorColor = Blanco
                    ),
                    placeholder = { Text(text = "Ejem: Max, Luna, Coco ...") },
                    singleLine = true,
                    maxLines = 1,
                )
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "Tipo: ", fontWeight = FontWeight.Medium, color = Negro)
                Spacer(modifier = Modifier.height(8.dp))
                PetType(
                    onPetSelected = { 
                        petType = it
                        focusManager.clearFocus() // Al seleccionar tipo, cerramos teclado/foco
                    },
                    selectedPet = petType
                )
            }

            // Fila de Edad y Peso
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Edad: ", fontWeight = FontWeight.Medium, color = Negro)
                    Spacer(modifier = Modifier.height(8.dp))
//                    TextField(
//                        value = petAge,
//                        onValueChange = { petAge = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(15.dp))
//                            .border(1.dp, BordeTextField, RoundedCornerShape(15.dp)),
//                        colors = TextFieldDefaults.colors(
//                            focusedContainerColor = BlancoFocused,
//                            unfocusedContainerColor = Blanco,
//                            focusedIndicatorColor = BordeTextField,
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedTextColor = NegroFocused,
//                            unfocusedTextColor = if (petAge.isNotEmpty()) NegroFocused else Color.Transparent,
//                            cursorColor = Blanco
//                        ),
//                        placeholder = { Text(text = "Ejem: 1 año...") },
//                        singleLine = true
//                    )
                    DatePickerFieldToModal(petAge, onAgeSelected = { petAge = it })
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Columna de Peso con Menú Desplegable
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Peso:", fontWeight = FontWeight.Medium, color = Negro)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box {
                        TextField(
                            value = petWeight,
                            onValueChange = { petWeight = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp))
                                .border(1.dp, BordeTextField, RoundedCornerShape(15.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = BlancoFocused,
                                unfocusedContainerColor = Blanco,
                                focusedIndicatorColor = BordeTextField,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = NegroFocused,
                                unfocusedTextColor = if (petWeight.isNotEmpty()) NegroFocused else Blanco,
                                cursorColor = Blanco
                            ),
                            placeholder = { Text(text = "Ej. 5 ") },
                            trailingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .clickable {
                                            isExpanded = true
                                            focusManager.clearFocus() // Cerramos foco al abrir el menú
                                        }
                                ) {
                                    //el texto se ve en base a la unidad seleccionada
                                    Text(text = selectedUnit, color = Verde, fontWeight = FontWeight.Bold)
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        tint = Verde
                                    )
                                }
                            },
                            singleLine = true,
                            maxLines = 1
                        )
                        
                        DropdownMenu(
                            //si es true se muestra el menu desplegable
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },
                            modifier = Modifier.background(Blanco)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Kilogramos (kg)") },
                                onClick = {
                                    selectedUnit = "kg"
                                        //cuando selecciona se cierra
                                    isExpanded = false
                                },
                                colors = MenuItemColors(
                                    textColor = NegroFocused,
                                    leadingIconColor = NegroFocused,
                                    trailingIconColor = NegroFocused,
                                    disabledTextColor = NegroFocused,
                                    disabledLeadingIconColor = NegroFocused,
                                    disabledTrailingIconColor = NegroFocused,
                                )
                            )
                            DropdownMenuItem(
                                text = { Text("Libras (lb)") },
                                onClick = {
                                    selectedUnit = "lb"
                                    isExpanded = false
                                },
                                colors = MenuItemColors(
                                    textColor = NegroFocused,
                                    leadingIconColor = NegroFocused,
                                    trailingIconColor = NegroFocused,
                                    disabledTextColor = NegroFocused,
                                    disabledLeadingIconColor = NegroFocused,
                                    disabledTrailingIconColor = NegroFocused,
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    val photoUri: Uri? = when (val image = selectedImage) {
                        is Uri -> image
                        is Bitmap -> bitmapToUri(context, image)
                        else -> null
                    }
                    viewModel.addPet(
                        name = petName,
                        type = petType,
                        age = petAge,
                        weight = "$petWeight $selectedUnit",
                        photoUri = photoUri
                    )

                    navToHome()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
                colors = ButtonColors(
                    containerColor = Verde,
                    contentColor = Blanco,
                    disabledContainerColor = VerdeDisabled,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                Text(
                    text = "Agregar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blanco
                )
            }
        }
    }
}
