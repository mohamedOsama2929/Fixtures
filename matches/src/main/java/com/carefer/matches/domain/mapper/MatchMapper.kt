package com.carefer.matches.domain.mapper


import android.text.format.DateUtils
import com.carefer.core.domain.usecase.base.ModelMapper
import com.carefer.core.domain.utils.changeDateFormat
import com.carefer.core.domain.utils.convertDateStringToMilliSeconds
import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.entity.local.MatchStatus
import com.carefer.matches.domain.entity.response.RemoteMatchesItem
import com.carefer.matches.domain.entity.response.RemoteScore
import javax.inject.Inject

class MatchMapper @Inject constructor() :
    ModelMapper<RemoteMatchesItem, MatchItem> {
    override fun convert(from: RemoteMatchesItem?): MatchItem {
        return from?.let {
            MatchItem(
                id = from.id ?: 0,
                matchDay = from.matchDay ?: 0,
                status = from.status.orEmpty(),
                awayTeam = from.remoteAwayTeam?.name.orEmpty(),
                homeTeam = from.remoteHomeTeam?.name.orEmpty(),
                score = getScore(from.remoteScore),
                headerTitle = getHeaderTitleType(date = from.utcDate.orEmpty()),
                matchTime = it.utcDate?.let { date ->
                    changeDateFormat(date, "yyyy-MM-dd'T'HH:mm:ss", "hh:mm")
                }.orEmpty(),
                matchStatus = getMatchStatus(from.status.orEmpty()),
                date = it.utcDate?.let { date ->
                    changeDateFormat(date, "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd-MM-yyyy")
                }.orEmpty(),
            )
        } ?: MatchItem()
    }

    private fun getScore(remoteScore: RemoteScore?): String {
        return when {
            remoteScore?.remotePenalties?.homeTeam != null -> "${remoteScore.remotePenalties.homeTeam} - ${remoteScore.remotePenalties.awayTeam}"
            remoteScore?.remoteExtraTime?.homeTeam != null -> "${remoteScore.remoteExtraTime.homeTeam} - ${remoteScore.remoteExtraTime.awayTeam}"
            remoteScore?.remoteFullTime?.homeTeam != null -> "${remoteScore.remoteFullTime.homeTeam} - ${remoteScore.remoteFullTime.awayTeam}"
            else -> ""
        }
    }

    private fun getHeaderTitleType(date: String): String {
        val dateMillSecond = convertDateStringToMilliSeconds(
            date,
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )
        return when {
            isYesterday(dateMillSecond) -> "Yesterday"
            DateUtils.isToday(dateMillSecond) -> "Today"
            isTomorrow(dateMillSecond) -> "Tomorrow"
            else -> changeDateFormat(date, "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd-MM-yyyy")
        }
    }


    private fun isYesterday(whenInMillis: Long): Boolean {
        return DateUtils.isToday(whenInMillis + DateUtils.DAY_IN_MILLIS)
    }

    private fun isTomorrow(whenInMillis: Long): Boolean {
        return DateUtils.isToday(whenInMillis - DateUtils.DAY_IN_MILLIS)
    }

    private fun getMatchStatus(status: String): MatchStatus {
        return when (status) {
            "FINISHED" -> MatchStatus.FINISHED
            "SCHEDULED" -> MatchStatus.SCHEDULED

            else -> MatchStatus.SCHEDULED
        }
    }

}