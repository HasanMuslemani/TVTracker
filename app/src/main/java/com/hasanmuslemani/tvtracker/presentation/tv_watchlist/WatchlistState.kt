package com.hasanmuslemani.tvtracker.presentation.tv_watchlist

import com.hasanmuslemani.tvtracker.domain.model.TVDetails
import com.hasanmuslemani.tvtracker.domain.model.TVSearch

data class WatchlistState(
    val isLoading: Boolean = false,
    val watchlist: List<TVSearch> = emptyList(),
    val error: String = ""
)