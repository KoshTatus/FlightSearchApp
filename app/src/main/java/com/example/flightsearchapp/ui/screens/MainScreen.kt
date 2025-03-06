package com.example.flightsearchapp.ui.screens

import SearchField
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.entities.Favorite
import com.example.flightsearchapp.ui.components.AirportList
import com.example.flightsearchapp.ui.components.FavoriteList
import com.example.flightsearchapp.ui.components.FlightList
import com.example.flightsearchapp.ui.viewmodels.FlightSearchViewModel


@Composable
fun MainScreen(viewModel: FlightSearchViewModel) {
    val query by viewModel.searchQuery.collectAsState()
    var searchText by remember { mutableStateOf(query) }

    val airports by viewModel.airports.collectAsState()
    val flights by viewModel.flights.collectAsState()
    val currAirport by viewModel.currAirport.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val isSearchEnabled = currAirport == null

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        )

        if (isSearchEnabled) {
            SearchField(
                query = query,
                onQueryChange = {
                    searchText = it
                    viewModel.saveQuery(searchText)
                    viewModel.searchAirports(it)
                }
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            )
        }

        else{
            Button(
                onClick = {
                    viewModel.clearSelection()
                    viewModel.searchAirports(query)
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black
                ),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Red,
                    disabledContentColor = Color.Green,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(text = "Назад")
            }
        }

        if (query.isNotEmpty() && currAirport == null) {
            Log.d("MainScreen: ", "${airports.size}")
            AirportList(
                airports = airports,
                onClickAirport = { airport ->
                    viewModel.selectAirport(airport)
                    viewModel.getAllDestinations(airport.id)
                }
            )
        }

        if (currAirport != null) {
            FlightList(
                departureAirport = currAirport!!,
                flights = flights,
                favorites = favorites,
                onClickFlight = { flight ->
                    viewModel.insertOrDeleteFavorite(
                        Favorite(
                            destinationCode = flight.destinationAirport.iata,
                            departureCode = flight.departureAirport.iata
                        )
                    )
                }
            )
        } else if (query.isEmpty()) {
            FavoriteList(
                favorites = favorites,
                onFavoriteClick = { favorite ->
                    viewModel.deleteFavorite(favorite)
                }
            )
        }
    }
}