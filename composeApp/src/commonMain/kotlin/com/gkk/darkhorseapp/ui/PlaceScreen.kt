package com.gkk.darkhorseapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gkk.darkhorseapp.viewmodel.RaceViewModel

@Composable
fun PlaceScreen(viewModel: RaceViewModel, date: String, onPlaceSelected: (String) -> Unit) {
    val places by viewModel.places.collectAsState()
    viewModel.fetchPlaces(date)

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(places) {
            Text(text = it, modifier = Modifier.clickable { onPlaceSelected(it) }.padding(8.dp).fillMaxWidth(),
                fontSize = 24.sp)
        }
    }
}
