package com.carefer.matches.data.source.remote

import com.carefer.matches.data.restful.MatchesApi
import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.entity.response.MatchesResponse
import javax.inject.Inject


class MatchesRemoteSourceImpl @Inject constructor(private val matchesApi: MatchesApi) :
    MatchesRemoteSource {
    override suspend fun getFixtures(matchesListQuery: MatchesListQuery): MatchesResponse {
        return matchesApi.getFixturesList(matchesListQuery.id)
    }

}



