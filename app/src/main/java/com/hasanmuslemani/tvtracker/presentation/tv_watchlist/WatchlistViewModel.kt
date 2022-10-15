package com.hasanmuslemani.tvtracker.presentation.tv_watchlist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import com.hasanmuslemani.tvtracker.presentation.tv_details.TVDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: TVShowDetailsRepository
): ViewModel() {

    private val _watchlistState = mutableStateOf(WatchlistState())
    val watchlistState: State<WatchlistState> = _watchlistState

    init {
        getWatchlist()
    }

    private fun getWatchlist() {
        viewModelScope.launch {
            try {
                _watchlistState.value = WatchlistState(isLoading = true)
                val watchlist = repository.getWatchlist().map { it.toTVSearch() }
                _watchlistState.value = WatchlistState(watchlist =  watchlist)
            }
            catch(e: Exception) {
                _watchlistState.value = WatchlistState(error = "An unexpected error has occurred.")
            }
        }
    }

    fun addToWatchlist(tvShow: TVSearch) {
        val watchlist = _watchlistState.value.watchlist
        val mutableWatchlist = watchlist.toMutableList()
        mutableWatchlist.add(0, tvShow)
        _watchlistState.value = WatchlistState(watchlist = mutableWatchlist)
    }

    fun removeFromWatchlist(tvShowId: Int) {
        val watchlist = _watchlistState.value.watchlist
        val mutableWatchlist = watchlist.toMutableList()
        for(i in mutableWatchlist.indices) {
            if(mutableWatchlist[i].id == tvShowId) {
                mutableWatchlist.removeAt(i)
                _watchlistState.value = WatchlistState(watchlist = mutableWatchlist)
                break
            }
        }
    }
}