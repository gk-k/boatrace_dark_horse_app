package com.gkk.darkhorseapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gkk.darkhorseapp.viewmodel.RaceViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RankingScreen(viewModel: RaceViewModel, date: String, place: String, race: String) {
    val ranking by viewModel.ranking.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.fetchRanking(date, place, race) })

    LaunchedEffect(date, place, race) {
        viewModel.fetchRanking(date, place, race)
    }

    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            itemsIndexed(ranking) { index, boatNumber ->
                Text(
                    text = "${index + 1}位: ${boatNumber}号艇",
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
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
}
