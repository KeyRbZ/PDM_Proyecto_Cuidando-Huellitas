package com.pdm0126.cuidandohuellitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pdm0126.cuidandohuellitas.Navigation.App
import com.pdm0126.cuidandohuellitas.ui.theme.CuidandoHuellitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuidandoHuellitasTheme {
                App()
            }
        }
    }
}