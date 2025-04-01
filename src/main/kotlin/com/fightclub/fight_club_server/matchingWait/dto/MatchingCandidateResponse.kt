package com.fightclub.fight_club_server.matchingWait.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass
import io.swagger.v3.oas.annotations.media.Schema


data class MatchingCandidateResponse(
    @Schema(description = "유저 ID", example = "1")
    val userId: Long,
    @Schema(description = "닉네임", example = "nickname")
    val nickname: String,
    @Schema(description = "몸무게", example = "45")
    val weight: Double,
    @Schema(description = "체급")
    val weightClass: WeightClass
)
