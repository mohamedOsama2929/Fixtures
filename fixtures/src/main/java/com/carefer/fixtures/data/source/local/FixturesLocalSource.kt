package com.carefer.fixtures.data.source.local


interface FixturesLocalSource {
    suspend fun getFavouredMatches(): List<String>
    suspend fun settMatchFavour(id: String)
    suspend fun setMatchUnFavour(id: String)

}
