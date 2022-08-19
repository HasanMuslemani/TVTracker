package com.hasanmuslemani.tvtracker.data.remote.dto.tv_search

import com.hasanmuslemani.tvtracker.domain.model.TVSearch

data class TVSearchDto (
    val page: Int?,
    val results: List<TVSearchResult>?,
    val total_pages: Int?,
    val total_results: Int?
)