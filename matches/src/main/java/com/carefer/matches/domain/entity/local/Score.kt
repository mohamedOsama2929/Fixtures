package com.carefer.matches.domain.entity.local

data class Score(
    val duration: String = "",
    val winner: String = "",
    val fullTime: FullTime = FullTime(),
    val penalties: Penalties = Penalties(),
    val extraTime: ExtraTime? = ExtraTime()
)