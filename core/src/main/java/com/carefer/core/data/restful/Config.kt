package com.carefer.core.data.restful

object Config {
    const val BASE_URL = "https://api.football-data.org/v2/"
    const val ID = "id"
    const val PATH_ID = "{$ID}"
    const val MATCHES = "competitions/".plus(PATH_ID).plus("/matches")
}