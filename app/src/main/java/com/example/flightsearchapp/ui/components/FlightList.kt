package com.example.flightsearchapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.entities.Airport
import com.example.flightsearchapp.data.entities.Favorite
import com.example.flightsearchapp.data.entities.Flight

@Composable
fun FlightList(
    departureAirport: Airport,
    flights: List<Flight>,
    favorites: List<Favorite>,
    onClickFlight: (Flight) -> Unit,
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = "Рейсы из ${departureAirport.iata}",
            style = MaterialTheme.typography.headlineMedium
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(5.dp)
    ) {
        items(flights) { flight ->
            FlightCard(
                flight = flight,
                isFavorite = favorites.any {
                    it.departureCode == flight.departureAirport.iata &&
                            it.destinationCode == flight.destinationAirport.iata
                },
                onFlightClick = onClickFlight
            )
        }
    }
}