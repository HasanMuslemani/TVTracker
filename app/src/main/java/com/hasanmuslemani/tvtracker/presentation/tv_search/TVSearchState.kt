package com.hasanmuslemani.tvtracker.presentation.tv_search

import com.hasanmuslemani.tvtracker.domain.model.TVSearch

data class TVSearchState(
    val isLoading: Boolean = false,
    val tvSearches: List<TVSearch> = emptyList(),
    val error: String = ""
)
