package com.carefer.matches.utils

import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.entity.local.MatchStatus
import com.carefer.matches.domain.entity.response.RemoteAwayTeam
import com.carefer.matches.domain.entity.response.RemoteFullTime
import com.carefer.matches.domain.entity.response.RemoteHomeTeam
import com.carefer.matches.domain.entity.response.RemoteMatchesItem
import com.carefer.matches.domain.entity.response.RemoteScore
import java.util.Collections

class TestUtils {
    companion object {
        val MatchItem_REMOTE_NULL = RemoteMatchesItem(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        )

        val MatchItem_REMOTE = RemoteMatchesItem(
            id = 2,
            matchDay = 1,
            status = "Finished",
            remoteAwayTeam = RemoteAwayTeam(name = "Man City"),
            remoteHomeTeam = RemoteHomeTeam(name = "Real Madrid"),
            remoteScore = RemoteScore(remoteFullTime = RemoteFullTime(awayTeam = 0, homeTeam = 4)),
            utcDate = "2023-03-04"

        )

        val MATCH_ITEM_BO = MatchItem(
            isFavorite = false,
            matchStatus = MatchStatus.SCHEDULED,
            score = "2 - 1",
            matchDay = 1,
            homeTeam = "Real Madrid",
            headerTitle = "Today",
            awayTeam = "Man City",
            id = 22,
        )

        val MATCH_ITEM_BO2 = MatchItem(
            isFavorite = false,
            matchStatus = MatchStatus.SCHEDULED,
            score = "2 - 1",
            matchDay = 1,
            homeTeam = "Real Madrid",
            headerTitle = "Today",
            awayTeam = "Man City",
            id = 23,
        )


        val MATCHES_LIST = Collections.unmodifiableList(
            ArrayList<MatchItem>().apply {
                add(MATCH_ITEM_BO)
                add(MATCH_ITEM_BO2)
            }
        )

        val FAFOUR_MATCHES_LIST = Collections.unmodifiableList(
            ArrayList<String>().apply {
                add("2")
                add("1")
            }
        )

    }
}