package com.gkk.darkhorseapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gkk.darkhorseapp.viewmodel.RaceViewModel

@Composable
fun RankingScreen(viewModel: RaceViewModel, date: String, place: String, race: String) {
    val ranking by viewModel.ranking.collectAsState()
    viewModel.fetchRanking(date, place, race)

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        itemsIndexed(ranking) { index, boatNumber ->
            Text(text = "${index + 1}位: ${boatNumber}号艇", modifier = Modifier.padding(8.dp).fillMaxWidth(),
                fontSize = 24.sp)
        }
    }
}
