package com.hasanmuslemani.tvtracker.domain.repository

import com.hasanmuslemani.tvtracker.data.remote.dto.tv_search.TVSearchDto

interface TVSearchRepository {

    suspend fun getTVSearches(): TVSearchDto

}