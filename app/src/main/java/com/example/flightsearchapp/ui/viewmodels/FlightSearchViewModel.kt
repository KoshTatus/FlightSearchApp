package com.example.flightsearchapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.data.entities.Airport
import com.example.flightsearchapp.data.entities.Favorite
import com.example.flightsearchapp.data.entities.Flight
import com.example.flightsearchapp.data.preferences.SearchPreference
import com.example.flightsearchapp.data.repository.FlightSearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val repository: FlightSearchRepository,
    private val searchPreference: SearchPreference
) : ViewModel(){
    private val _airports = MutableStateFlow<List<Airport>>(emptyList())
    val airports: StateFlow<List<Airport>> = _airports

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _currAirport = MutableStateFlow<Airport?>(null)
    val currAirport: StateFlow<Airport?> = _currAirport

    private val _flights = MutableStateFlow<List<Flight>>(emptyList())
    val flights: StateFlow<List<Flight>> = _flights

    init {
        viewModelScope.launch{
            loadFavorites()
            loadSearchQuery()
        }
    }

    private fun loadFavorites(){
        viewModelScope.launch {
            repository.getAllFavorites().collect{ favorites->
                _favorites.value = favorites
            }
        }
    }

    private fun loadSearchQuery(){
        viewModelScope.launch {
            searchPreference.searchQuery.collect { query ->
                _searchQuery.value = query
               if (query.isNotEmpty())
                    searchAirports(query)
            }
        }
    }

    fun insertOrDeleteFavorite(favorite: Favorite){
        viewModelScope.launch {
            if (repository.getFavorite(favorite.departureCode, favorite.destinationCode) == null){
                insertFavorite(favorite)
            }
            else{
                deleteFavorite(favorite)
            }
        }
    }

    fun searchAirports(query: String){
        viewModelScope.launch {
            _searchQuery.value = query
            repository.getAirportsByQuery(query).collect{ airports->
                _airports.value = airports
            }
        }
    }

    fun saveQuery(query: String){
        viewModelScope.launch {
            searchPreference.saveQuery(query)
        }
    }

    fun deleteFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
            loadFavorites()
        }
    }

    private fun insertFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.insertFavorite(favorite)
            loadFavorites()
        }
    }

    fun getAllDestinations(currentId: Int){
        viewModelScope.launch {
            repository.getAllDestinations(currentId).collect{ flights->
                _flights.value = flights
            }
        }
    }

    fun selectAirport(airport: Airport) {
        _currAirport.value = airport
    }

    fun clearSelection() {
        _currAirport.value = null
        _flights.value = emptyList()
        _airports.value = emptyList()
    }

}