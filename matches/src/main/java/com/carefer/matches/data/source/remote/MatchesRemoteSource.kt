package com.carefer.matches.data.source.remote

import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.entity.response.MatchesResponse


interface MatchesRemoteSource {
    suspend fun getFixtures(matchesListQuery: MatchesListQuery): MatchesResponse

}
