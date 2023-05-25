package com.carefer.matches.data.repository

import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.entity.response.MatchesResponse


interface MatchesRepository {

    suspend fun getMatchesList(matchesListQuery: MatchesListQuery): MatchesResponse
    suspend fun getFavouredMatchesList(): List<String>
    suspend fun setMatchFavour(id: String)
    suspend fun removeMatchFavour(id: String)
}