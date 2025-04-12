package com.fightclub.fight_club_server.match.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class MatchInfoResponse(
    @Schema(description = "매치 ID", example = "1")
    val matchId: Long?,
    @Schema(description = "내 닉네임", example = "nickname1")
    val myNickname: String?,
    @Schema(description = "내 몸무게", example = "54.0")
    val myWeight: Double,
    @Schema(description = "상대 닉네임", example = "nickname2")
    val opponentNickname: String?,
    @Schema(description = "상대 몸무게", example = "55.0")
    val opponentWeight: Double,
    @Schema(description = "체급", example = "BANTAM")
    val weightClass: WeightClass,

    @Schema(description = "내 준비 유무", example = "true")
    val isMeReady: Boolean,
    @Schema(description = "상대 준비 유무", example = "false")
    val isOpponentReady: Boolean,

    @Schema(description = "매치 성립 시간")
    val matchedAt: LocalDateTime,
)
