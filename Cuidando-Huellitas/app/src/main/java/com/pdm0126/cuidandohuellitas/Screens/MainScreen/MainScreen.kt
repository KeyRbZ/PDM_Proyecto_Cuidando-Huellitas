package com.pdm0126.cuidandohuellitas.Screens.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import androidx.compose.foundation.Image
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.runtime.remember
import com.pdm0126.cuidandohuellitas.Components.BottomNavigationBar
import com.pdm0126.cuidandohuellitas.utils.ImageUtils.base64ToBitmap


data class Mascotas(
    val nombre: String,
    val img: String
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navToPetInfo: (String) -> Unit,
               navToAddPet: () -> Unit,
               viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory)) {
    val pets by viewModel.pets.collectAsState()

    var selectedItem by remember { mutableStateOf("perfil") }
    val misMascotas = listOf(
        Mascotas("Firulais", "🐶"),
        Mascotas("Michi", "🐱"),
        Mascotas("Luna", "🐕"),
        Mascotas("Pelusa", "🐈"),
        Mascotas("Rocky", "🐶")
    )
    Scaffold(
        containerColor = Color(0xFFeffafb),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cuidando Huellitas",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFAFCF2)) },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9dbf9e)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navToHome = {},
                navToReminders = {},
                navToTips = {},
                navToProfile = {}
            )
        }
        ,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navToAddPet()
                },
                containerColor = Color(0xFF9dbf9e),
                contentColor = Color(0xFFFAFCF2)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }

    ) {innerPadding ->
        Column(Modifier.padding(innerPadding).fillMaxSize().padding(15.dp)) {

            Text("Mis Mascotas",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF9dbf9e))

            LazyRow(Modifier.fillMaxWidth()){
                    items(pets) { pet ->
                        ElevatedCard(
                            modifier = Modifier.padding(8.dp)
                                .clickable { navToPetInfo( pet.id) },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp),
                            colors = CardDefaults.cardColors(Color(0xFFFAFCF2))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF9dbf9e)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val bitmap = remember(pet.photoUrl) {
                                        pet.photoUrl.base64ToBitmap()
                                    }
                                    if (bitmap != null) {
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = pet.name,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        // placeholder si no tiene foto
                                        Icon(
                                            imageVector = Icons.Default.Pets,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(text = pet.name)
                            }
                        }
                    }
                }

            }
        }
    }


//@Preview(showBackground = true)
//@Composable
//fun PreviewMain(){
//    MainScreen()
//}