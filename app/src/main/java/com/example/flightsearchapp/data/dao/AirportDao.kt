package com.example.flightsearchapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearchapp.data.entities.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE name LIKE :query OR iata_code LIKE :query ORDER BY passengers DESC")
    fun searchAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id <> :currentId")
    fun getAllDestinations(currentId: Int): Flow<List<Airport>>

    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id = :id")
    fun getAirportById(id: Int): Flow<Airport>
}