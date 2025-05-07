package com.fightclub.fight_club_server.matchProposal.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AcceptResponse(
    @Schema(description = "생성된 매치 ID", example = "1")
    val matchId: Long? = null,
)
