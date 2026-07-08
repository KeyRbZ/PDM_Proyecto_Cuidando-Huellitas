package com.pdm0126.cuidandohuellitas.Screens.TipScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.cuidandohuellitas.Components.BottomNavigationBar
import com.pdm0126.cuidandohuellitas.Components.LoadingScreen
import com.pdm0126.cuidandohuellitas.Navigation.Routes
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.Celeste
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
import com.pdm0126.cuidandohuellitas.ui.theme.Verde

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipScreen(viewModel: TipViewModel = viewModel(),
              currentRoute: Routes, navToHome :() -> Unit) {
    val tips by viewModel.tips.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    if(loading){
        LoadingScreen("Consejos")
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Consejos",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Blanco) },

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
                navToTips = {},
                navToProfile = {}
            )
        },
        containerColor = Celeste
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(25.dp)
        ) {
            items(tips) { tip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth().padding(0.dp,10.dp),
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
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Verde
                            ),
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Text(
                                text = tip.category,
                                color = Blanco,
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                ),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = tip.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Verde
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = tip.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Negro
                        )
                    }
                }
            }
        }
    }
}
