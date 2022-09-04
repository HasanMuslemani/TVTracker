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
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TVDetailsViewModel constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository: TVShowDetailsRepository = TVShowDetailsRepositoryImpl()
    private val _state = mutableStateOf(TVDetailsState())
    val state: State<TVDetailsState> = _state

    init {
        val tvShowId = savedStateHandle.get<String>("tvShowId")
        if(tvShowId != null) {
            getTVDetails(tvShowId)
        }
    }

    private fun getTVDetails(tvShowId: String) {
        viewModelScope.launch {
            try {
                _state.value = TVDetailsState(isLoading = true)
                val tvDetails = repository.getTVShowDetail(tvShowId.toInt()).toTVDetails()
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

}

//class TVDetailsViewModelFactory constructor(
//    private val repository: TVShowDetailsRepository,
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return TVDetailsViewModel(repository = repository) as T
//    }
//
//}