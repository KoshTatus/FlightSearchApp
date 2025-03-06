package com.example.flightsearchapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.data.preferences.SearchPreference
import com.example.flightsearchapp.data.repository.FlightSearchRepository
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme
import com.example.flightsearchapp.ui.viewmodels.FlightSearchViewModel


class MainActivity : ComponentActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="search_preferences")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val searchPreference = SearchPreference(dataStore)
        val viewModel = FlightSearchViewModel(
            FlightSearchRepository(
                database.airportDao(),
                database.favoriteDao()
            ),
            searchPreference
        )

        setContent {
            FlightSearchAppTheme {
                App(viewModel)
            }
        }
    }
}
