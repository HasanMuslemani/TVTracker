package com.hasanmuslemani.tvtracker.data.repository

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import kotlinx.coroutines.delay

class TVShowDetailsRepositoryImpl: TVShowDetailsRepository {

    override suspend fun getTVShowDetail(tvShowId: Int): TVShowDetailsDto {
        delay(1000)
        return Constants.api.tvShowDetails(id = tvShowId, key = Constants.API_KEY)
    }

}