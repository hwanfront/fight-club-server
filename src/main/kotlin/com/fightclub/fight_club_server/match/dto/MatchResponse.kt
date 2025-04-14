package com.fightclub.fight_club_server.match.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class MatchResponse(
    @Schema(description = "매치 ID", example = "1")
    val matchId: Long?,
    @Schema(description = "상대 닉네임", example = "nickname2")
    val opponentNickname: String?,
    @Schema(description = "상대 몸무게", example = "55.0")
    val opponentWeight: Double,
    @Schema(description = "체급", example = "BANTAM")
    val weightClass: WeightClass,

    @Schema(description = "최근 메시지", example = "메시지")
    val recentMessage: String? = null,
    @Schema(description = "읽지 않은 메시지 개수", example = "50")
    val unreadMessageCount: Int,
    @Schema(description = "최근 메시지를 받은 날짜")
    val lastMessageTime: LocalDateTime? = null,

    @Schema(description = "내 준비 유무", example = "true")
    val isMeReady: Boolean,
    @Schema(description = "상대 준비 유무", example = "false")
    val isOpponentReady: Boolean,
)
