package com.hasanmuslemani.tvtracker.presentation

import com.hasanmuslemani.tvtracker.domain.model.TVSearch

data class MainActivityState(
    val isLoading: Boolean = false,
    val tvSearches: List<TVSearch> = emptyList(),
    val error: String = ""
)
