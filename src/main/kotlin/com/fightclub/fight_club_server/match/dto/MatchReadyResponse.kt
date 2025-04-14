package com.fightclub.fight_club_server.match.dto

import io.swagger.v3.oas.annotations.media.Schema

data class MatchReadyResponse(
    @Schema(description = "매치 ID", example = "1")
    val matchId: Long,
    @Schema(description = "내 준비 유무", example = "true")
    val isMeReady: Boolean,
    @Schema(description = "상대 준비 유무", example = "false")
    val isOpponentReady: Boolean,
)