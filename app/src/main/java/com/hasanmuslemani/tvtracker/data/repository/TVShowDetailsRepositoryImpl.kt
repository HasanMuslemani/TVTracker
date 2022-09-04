package com.hasanmuslemani.tvtracker.data.repository

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.ApiService
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import kotlinx.coroutines.delay

class TVShowDetailsRepositoryImpl(
    private val api: ApiService
): TVShowDetailsRepository {

    override suspend fun getTVShowDetail(tvShowId: Int): TVShowDetailsDto {
        return api.tvShowDetails(id = tvShowId, key = Constants.API_KEY)
    }

}