package com.gkk.darkhorseapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RaceViewModel : ViewModel() {

    private val _dates = MutableStateFlow<List<String>>(emptyList())
    val dates = _dates.asStateFlow()

    private val _places = MutableStateFlow<List<String>>(emptyList())
    val places = _places.asStateFlow()

    private val _races = MutableStateFlow<List<String>>(emptyList())
    val races = _races.asStateFlow()

    private val _ranking = MutableStateFlow<List<Int>>(emptyList())
    val ranking = _ranking.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val firestore = Firebase.firestore

    fun fetchDates() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // Path: /predictions/dark_horse/race_date
                val snapshot = firestore.collection("predictions/dark_horse/race_date").orderBy("race_date",
                    Direction.DESCENDING).get()
                _dates.value = snapshot.documents.map { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
                _dates.value = emptyList()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun fetchPlaces(date: String) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // Path: /predictions/dark_horse/race_date/{date}/place
                val snapshot = firestore.collection("predictions/dark_horse/race_date/$date/place").get()
                _places.value = snapshot.documents.map { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
                _places.value = emptyList()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun fetchRaces(date: String, place: String) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // Path: /predictions/dark_horse/race_date/{date}/place/{place}/races
                val snapshot =
                    firestore.collection("predictions/dark_horse/race_date/$date/place/$place/races").get()
                _races.value = snapshot.documents.map { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
                _races.value = emptyList()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun fetchRanking(date: String, place: String, race: String) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // Path: /predictions/dark_horse/race_date/{date}/place/{place}/races/{race}
                val raceDocument =
                    firestore.document("predictions/dark_horse/race_date/$date/place/$place/races/$race").get()

                if (raceDocument.exists) {
                    // Assuming the document has a "ranking" field of type Array<Number>
                    val rankingList = raceDocument.get("ranking") as? List<Long> ?: emptyList()
                    _ranking.value = rankingList.map { it.toInt() }
                } else {
                     _ranking.value = emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _ranking.value = emptyList()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
