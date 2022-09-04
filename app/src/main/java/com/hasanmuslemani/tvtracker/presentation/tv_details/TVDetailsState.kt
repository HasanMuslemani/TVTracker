package com.hasanmuslemani.tvtracker.presentation.tv_details

import com.hasanmuslemani.tvtracker.domain.model.TVDetails

data class TVDetailsState(
    val isLoading: Boolean = false,
    val tvDetails: TVDetails? = null,
    val error: String = ""
)
