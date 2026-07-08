package com.pdm0126.cuidandohuellitas.Components

import android.R.attr.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdm0126.cuidandohuellitas.Navigation.Routes
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.BlancoFocused
import com.pdm0126.cuidandohuellitas.ui.theme.NegroFocused
import com.pdm0126.cuidandohuellitas.ui.theme.Verde

@Composable
fun BottomNavigationBar(
    currentRoute: Routes,
    navToHome: () -> Unit,
    navToReminders: () -> Unit,
    navToTips: () -> Unit,
    navToProfile: () -> Unit,
) {
    NavigationBar(
        containerColor = Blanco,
        contentColor = NegroFocused,
        tonalElevation = 2.dp
    ) {
        NavigationBarItem(
            selected = currentRoute is Routes.MainScreen,
            onClick = { navToHome() },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = if (currentRoute is Routes.MainScreen) Verde else NegroFocused
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = BlancoFocused,
                selectedIconColor = Verde,
                selectedTextColor = Verde,
                unselectedIconColor = NegroFocused,
                unselectedTextColor = NegroFocused
            )
        )

        NavigationBarItem(
            selected = false,
//            selected = currentRoute is Routes.Reminders,
            onClick = { navToReminders() },
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Recordatorio",
                    tint = NegroFocused
                        //if (currentRoute is Routes.Recordatorio) Verde else NegroFocused
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = BlancoFocused,
                selectedIconColor = Verde,
                selectedTextColor = Verde,
                unselectedIconColor = NegroFocused,
                unselectedTextColor = NegroFocused
            )
        )

        NavigationBarItem(
            selected = currentRoute is Routes.Tips,
            onClick = { navToTips() },
            icon = {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = "Consejos",
                    tint =
                    if (currentRoute is Routes.Tips) Verde else NegroFocused
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = BlancoFocused,
                selectedIconColor = Verde,
                selectedTextColor = Verde,
                unselectedIconColor = NegroFocused,
                unselectedTextColor = NegroFocused
            )
        )

        NavigationBarItem(
            selected = currentRoute is Routes.Profile,
            onClick = { navToProfile() },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = if (currentRoute is Routes.MainScreen) Verde else NegroFocused
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = BlancoFocused,
                selectedIconColor = Verde,
                selectedTextColor = Verde,
                unselectedIconColor = NegroFocused,
                unselectedTextColor = NegroFocused
            )
        )
    }
}