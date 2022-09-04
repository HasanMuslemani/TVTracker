package com.hasanmuslemani.tvtracker.data.repository

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.ApiService
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_search.TVSearchDto
import com.hasanmuslemani.tvtracker.domain.repository.TVSearchRepository
import kotlinx.coroutines.delay

class TVSearchRepositoryImpl constructor(
    private val api: ApiService
): TVSearchRepository {

    override suspend fun getTVSearches(search: String): TVSearchDto {
        return api.searchTV(key = Constants.API_KEY, search = search)
    }

    override suspend fun getPopularTVShows(): TVSearchDto {
        return api.popularTVShows(key = Constants.API_KEY)
    }

}