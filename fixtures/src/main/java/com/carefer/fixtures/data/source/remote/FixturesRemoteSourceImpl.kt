package com.carefer.fixtures.data.source.remote

import com.carefer.fixtures.data.restful.FixturesApi
import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse
import javax.inject.Inject


class FixturesRemoteSourceImpl @Inject constructor(private val fixturesApi: FixturesApi) :
    FixturesRemoteSource {
    override suspend fun getFixtures(fixturesListQuery: FixturesListQuery): FixturesResponse {
        return fixturesApi.getFixturesList(fixturesListQuery.id)
    }

}



