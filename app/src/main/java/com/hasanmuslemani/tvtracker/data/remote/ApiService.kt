package com.hasanmuslemani.tvtracker.data.remote

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.dto.TVSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/3/search/tv")
    suspend fun searchTV(
        @Query("api_key") key: String,
        @Query("language") lang: String = "en-US",
        @Query("query") search: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") adult: Boolean = false,
        ): TVSearchDto
}