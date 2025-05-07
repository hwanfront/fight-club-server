package com.fightclub.fight_club_server.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

data class RefreshResponse (
    @Schema(description = "권한 검증 토큰")
    val accessToken: String,
    @Schema(description = "Access Token 만료 시 갱신용 토큰")
    val refreshToken: String
)
