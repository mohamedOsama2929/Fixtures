package com.carefer.fixtures.domain.entity.local


data class MatchItem(
    val matchDay: Int = 0,
    val awayTeam: String = "",
    val date: String = "",
    val score: String = "",
    val homeTeam: String = "",
    val matchTime: String = "",
    val isFavorite: Boolean = false,
    val matchStatus: MatchStatus = MatchStatus.SCHEDULED,
    val id: Int = 0,
    val status: String = "",
    val headerTitle: String = "",
)