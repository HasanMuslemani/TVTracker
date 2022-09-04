package com.hasanmuslemani.tvtracker.di

import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.remote.ApiService
import com.hasanmuslemani.tvtracker.data.repository.TVSearchRepositoryImpl
import com.hasanmuslemani.tvtracker.data.repository.TVShowDetailsRepositoryImpl
import com.hasanmuslemani.tvtracker.domain.repository.TVSearchRepository
import com.hasanmuslemani.tvtracker.domain.repository.TVShowDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTVSearchRepository(api: ApiService): TVSearchRepository {
        return TVSearchRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTVShowDetailsRepository(api: ApiService): TVShowDetailsRepository {
        return TVShowDetailsRepositoryImpl(api)
    }
}