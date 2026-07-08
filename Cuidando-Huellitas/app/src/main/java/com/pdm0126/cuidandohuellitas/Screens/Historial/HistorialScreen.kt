package com.pdm0126.cuidandohuellitas.Screens.Historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.cuidandohuellitas.Components.BottomNavigationBar
import com.pdm0126.cuidandohuellitas.Navigation.Routes
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.Celeste
import com.pdm0126.cuidandohuellitas.ui.theme.Verde
import com.pdm0126.cuidandohuellitas.ui.theme.VerdeDisabled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    currentRoute: Routes,
    navToVaccacines: () -> Unit,
    navToProfile: () -> Unit,
    navToTips: () -> Unit,
    navBack: () -> Unit,
    petId: String,
    navToHome: () -> Unit,
    // viewModel: HistorialViewModel = viewModel(factory = HistorialViewModel.Factory)
) {

    //val pet by viewModel.pet.collectAsState()

    LaunchedEffect(petId) {
        //viewModel.getHistorial(petId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Historial de Salud",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Blanco,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Verde
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute as Routes,
                navToHome = { navToHome() },
                navToReminders = {},
                navToTips = { navToTips() },
                navToProfile = { navToProfile() }
            )
        },
        containerColor = Celeste
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Text("hola corazon bello")

            LazyColumn() {

            }
            Column() {
                TextButton(
                    onClick = { navToVaccacines() },
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
                            imageVector = Icons.Default.Vaccines,
                            contentDescription = "Vaccines",
                            tint = Blanco,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Ver vacunas por mascota ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blanco
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = { },
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
                            imageVector = Icons.Default.Vaccines,
                            contentDescription = "Vaccines",
                            tint = Blanco,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Ver vacunas por mascota ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blanco
                        )
                    }
                }
            }
        }
    }
}