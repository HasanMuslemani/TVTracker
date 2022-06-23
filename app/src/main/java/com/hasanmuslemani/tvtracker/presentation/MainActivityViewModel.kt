package com.hasanmuslemani.tvtracker.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasanmuslemani.tvtracker.common.Resource
import com.hasanmuslemani.tvtracker.data.remote.dto.toTVSearch
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import com.hasanmuslemani.tvtracker.domain.repository.TVSearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException

class MainActivityViewModel constructor(
    private val repository: TVSearchRepository
) : ViewModel() {

    private val _state = mutableStateOf(MainActivityState())
    val state: State<MainActivityState> = _state

    init {
        getTVSearches()
    }

    private fun getTVSearches() {
        invoke().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = MainActivityState(tvSearches = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = MainActivityState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MainActivityState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private operator fun invoke(): Flow<Resource<List<TVSearch>>> = flow {
        try {
            emit(Resource.Loading())
            val tvSearches1 = repository.getTVSearches()
            val tvSearches = tvSearches1.results!!.map { it.toTVSearch() }
            emit(Resource.Success(tvSearches))
        }
        catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error has occurred."))
        }
        catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}