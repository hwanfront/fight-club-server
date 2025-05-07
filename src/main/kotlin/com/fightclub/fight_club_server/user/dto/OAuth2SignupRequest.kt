package com.fightclub.fight_club_server.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class OAuth2SignupRequest(
    @Schema(description = "닉네임", example = "nickname")
    @field:NotBlank(message = "닉네임은 필수 입력값입니다.")
    @field:Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
    val nickname: String,

    @Schema(description = "사용자명", example = "username")
    @field:NotBlank(message = "사용자명은 필수 입력값입니다.")
    @field:Size(min = 2, max = 50, message = "사용자명은 2자 이상 50자 이하여야 합니다.")
    val username: String
)