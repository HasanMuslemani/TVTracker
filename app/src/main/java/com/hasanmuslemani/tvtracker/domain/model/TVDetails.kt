package com.hasanmuslemani.tvtracker.domain.model

import com.hasanmuslemani.tvtracker.data.local.entity.TVShowEntity
import java.util.*

data class TVDetails(
    val id: Int?,
    val backdropPath: String?,
    val posterPath: String?,
    val name: String?,
    val overview: String?,
    val firstAirDate: String?,
    val status: String?
) {
    fun toTVShowEntity(): TVShowEntity {
        return TVShowEntity(
            id = this.id,
            name = this.name,
            description = this.overview,
            airDate = this.firstAirDate,
            status = this.status,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            createdOn = Calendar.getInstance().time
        )
    }

    fun toTVSearch(): TVSearch {
        return TVSearch(
            id = this.id,
            title = this.name,
            imagePath = this.posterPath,
            firstAired = this.firstAirDate
        )
    }
}
