package com.hasanmuslemani.tvtracker.common

import com.hasanmuslemani.tvtracker.BuildConfig
import com.hasanmuslemani.tvtracker.data.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.themoviedb.org/"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"
}