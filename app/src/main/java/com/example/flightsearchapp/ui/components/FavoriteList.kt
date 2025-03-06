package com.example.flightsearchapp.ui.components

import android.annotation.SuppressLint
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
import com.example.flightsearchapp.data.entities.Favorite

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoriteList(
    favorites: List<Favorite>,
    onFavoriteClick: (Favorite) -> Unit,
) {
    if (favorites.isNotEmpty()){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Избранные рейсы",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(favorites) { favorite ->
                FavoriteCard(
                    favorite = favorite,
                    onFavoriteClick = {onFavoriteClick(favorite)}
                )
            }
        }
    }
}