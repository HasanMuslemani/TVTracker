package com.hasanmuslemani.tvtracker.data.remote

import com.hasanmuslemani.tvtracker.common.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/3/search/multi?api_key={key}&language=en-US&query={search}&page=1&include_adult=false")
    suspend fun search(@Query("key") key: String, @Query("search") search: String)
}