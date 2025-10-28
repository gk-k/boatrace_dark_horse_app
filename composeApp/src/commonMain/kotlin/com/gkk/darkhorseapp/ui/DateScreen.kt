package com.gkk.darkhorseapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun DateScreen(viewModel: RaceViewModel, onDateSelected: (String) -> Unit) {
    val dates by viewModel.dates.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.fetchDates() })

    LaunchedEffect(Unit) {
        viewModel.fetchDates()
    }

    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(dates) {
                Text(
                    text = it,
                    modifier = Modifier.clickable { onDateSelected(it) }.padding(8.dp).fillMaxWidth(),
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
