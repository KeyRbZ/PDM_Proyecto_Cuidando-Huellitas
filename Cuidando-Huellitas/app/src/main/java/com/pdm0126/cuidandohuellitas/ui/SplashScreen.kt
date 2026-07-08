package com.pdm0126.cuidandohuellitas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToLogin()
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = "Huella",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Cuidando\nHuellitas",
                    fontSize = 38.sp,      // Un poco más grande para igualar la imagen
                    lineHeight = 44.sp,    // Interlineado más amplio para que respire el texto
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tu compañero en el cuidado de\nmascotas",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,      // Estrecha el texto idéntico al prototipo
                    lineHeight = 22.sp,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Row(
                modifier = Modifier.padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val amarilloPunto = Color(0xFFF1C40F)

                // Primer punto activo (tamaño 12dp)
                Box(modifier = Modifier.size(12.dp).background(amarilloPunto, CircleShape))

                // Segundo y tercer punto inactivos (tamaño 10dp y con opacidad/alpha)
                Box(modifier = Modifier.size(10.dp).background(amarilloPunto.copy(alpha = 0.4f), CircleShape))
                Box(modifier = Modifier.size(10.dp).background(amarilloPunto.copy(alpha = 0.4f), CircleShape))
            }
        }
    }
}