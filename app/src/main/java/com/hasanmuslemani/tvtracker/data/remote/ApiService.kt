package com.hasanmuslemani.tvtracker.data.remote

import com.hasanmuslemani.tvtracker.data.remote.dto.tv_details.TVShowDetailsDto
import com.hasanmuslemani.tvtracker.data.remote.dto.tv_search.TVSearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/tv/popular")
    suspend fun popularTVShows(
        @Query("api_key") key: String,
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
    ) : TVSearchDto

    @GET("/3/search/tv")
    suspend fun searchTV(
        @Query("api_key") key: String,
        @Query("language") lang: String = "en-US",
        @Query("query") search: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") adult: Boolean = false,
        ): TVSearchDto

    @GET("/3/tv/{id}")
    suspend fun tvShowDetails(
        @Path("id") id: Int,
        @Query("api_key") key: String,
        @Query("language") lang: String = "en-US"
    ): TVShowDetailsDto

}