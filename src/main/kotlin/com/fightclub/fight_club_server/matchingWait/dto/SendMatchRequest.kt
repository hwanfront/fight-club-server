package com.fightclub.fight_club_server.matchingWait.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SendMatchRequest(
    @Schema(description = "상대방 ID", example = "1")
    val receiverId: Long
)
