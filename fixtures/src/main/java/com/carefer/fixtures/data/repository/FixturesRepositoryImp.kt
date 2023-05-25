package com.carefer.fixtures.data.repository

import com.carefer.fixtures.data.source.local.FixturesLocalSource
import com.carefer.fixtures.data.source.remote.FixturesRemoteSource
import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse
import javax.inject.Inject


class FixturesRepositoryImp @Inject constructor(
    private val fixturesRemoteSource: FixturesRemoteSource,
    private val fixturesLocalSource: FixturesLocalSource,
) : FixturesRepository {

    override suspend fun getMatchesList(fixturesListQuery: FixturesListQuery): FixturesResponse {
        return fixturesRemoteSource.getFixtures(fixturesListQuery)
    }

    override suspend fun getFavouredMatchesList(): List<String> {
        return fixturesLocalSource.getFavouredMatches()
    }

    override suspend fun setMatchFavour(id: String) {
        return fixturesLocalSource.settMatchFavour(id)
    }

    override suspend fun removeMatchFavour(id: String) {
        return fixturesLocalSource.setMatchUnFavour(id)
    }


}
