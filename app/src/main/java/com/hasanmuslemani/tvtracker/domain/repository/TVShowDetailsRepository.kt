package com.hasanmuslemani.tvtracker.domain.repository

import com.hasanmuslemani.tvtracker.data.local.entity.TVShowEntity
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto

interface TVShowDetailsRepository {

    suspend fun getTVShowDetail(tvShowId: Int): TVShowDetailsDto

    suspend fun getWatchlist(): List<TVShowEntity>

    suspend fun addToWatchlist(tvShow: TVShowEntity)

    suspend fun removeFromWatchlist(tvShow: TVShowEntity)

    suspend fun isInWatchlist(tvShowId: Int): Boolean
}