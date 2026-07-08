package com.pdm0126.cuidandohuellitas.Screens.TipScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TipScreen(viewModel: TipViewModel = viewModel()) {
    val tips by viewModel.tips.collectAsState()

    LazyColumn(Modifier.fillMaxSize()) {
        items(tips){tip->
            Text(tip.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    TipScreen()
}