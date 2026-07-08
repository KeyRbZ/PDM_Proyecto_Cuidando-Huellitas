package com.pdm0126.cuidandohuellitas.Screens.Historial

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.cuidandohuellitas.Components.BottomNavigationBar
import com.pdm0126.cuidandohuellitas.Navigation.Routes
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.Celeste
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
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
    viewModel: HistorialViewModel = viewModel(factory = HistorialViewModel.Factory)
) {
    val error by viewModel.error.collectAsState()
    val listVaccines by viewModel.listVaccines.collectAsState()
    val vaccines by viewModel.vaccines.collectAsState()
    var showSheet by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(petId) {
        viewModel.getPetHistorial(petId)
        viewModel.getlistVaccines()
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
            if (error!=null){
                Text("$error")
            }else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(25.dp)
                ) {
                    items(vaccines) { vaccine ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Blanco
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Row(){
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Verde
                                        ),
                                        shape = RoundedCornerShape(50.dp),
                                        modifier = Modifier.wrapContentSize()
                                    ) {
                                        Text(
                                            text = vaccine.nameVac,
                                            color = Blanco,
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 6.dp
                                            ),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = "${vaccine.startDate}")
                                }

                                Text(
                                    text = "",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Verde
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text = "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Negro
                                )
                            }
                        }
                    }
                }
                Column() {

                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        onClick = { showSheet = true },
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
                                text = "Agregar vacunas a ",
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
    if (showSheet) {
        HistorialBottomSheet(
            onSave = { nameVac,startDate,intervale ->
                viewModel.addVaccine(petId,nameVac,startDate,intervale)
                viewModel.getPetHistorial(petId)
            },
            onDismiss = { showSheet = false },
            listVaccines
        )
    }
}