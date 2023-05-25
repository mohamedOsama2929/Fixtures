package com.carefer.matches.data.restful

import com.carefer.core.data.restful.Config
import com.carefer.matches.domain.entity.response.MatchesResponse
import retrofit2.http.*


interface MatchesApi {

    @GET(Config.MATCHES)
    suspend fun getFixturesList(
        @Path(Config.ID) id: String,
    ): MatchesResponse
}