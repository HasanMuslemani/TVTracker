package com.hasanmuslemani.tvtracker.domain.repository

import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto

interface TVShowDetailsRepository {

    suspend fun getTVShowDetail(tvShowId: Int): TVShowDetailsDto

}