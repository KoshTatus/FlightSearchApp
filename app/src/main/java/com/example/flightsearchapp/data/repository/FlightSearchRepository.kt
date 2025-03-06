package com.example.flightsearchapp.data.repository

import com.example.flightsearchapp.data.dao.AirportDao
import com.example.flightsearchapp.data.dao.FavoriteDao
import com.example.flightsearchapp.data.entities.Favorite
import com.example.flightsearchapp.data.entities.Flight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class FlightSearchRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
) {
    fun getAirportsByQuery(query: String) = airportDao.searchAirports("%$query%")

    fun getAllDestinations(currentId: Int): Flow<List<Flight>> = flow {
        val currentAirport = airportDao.getAirportById(currentId)
        val destinations = airportDao.getAllDestinations(currentId)
        val flights = mutableListOf<Flight>()

        destinations.first().map { destination ->
            flights.add(Flight(currentAirport.first(), destination))
        }

        emit(flights)
    }

    fun getAllFavorites() = favoriteDao.getAllFavorites()

    suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite.departureCode, favorite.destinationCode)

    suspend fun getFavorite(departureCode: String, destinationCode: String) = favoriteDao.getFavorite(departureCode, destinationCode)
}