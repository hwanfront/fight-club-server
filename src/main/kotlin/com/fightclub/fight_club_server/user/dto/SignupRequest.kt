package com.fightclub.fight_club_server.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank(message = "이메일은 필수 입력값입니다.")
    @field:Email(message = "유효한 이메일 형식이 아닙니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @field:Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하이어야 합니다.")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
        message = "비밀번호는 8자 이상 20자 이하이며, 하나 이상의 문자와 숫자를 포함해야 합니다."
    )
    val password: String,

    @field:NotBlank(message = "닉네임은 필수 입력값입니다.")
    @field:Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
    val nickname: String,

    @field:NotBlank(message = "사용자명은 필수 입력값입니다.")
    @field:Size(min = 2, max = 50, message = "사용자명은 2자 이상 50자 이하여야 합니다.")
    val username: String
)