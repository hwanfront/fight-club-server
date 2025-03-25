package com.fightclub.fight_club_server.matchingWait.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass


data class MatchingCandidateResponse(
    val userId: Long,
    val nickname: String,
    val weight: Double,
    val weightClass: WeightClass
)
