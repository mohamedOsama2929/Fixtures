package com.carefer.matches.di

import com.carefer.matches.data.repository.MatchesRepository
import com.carefer.matches.data.repository.MatchesRepositoryImp
import com.carefer.matches.data.source.local.MatchesLocalSource
import com.carefer.matches.data.source.local.MatchesLocalSourceImpl
import com.carefer.matches.data.source.remote.MatchesRemoteSource
import com.carefer.matches.data.source.remote.MatchesRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class MatchesModule {
    @Binds
    abstract fun bindFixturesRepository(
        fixturesRepositoryImp: MatchesRepositoryImp
    ): MatchesRepository

    @Binds
    abstract fun bindFixtureLocalSource(
        fixturesRemoteSourceImpl: MatchesLocalSourceImpl
    ): MatchesLocalSource

    @Binds
    abstract fun bindFixtureRemoteSource(
        fixturesRemoteSourceImpl: MatchesRemoteSourceImpl
    ): MatchesRemoteSource
}
