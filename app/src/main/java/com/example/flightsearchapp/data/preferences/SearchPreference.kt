package com.example.flightsearchapp.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SearchPreference(private val dataStore: DataStore<Preferences>) {
    private companion object{
        val QUERY_KEY = stringPreferencesKey("search_query")
    }

    val searchQuery: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(
                emptyPreferences()
            )
        } else {
            throw exception
        }
    }.map {  prefs -> prefs[QUERY_KEY] ?: "" }

    suspend fun saveQuery(query: String){
        dataStore.edit{ preferences  ->
            preferences[QUERY_KEY] = query
        }
    }
}