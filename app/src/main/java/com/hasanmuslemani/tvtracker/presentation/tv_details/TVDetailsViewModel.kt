package com.hasanmuslemani.tvtracker.presentation.tv_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.toTVDetails
import com.hasanmuslemani.tvtracker.data.repository.TVShowDetailsRepositoryImpl
import com.hasanmuslemani.tvtracker.domain.model.TVDetails
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import com.hasanmuslemani.tvtracker.presentation.tv_watchlist.WatchlistViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TVDetailsViewModel @Inject constructor(
    private val repository: TVShowDetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TVDetailsState())
    val state: State<TVDetailsState> = _state

    private val _isInWatchlistState = mutableStateOf(false)
    val isInWatchlist: State<Boolean> = _isInWatchlistState

    init {
        val tvShowId = savedStateHandle.get<String>("tvShowId")
        if(tvShowId != null) {
            val tvShowIdInt = tvShowId.toInt()
            getTVDetails(tvShowIdInt)
            isInWatchlist(tvShowIdInt)
        }
    }

    private fun getTVDetails(tvShowId: Int) {
        viewModelScope.launch {
            try {
                _state.value = TVDetailsState(isLoading = true)
                val tvDetails = repository.getTVShowDetail(tvShowId).toTVDetails()
                _state.value = TVDetailsState(tvDetails = tvDetails)
            }
            catch(e: HttpException) {
                _state.value = TVDetailsState(error = e.localizedMessage ?: "An unexpected error has occurred.")
            }
            catch(e: IOException) {
                _state.value = TVDetailsState(error = "Couldn't reach server. Check your internet connection.")
            }
        }
    }

    fun addToWatchlist(tvShow: TVDetails, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.addToWatchlist(tvShow.toTVShowEntity())
                _isInWatchlistState.value = true
                onSuccess()
            }
            catch(e: Exception) {

            }
        }
    }

    fun removeFromWatchlist(tvShow: TVDetails, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.removeFromWatchlist(tvShow.toTVShowEntity())
                _isInWatchlistState.value = false
                onSuccess()
            }
            catch(e: Exception) {

            }
        }
    }

    private fun isInWatchlist(tvShowId: Int) {
        viewModelScope.launch {
            _isInWatchlistState.value = repository.isInWatchlist(tvShowId)
        }
    }

}