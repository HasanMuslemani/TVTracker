package com.hasanmuslemani.tvtracker.data.repository

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.local.TVShowDatabase
import com.hasanmuslemani.tvtracker.data.local.entity.TVShowEntity
import com.hasanmuslemani.tvtracker.data.remote.ApiService
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import kotlinx.coroutines.delay

class TVShowDetailsRepositoryImpl(
    private val api: ApiService,
    private val db: TVShowDatabase
): TVShowDetailsRepository {

    override suspend fun getTVShowDetail(tvShowId: Int): TVShowDetailsDto {
        return api.tvShowDetails(id = tvShowId, key = Constants.API_KEY)
    }

    override suspend fun getWatchlist(): List<TVShowEntity> {
        return db.tvShowDao.getTVShows()
    }

    override suspend fun addToWatchlist(tvShow: TVShowEntity) {
        db.tvShowDao.insertTVShow(tvShow)
    }

    override suspend fun removeFromWatchlist(tvShow: TVShowEntity) {
        db.tvShowDao.deleteTVShow(tvShow)
    }

    override suspend fun isInWatchlist(tvShowId: Int): Boolean {
        return db.tvShowDao.isInWatchlist(tvShowId)
    }

}