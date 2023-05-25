package com.carefer.fixtures.di

import com.carefer.fixtures.data.repository.FixturesRepository
import com.carefer.fixtures.data.repository.FixturesRepositoryImp
import com.carefer.fixtures.data.source.remote.FixturesRemoteSource
import com.carefer.fixtures.data.source.remote.FixturesRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class FixturesModule {
    @Binds
    abstract fun bindFixturesRepository(
        fixturesRepositoryImp: FixturesRepositoryImp
    ): FixturesRepository

    @Binds
    abstract fun bindFixtureRemoteSource(
        fixturesRemoteSourceImpl: FixturesRemoteSourceImpl
    ): FixturesRemoteSource
}