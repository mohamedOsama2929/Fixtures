package com.carefer.matches.domain.entity.local


data class MatchItem(
    val matchDay: Int = 0,
    val awayTeam: String = "",
    val date: String = "",
    val score: String = "",
    val homeTeam: String = "",
    val matchTime: String = "",
    var isFavorite: Boolean = false,
    val matchStatus: MatchStatus = MatchStatus.SCHEDULED,
    val id: Int = 0,
    val status: String = "",
    val headerTitle: String = "",
)