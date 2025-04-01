package com.fightclub.fight_club_server.matchingWait.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class MatchingWaitResponse(
    @Schema(description = "몸무게", example = "45")
    val weight: Double,
    @Schema(description = "체급")
    val weightClass: WeightClass,
    val createdAt: LocalDateTime,
)
