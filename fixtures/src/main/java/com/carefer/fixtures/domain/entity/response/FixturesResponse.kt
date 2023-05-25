package com.carefer.fixtures.domain.entity.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FixturesResponse(

    @field:SerializedName("count")
    @Expose
    val count: Int? = null,

    @field:SerializedName("competition")
    @Expose
    val remoteCompetition: RemoteCompetition? = null,

    @field:SerializedName("filters")
    @Expose
    val filters: Filters? = null,

    @field:SerializedName("matches")
    @Expose
    val matches: List<RemoteMatchesItem?>? = null
)

data class Odds(

    @field:SerializedName("msg")
    @Expose
    val msg: String? = null
)

data class RemoteMatchesItem(

    @field:SerializedName("matchday")
    @Expose
    val matchDay: Int? = null,

    @field:SerializedName("awayTeam")
    @Expose
    val remoteAwayTeam: RemoteAwayTeam? = null,

    @field:SerializedName("utcDate")
    @Expose
    val utcDate: String? = null,

    @field:SerializedName("lastUpdated")
    @Expose
    val lastUpdated: String? = null,

    @field:SerializedName("score")
    @Expose
    val remoteScore: RemoteScore? = null,

    @field:SerializedName("stage")
    @Expose
    val stage: String? = null,

    @field:SerializedName("odds")
    @Expose
    val odds: Odds? = null,

    @field:SerializedName("season")
    @Expose
    val season: Season? = null,

    @field:SerializedName("homeTeam")
    @Expose
    val remoteHomeTeam: RemoteHomeTeam? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("referees")
    @Expose
    val referees: List<RefereesItem?>? = null,

    @field:SerializedName("status")
    @Expose
    val status: String? = null,

    @field:SerializedName("group")
    @Expose
    val group: Any? = null
)

data class RemoteAwayTeam(

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null
)

data class RemoteCompetition(

    @field:SerializedName("area")
    @Expose
    val area: Area? = null,

    @field:SerializedName("lastUpdated")
    @Expose
    val lastUpdated: String? = null,

    @field:SerializedName("code")
    @Expose
    val code: String? = null,

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("plan")
    @Expose
    val plan: String? = null
)

data class HalfTime(

    @field:SerializedName("awayTeam")
    @Expose
    val awayTeam: Int? = null,

    @field:SerializedName("homeTeam")
    @Expose
    val homeTeam: Int? = null
)

data class Season(

    @field:SerializedName("currentMatchday")
    @Expose
    val currentMatchDay: Int? = null,

    @field:SerializedName("endDate")
    @Expose
    val endDate: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("startDate")
    @Expose
    val startDate: String? = null
)

data class RemotePenalties(

    @field:SerializedName("awayTeam")
    @Expose
    val awayTeam: Int? = null,

    @field:SerializedName("homeTeam")
    @Expose
    val homeTeam: Int? = null
)

data class RemoteExtraTime(

    @field:SerializedName("awayTeam")
    @Expose
    val awayTeam: Int? = null,

    @field:SerializedName("homeTeam")
    @Expose
    val homeTeam: Int? = null
)

data class RemoteHomeTeam(

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null
)

data class RemoteScore(

    @field:SerializedName("duration")
    @Expose
    val duration: String? = null,

    @field:SerializedName("winner")
    @Expose
    val winner: String? = null,

    @field:SerializedName("penalties")
    @Expose
    val remotePenalties: RemotePenalties? = null,

    @field:SerializedName("halfTime")
    @Expose
    val halfTime: HalfTime? = null,

    @field:SerializedName("fullTime")
    @Expose
    val remoteFullTime: RemoteFullTime? = null,

    @field:SerializedName("extraTime")
    @Expose
    val remoteExtraTime: RemoteExtraTime? = null
)

data class Filters(
    val any: Any? = null
)

data class RemoteFullTime(

    @field:SerializedName("awayTeam")
    @Expose
    val awayTeam: Int? = null,

    @field:SerializedName("homeTeam")
    @Expose
    val homeTeam: Int? = null
)

data class RefereesItem(

    @field:SerializedName("role")
    @Expose
    val role: String? = null,

    @field:SerializedName("nationality")
    @Expose
    val nationality: String? = null,

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null
)

data class Area(

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null
)
