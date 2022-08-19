package com.hasanmuslemani.tvtracker.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_search.toTVSearch
import com.hasanmuslemani.tvtracker.domain.repository.TVSearchRepository
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivityViewModel constructor(
    private val repository: TVSearchRepository,
    private val detailsRepository: TVShowDetailsRepository
) : ViewModel() {

    private val _state = mutableStateOf(MainActivityState())
    val state: State<MainActivityState> = _state

    private val _detailsState = mutableStateOf(0)
    val detailsState: State<Int> = _detailsState

    init {
        getTVSearches()
        getTVShowDetails()
    }

    private fun getTVSearches() {
        viewModelScope.launch {
            try {
                _state.value = MainActivityState(isLoading = true)
                val tvSearches = repository.getTVSearches().results?.map { it.toTVSearch() }
                _state.value = MainActivityState(tvSearches = tvSearches ?: emptyList())
            }
            catch(e: HttpException) {
                _state.value = MainActivityState(error = e.localizedMessage ?: "An unexpected error has occurred.")
            }
            catch(e: IOException) {
                _state.value = MainActivityState(error = "Couldn't reach server. Check your internet connection.")
            }
        }
    }

    private fun getTVShowDetails() {
        viewModelScope.launch {
            val tvShowDetails = detailsRepository.getTVShowDetail()
            _detailsState.value = tvShowDetails.voteCount ?: 0
        }
    }
}

class MainActivityViewModelFactory constructor(
    private val repository: TVSearchRepository,
    private val detailsRepository: TVShowDetailsRepository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository = repository, detailsRepository = detailsRepository) as T
    }

}