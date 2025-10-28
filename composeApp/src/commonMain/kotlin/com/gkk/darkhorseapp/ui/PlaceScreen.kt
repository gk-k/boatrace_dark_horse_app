package com.gkk.darkhorseapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.gkk.darkhorseapp.viewmodel.RaceViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PlaceScreen(viewModel: RaceViewModel, date: String, onPlaceSelected: (String) -> Unit, onBack: () -> Unit) {
    val places by viewModel.places.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.fetchPlaces(date) })
    
    BackHandler {
        onBack()
    }

    LaunchedEffect(date) {
        viewModel.fetchPlaces(date)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth().pullRefresh(pullRefreshState)
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(places) {
                    Text(
                        text = it,
                        modifier = Modifier.clickable { onPlaceSelected(it) }.padding(8.dp).fillMaxWidth(),
                        fontSize = 24.sp
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Button(onClick = onBack, modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
            Text("戻る")
        }
    }
}