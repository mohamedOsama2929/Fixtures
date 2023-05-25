package com.carefer.matches.di

import com.carefer.matches.data.restful.MatchesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    internal fun provideServicesApi(retrofit: Retrofit): MatchesApi {
        return retrofit.create(MatchesApi::class.java)
    }
}
