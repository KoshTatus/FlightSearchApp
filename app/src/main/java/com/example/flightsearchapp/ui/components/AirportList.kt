package com.example.flightsearchapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearchapp.data.entities.Airport
import com.example.flightsearchapp.ui.viewmodels.FlightSearchViewModel

@Composable
fun AirportList(
    airports: List<Airport>,
    onClickAirport: (Airport) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(airports){ airport ->
            AirportCard(
                airport = airport,
                onClickAirport = { onClickAirport(airport) }
            )
        }
    }
}