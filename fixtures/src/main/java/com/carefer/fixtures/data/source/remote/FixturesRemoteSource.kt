package com.carefer.fixtures.data.source.remote

import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse


interface FixturesRemoteSource {
    suspend fun getFixtures(fixturesListQuery: FixturesListQuery): FixturesResponse

}
