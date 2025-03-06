package com.example.flightsearchapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.data.preferences.SearchPreference
import com.example.flightsearchapp.ui.screens.MainScreen
import com.example.flightsearchapp.ui.viewmodels.FlightSearchViewModel
import com.example.flightsearchapp.ui.viewmodels.FlightViewModelFactory

@Composable
fun App(
    viewModel: FlightSearchViewModel
){
    Surface(color = MaterialTheme.colorScheme.background) {
        MainScreen (viewModel = viewModel)
    }
}
