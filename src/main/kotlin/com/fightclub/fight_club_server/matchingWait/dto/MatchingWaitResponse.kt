package com.fightclub.fight_club_server.matchingWait.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass
import java.time.LocalDateTime

data class MatchingWaitResponse(
    val weight: Double,
    val weightClass: WeightClass,
    val createdAt: LocalDateTime,
)
