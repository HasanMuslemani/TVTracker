package com.hasanmuslemani.tvtracker.data.local.dao

import androidx.room.*
import com.hasanmuslemani.tvtracker.data.local.entity.TVShowEntity

@Dao
interface TVShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShow(tvShow: TVShowEntity)

    @Delete
    suspend fun deleteTVShow(tvShow: TVShowEntity)
}