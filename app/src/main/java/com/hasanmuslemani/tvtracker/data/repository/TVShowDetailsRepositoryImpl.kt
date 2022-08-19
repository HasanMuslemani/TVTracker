package com.hasanmuslemani.tvtracker.data.repository

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository

class TVShowDetailsRepositoryImpl: TVShowDetailsRepository {

    override suspend fun getTVShowDetail(): TVShowDetailsDto {
        return Constants.api.tvShowDetails(id = 2349, key = Constants.API_KEY)
    }

}