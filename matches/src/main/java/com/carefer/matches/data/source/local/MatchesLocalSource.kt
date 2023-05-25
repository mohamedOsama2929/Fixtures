package com.carefer.matches.data.source.local


interface MatchesLocalSource {
    suspend fun getFavouredMatches(): List<String>
    suspend fun settMatchFavour(id: String)
    suspend fun setMatchUnFavour(id: String)

}
