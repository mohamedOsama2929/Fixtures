package com.carefer.fixtures.data.repository

import com.carefer.fixtures.data.source.remote.FixturesRemoteSource
import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse
import javax.inject.Inject


class FixturesRepositoryImp @Inject constructor(
    private val fixturesRemoteSource: FixturesRemoteSource
) : FixturesRepository {

    override suspend fun getFixturesList(fixturesListQuery: FixturesListQuery): FixturesResponse {
        return fixturesRemoteSource.getFixtures(fixturesListQuery)
    }


}
