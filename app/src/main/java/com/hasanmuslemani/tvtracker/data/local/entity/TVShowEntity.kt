package com.hasanmuslemani.tvtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hasanmuslemani.tvtracker.domain.model.TVDetails

@Entity(tableName = "tv_show")
data class TVShowEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val airDate: String,
    val status: String,
    val posterPath: String,
    val backdropPath: String
) {
    fun toTVDetails(): TVDetails {
        return TVDetails(
            id = this.id,
            name = this.name,
            overview = this.description,
            firstAirDate = this.airDate,
            status = this.status,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath
        )
    }
}
