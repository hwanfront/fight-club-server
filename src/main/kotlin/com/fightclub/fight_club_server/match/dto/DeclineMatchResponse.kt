package com.fightclub.fight_club_server.match.dto

data class DeclineMatchResponse(
    val matchId: Long,
    val declinedBy: String?,
)
