package com.carefer.fixtures.data.repository

import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse


interface FixturesRepository {

    suspend fun getMatchesList(fixturesListQuery: FixturesListQuery): FixturesResponse
    suspend fun getFavouredMatchesList(): List<String>
    suspend fun setMatchFavour(id: String)
    suspend fun removeMatchFavour(id: String)
}