package com.pdm0126.cuidandohuellitas.Screens.Pet_Info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.cuidandohuellitas.Components.AlertDialogDelete
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.BlancoFocused
import com.pdm0126.cuidandohuellitas.ui.theme.BordeTextField
import com.pdm0126.cuidandohuellitas.ui.theme.Celeste
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
import com.pdm0126.cuidandohuellitas.ui.theme.Verde
import com.pdm0126.cuidandohuellitas.ui.theme.VerdeDisabled
import com.pdm0126.cuidandohuellitas.utils.ImageUtils.base64ToBitmap
import com.pdm0126.cuidandohuellitas.utils.calculateAge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pet_Info(
    navToHome: () -> Unit,
    navBack: () -> Unit,
    navToHistorial: () -> Unit,
    petId: String, //recibe el id de la mascota para cargar sus datos
    viewModel: Pet_Info_ViewModel = viewModel(factory = Pet_Info_ViewModel.Factory)
) {

    val pet by viewModel.pet.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }
    //carga los datos al entrar a la pantalla
    val guardadoExitoso by viewModel.guardadoExitoso.collectAsState()
    val error by viewModel.error.collectAsState()

// Snackbar
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso == true) {
            snackbarHostState.showSnackbar("Mascota eliminada correctamente")
            viewModel.resetState()
            navToHome() //navega después de mostrar el snackbar
        }
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar("Error: $it")
            viewModel.resetState()
        }
    }

    LaunchedEffect(petId) {
        viewModel.getPetDetails(petId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        //usa el nombre del ViewModel o vacío mientras carga
                        text = pet?.name ?: "",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Blanco,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        modifier = Modifier.clickable { navBack() },
                        tint = Blanco
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Regresar",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                openAlertDialog.value = true
                            },
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
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Perfil Pet
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(2.dp, BordeTextField, CircleShape),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val bitmap = remember(pet?.photoUrl) {
                            pet?.photoUrl?.base64ToBitmap()
                        }
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = pet?.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // placeholder si no tiene foto
                            Icon(
                                imageVector = Icons.Default.Pets,
                                contentDescription = null,
                                tint = Verde,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            //info Pet Container
            Column(
                modifier = Modifier
                    .background(Blanco, RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.dp, BordeTextField, RoundedCornerShape(15.dp))
            ) {

                Text(
                    text = "Información General",
                    fontWeight = FontWeight.Bold,
                    color = Verde,
                    modifier = Modifier.padding(19.dp),
                    fontSize = 20.sp
                )

                // Nombre
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Nombre:", fontWeight = FontWeight.Medium, color = Negro)
                    Text(
                        //dato del ViewModel
                        text = pet?.name ?: "",
                        fontWeight = FontWeight.Normal,
                        color = Negro
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    thickness = 0.5.dp,
                    color = BlancoFocused
                )

                // Tipo
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Tipo:", fontWeight = FontWeight.Medium, color = Negro)
                    Text(
                        //dato del ViewModel
                        text = pet?.type ?: "",
                        fontWeight = FontWeight.Normal,
                        color = Negro
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    thickness = 0.5.dp,
                    color = BlancoFocused
                )

                // Edad
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Edad:", fontWeight = FontWeight.Medium, color = Negro)
                    Text(
                        text = calculateAge(
                            pet?.age ?: ""
                        ),//llamando a la funcion para calcular la edad en base a la fecha de la bd
                        fontWeight = FontWeight.Normal,
                        color = Negro
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    thickness = 0.5.dp,
                    color = BlancoFocused
                )

                // Peso
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Peso:", fontWeight = FontWeight.Medium, color = Negro)
                    Text(
                        //dato del ViewModel
                        text = pet?.weight.toString(),
                        fontWeight = FontWeight.Normal,
                        color = Negro
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón Historial
            TextButton(
                onClick = { navToHistorial() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(15.dp)),
                colors = ButtonColors(
                    containerColor = Verde,
                    contentColor = Blanco,
                    disabledContainerColor = VerdeDisabled,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "corazon",
                        tint = Blanco,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Ver historial de salud ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                }
            }
        }
    }
    if (openAlertDialog.value) {
        when {
            openAlertDialog.value -> {
                AlertDialogDelete(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        viewModel.deletePet(petId)
                        navToHome()
                    },
                    dialogTitle = "¿Estas seguro de querer eliminar a ${pet?.name}?",
                    dialogText = "Esta es una decision definitiva y no se puede revertir",
                    icon = Icons.Default.Delete
                )
            }
        }
    }
}