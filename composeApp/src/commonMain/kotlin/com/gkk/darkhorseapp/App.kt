package com.gkk.darkhorseapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gkk.darkhorseapp.ui.DateScreen
import com.gkk.darkhorseapp.ui.PlaceScreen
import com.gkk.darkhorseapp.ui.RaceListScreen
import com.gkk.darkhorseapp.ui.RankingScreen
import com.gkk.darkhorseapp.viewmodel.RaceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember { RaceViewModel() }

        var selectedDate by remember { mutableStateOf<String?>(null) }
        var selectedPlace by remember { mutableStateOf<String?>(null) }
        var selectedRace by remember { mutableStateOf<String?>(null) }

        val path = listOfNotNull(selectedDate, selectedPlace, selectedRace).joinToString(" / ")

        val onBack: (() -> Unit)? = when {
            selectedRace != null -> {
                { selectedRace = null }
            }
            selectedPlace != null -> {
                { selectedPlace = null }
            }
            selectedDate != null -> {
                { selectedDate = null }
            }
            else -> null
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(path) },
                    navigationIcon = {
                        if (onBack != null) {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when {
                    selectedDate == null -> {
                        DateScreen(viewModel) { date ->
                            selectedDate = date
                            selectedPlace = null
                            selectedRace = null
                        }
                    }
                    selectedPlace == null -> {
                        PlaceScreen(viewModel, selectedDate!!) { place ->
                            selectedPlace = place
                            selectedRace = null
                        }
                    }
                    selectedRace == null -> {
                        RaceListScreen(viewModel, selectedDate!!, selectedPlace!!) { race ->
                            selectedRace = race
                        }
                    }
                    else -> {
                        RankingScreen(viewModel, selectedDate!!, selectedPlace!!, selectedRace!!)
                    }
                }
            }
        }
    }
}