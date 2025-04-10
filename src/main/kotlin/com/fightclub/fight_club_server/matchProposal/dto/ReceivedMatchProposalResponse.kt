package com.fightclub.fight_club_server.matchProposal.dto

import com.fightclub.fight_club_server.meta.enums.WeightClass
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ReceivedMatchProposalResponse(
    @Schema(description = "매치 대기 ID", example = "1")
    val id: Long? = null,
    @Schema(description = "상대방 닉네임", example = "nickname")
    val senderNickname: String? = null,
    @Schema(description = "상대방 몸무게", example = "45")
    val senderWeight: Double,
    @Schema(description = "체급", example = "BANTAM")
    val weightClass: WeightClass,
    @Schema(description = "요청날짜")
    val requestedAt: LocalDateTime = LocalDateTime.now(),
)
