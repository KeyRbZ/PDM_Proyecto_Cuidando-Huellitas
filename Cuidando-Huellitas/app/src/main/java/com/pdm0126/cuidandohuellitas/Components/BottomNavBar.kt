package com.pdm0126.cuidandohuellitas.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    selectedIndex: Int = 0,
    navToHome: () -> Unit,
    navToReminders: () -> Unit,
    navToTips: () -> Unit,
    navToProfile: () -> Unit,
) {
    val inactiveColor = Color(0xFF9498A0)
    val activeColor = Color.Black

    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { navToHome() },
            icon = { Icon(Icons.Default.Home, null, tint = if (selectedIndex == 0) activeColor else inactiveColor) }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { navToReminders() },
            icon = { Icon(Icons.Default.DateRange, null, tint = if (selectedIndex == 1) activeColor else inactiveColor) }
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { navToTips() },
            icon = { Icon(Icons.Default.Lightbulb, null, tint = if (selectedIndex == 2) activeColor else inactiveColor) }
        )
        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = { navToProfile() },
            icon = { Icon(Icons.Default.Person, null, tint = if (selectedIndex == 3) activeColor else inactiveColor) }
        )
    }
}