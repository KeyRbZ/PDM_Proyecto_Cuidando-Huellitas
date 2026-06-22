package com.pdm0126.cuidandohuellitas.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.BlancoFocused
import com.pdm0126.cuidandohuellitas.ui.theme.Verde

data class PetsImages(
    val id: Int,
    val imageUrl: String,
    val description: String
)

val dummyImages = listOf(
    PetsImages(1, "https://img.icons8.com/?size=96&id=20903&format=png", "Perro"),
    PetsImages(2, "https://img.icons8.com/?size=120&id=tvJZdxwTxU5v&format=png", "Gato"),
    PetsImages(3, "https://img.icons8.com/?size=96&id=16040&format=png", "Pez"),
    PetsImages(4, "https://img.icons8.com/?size=64&id=IhajvpXoizp4&format=png", "Pajaro"),
    PetsImages(5, "https://img.icons8.com/?size=96&id=16106&format=png", "Tortuga"),
    PetsImages(6, "https://img.icons8.com/?size=160&id=VErD4ahaXAkY&format=png", "Otro"),
)

@Composable
fun PetType(onPetSelected: (String) -> Unit, selectedPet: String) {
    // Estado para el scroll de la lista
    val listState = rememberLazyListState()


    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(dummyImages) { pet ->
                val isSelected = pet.description == selectedPet
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable { onPetSelected(pet.description) },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) BlancoFocused else Blanco
                        )
                            ,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = pet.imageUrl,
                                contentDescription = pet.description,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pet.description,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Mostrar icono de flecha solo si hay más contenido a la derecha
        if (listState.canScrollForward) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Más contenido derecha",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp)
                    .size(24.dp)
                    .background(Verde.copy(alpha = 0.5f), CircleShape)
                    .padding(4.dp),
                tint = Blanco,
            )
        }
        
        // Mostrar icono de flecha solo si hay más contenido a la izquierda
        if(listState.canScrollBackward){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Más contenido izquierda",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp)
                    .size(25.dp)
                    .background(Verde.copy(alpha = 0.5f), CircleShape)
                    .padding(4.dp),
                tint = Blanco,
            )
        }
    }
}
