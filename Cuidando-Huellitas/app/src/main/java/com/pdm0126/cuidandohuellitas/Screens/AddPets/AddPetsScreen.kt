package com.pdm0126.cuidandohuellitas.Screens.AddPets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.BordeTextField
import com.pdm0126.cuidandohuellitas.ui.theme.Celeste
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
import com.pdm0126.cuidandohuellitas.ui.theme.Verde

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPet(navBack: () -> Unit) {
    var petFile by rememberSaveable { mutableStateOf("") }

    Scaffold(
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
                .verticalScroll(rememberScrollState())
        ) {
            // Nombre
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "Nombre de la mascota: ", fontWeight = FontWeight.Medium, color = Negro)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = petFile,
                    onValueChange = { petFile = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, BordeTextField, RoundedCornerShape(15.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Blanco,
                        unfocusedContainerColor = Blanco,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Ejem: Max, Luna, Coco ...") },
                    singleLine = true,
                    maxLines = 1,
                )
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "Tipo: ", fontWeight = FontWeight.Medium, color = Negro)
            }

            // Fila de Edad y Peso
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Edad: ", fontWeight = FontWeight.Medium, color = Negro)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = petFile,
                        onValueChange = { petFile = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .border(1.dp, BordeTextField, RoundedCornerShape(15.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Blanco,
                            unfocusedContainerColor = Blanco,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Ejem: 1 año...") },
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Peso:", fontWeight = FontWeight.Medium, color = Negro)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = petFile,
                        onValueChange = { petFile = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .border(1.dp, BordeTextField, RoundedCornerShape(15.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Blanco,
                            unfocusedContainerColor = Blanco,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Ejem: 5 kg") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                onClick = { /* ViewModel */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
                colors = ButtonColors(
                    containerColor = Verde,
                    contentColor = Blanco,
                    disabledContainerColor = Color.Transparent,
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
