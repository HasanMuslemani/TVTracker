package com.hasanmuslemani.tvtracker.common

import com.hasanmuslemani.tvtracker.BuildConfig
import com.hasanmuslemani.tvtracker.data.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Constants {
    val API_KEY = BuildConfig.API_KEY
    val BASE_URL = "https://api.themoviedb.org/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}