package com.hasanmuslemani.tvtracker.data.repository

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_search.TVSearchDto
import com.hasanmuslemani.tvtracker.domain.repository.TVSearchRepository
import kotlinx.coroutines.delay

class TVSearchRepositoryImpl: TVSearchRepository{

    override suspend fun getTVSearches(search: String): TVSearchDto {
        delay(1000)
        return Constants.api.searchTV(key = Constants.API_KEY, search = search)
    }

    override suspend fun getPopularTVShows(): TVSearchDto {
        delay(1000)
        return Constants.api.popularTVShows(key = Constants.API_KEY)
    }

}