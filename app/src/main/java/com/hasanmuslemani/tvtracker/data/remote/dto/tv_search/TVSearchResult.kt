package com.hasanmuslemani.tvtracker.data.remote.dto.tv_search

import com.hasanmuslemani.tvtracker.domain.model.TVSearch

data class TVSearchResult (
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun TVSearchResult.toTVSearch(): TVSearch {
    return TVSearch(
        id = this.id,
        title = this.name,
        imagePath = this.poster_path,
        firstAired = this.first_air_date
    )
}