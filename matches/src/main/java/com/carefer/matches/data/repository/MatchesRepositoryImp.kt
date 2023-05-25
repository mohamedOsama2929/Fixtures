package com.carefer.matches.data.repository

import com.carefer.matches.data.source.local.MatchesLocalSource
import com.carefer.matches.data.source.remote.MatchesRemoteSource
import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.entity.response.MatchesResponse
import javax.inject.Inject


class MatchesRepositoryImp @Inject constructor(
    private val matchesRemoteSource: MatchesRemoteSource,
    private val matchesLocalSource: MatchesLocalSource,
) : MatchesRepository {

    override suspend fun getMatchesList(matchesListQuery: MatchesListQuery): MatchesResponse {
        return matchesRemoteSource.getFixtures(matchesListQuery)
    }

    override suspend fun getFavouredMatchesList(): List<String> {
        return matchesLocalSource.getFavouredMatches()
    }

    override suspend fun setMatchFavour(id: String) {
        return matchesLocalSource.settMatchFavour(id)
    }

    override suspend fun removeMatchFavour(id: String) {
        return matchesLocalSource.setMatchUnFavour(id)
    }


}
