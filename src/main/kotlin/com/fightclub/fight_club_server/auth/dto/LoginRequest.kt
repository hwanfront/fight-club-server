package com.fightclub.fight_club_server.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class LoginRequest(
    @Schema(description = "사용자 이메일", example = "test2@example.com")
    @field:Email(message = "올바른 이메일 형식이어야 합니다.")
    val email: String,

//    @field:Pattern(
//        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?])[A-Za-z\\d!@#\$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]{8,20}$",
//        message = "비밀번호는 영문, 숫자, 특수문자를 포함해 8~20자로 입력해주세요."
//    )
    @Schema(description = "비밀번호", example = "test1234")
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    val password: String
)
