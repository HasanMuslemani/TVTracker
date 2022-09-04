package com.hasanmuslemani.tvtracker.presentation.tv_search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_search.toTVSearch
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import com.hasanmuslemani.tvtracker.domain.repository.TVSearchRepository
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import com.hasanmuslemani.tvtracker.presentation.tv_search.TVSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TVSearchViewModel @Inject constructor(
    private val repository: TVSearchRepository,
) : ViewModel() {

    private val _state = mutableStateOf(TVSearchState())
    val state: State<TVSearchState> = _state

    var searchJob: Job? = null

    init {
        getTVSearches(null)
    }

    fun getTVSearches(search: String?) {
        if(search != null && search.isEmpty()) return
        viewModelScope.launch {
            try {
                _state.value = TVSearchState(isLoading = true)
                val tvSearches = if(search == null) {
                    repository.getPopularTVShows().results?.map { it.toTVSearch() }
                } else {
                    repository.getTVSearches(search).results?.map { it.toTVSearch() }
                }
                _state.value = TVSearchState(tvSearches = tvSearches ?: emptyList())
            }
            catch(e: HttpException) {
                _state.value = TVSearchState(error = e.localizedMessage ?: "An unexpected error has occurred.")
            }
            catch(e: IOException) {
                _state.value = TVSearchState(error = "Couldn't reach server. Check your internet connection.")
            }
        }
    }

    fun searchDebounced(search: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            getTVSearches(search)
        }
    }
}