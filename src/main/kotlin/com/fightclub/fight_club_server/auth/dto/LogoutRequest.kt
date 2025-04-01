package com.fightclub.fight_club_server.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class LogoutRequest(
    @Schema(description = "권한 검증 토큰", example = "your refresh token")
    @field:NotBlank(message = "리프레시 토큰은 필수입니다.")
    val refreshToken: String
)