package com.carefer.fixtures.data.restful

import com.carefer.core.data.restful.Config
import com.carefer.fixtures.domain.entity.response.FixturesResponse
import retrofit2.http.*


interface FixturesApi {

    @GET(Config.MATCHES)
    suspend fun getFixturesList(
        @Path(Config.ID) id: String,
    ): FixturesResponse
}