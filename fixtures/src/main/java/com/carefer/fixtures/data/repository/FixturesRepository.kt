package com.carefer.fixtures.data.repository

import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse


interface FixturesRepository {

    suspend fun getFixturesList(fixturesListQuery: FixturesListQuery): FixturesResponse
}