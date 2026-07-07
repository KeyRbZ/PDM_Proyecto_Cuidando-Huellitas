package com.pdm0126.cuidandohuellitas.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    navToHome: () -> Unit,
    navToReminders: () -> Unit,
    navToTips: () -> Unit,
    navToProfile: () -> Unit,
) {
    NavigationBar{

        NavigationBarItem(
            selected = true,
            onClick = { navToHome() },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint= Color(0xFF9498A0)
                )
            }
        )


        NavigationBarItem(
            selected = false,
            onClick = { navToReminders() },
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Recordatorio" ,
                    tint= Color(0xFF9498A0)
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navToTips() },
            icon = {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = "Consejos",
                    tint= Color(0xFF9498A0)
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navToProfile() },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint= Color(0xFF9498A0)
                )
            }
        )
    }
}