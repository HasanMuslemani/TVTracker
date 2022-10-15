package com.hasanmuslemani.tvtracker.data.local.dao

import androidx.room.*
import com.hasanmuslemani.tvtracker.data.local.entity.TVShowEntity

@Dao
interface TVShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShow(tvShow: TVShowEntity)

    @Query("SELECT * FROM tv_show ORDER BY createdOn DESC")
    suspend fun getTVShows(): List<TVShowEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM tv_show WHERE id = :tvShowId)")
    suspend fun isInWatchlist(tvShowId: Int): Boolean

    @Delete
    suspend fun deleteTVShow(tvShow: TVShowEntity)
}