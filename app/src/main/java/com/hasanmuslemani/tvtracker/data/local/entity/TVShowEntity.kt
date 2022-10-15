package com.hasanmuslemani.tvtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hasanmuslemani.tvtracker.data.local.DateConverter
import com.hasanmuslemani.tvtracker.domain.model.TVDetails
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import java.util.*

@Entity(tableName = "tv_show")
@TypeConverters(DateConverter::class)
data class TVShowEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String?,
    val description: String?,
    val airDate: String?,
    val status: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val createdOn: Date? = null
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

    fun toTVSearch(): TVSearch {
        return TVSearch(
            id = this.id,
            title = this.name,
            imagePath = this.posterPath,
            firstAired = this.airDate
        )
    }
}
