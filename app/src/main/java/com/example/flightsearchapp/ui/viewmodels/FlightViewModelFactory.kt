package com.example.flightsearchapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flightsearchapp.data.dao.AirportDao
import com.example.flightsearchapp.data.dao.FavoriteDao
import com.example.flightsearchapp.data.preferences.SearchPreference
import com.example.flightsearchapp.data.repository.FlightSearchRepository

class FlightViewModelFactory(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val searchPreferencesRepository: SearchPreference
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightSearchViewModel::class.java)) {
            val repository = FlightSearchRepository(
                airportDao = airportDao,
                favoriteDao = favoriteDao
            )
            return FlightSearchViewModel(repository, searchPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}