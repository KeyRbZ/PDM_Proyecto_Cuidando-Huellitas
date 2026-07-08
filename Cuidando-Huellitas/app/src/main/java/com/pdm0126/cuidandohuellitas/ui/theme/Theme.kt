package com.pdm0126.cuidandohuellitas.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Verde,
    background = NegroFocused,
    surface = NegroFocused,
    onPrimary = Blanco,
    onBackground = Blanco,
    onSurface = Blanco
)

private val LightColorScheme = lightColorScheme(
    primary = Verde,                // Botones principales e íconos grandes
    background = Celeste,            // Fondo general de las pantallas
    surface = Blanco,                // Fondo de text fields y tarjetas
    onPrimary = Blanco,              // Texto dentro de los botones verdes
    onBackground = NegroFocused,     // Texto general oscuro
    onSurface = NegroFocused         // Texto secundario sobre superficies blancas
)

@Composable
fun CuidandoHuellitasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}