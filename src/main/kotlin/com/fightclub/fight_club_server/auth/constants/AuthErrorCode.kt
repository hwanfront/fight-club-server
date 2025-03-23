package com.fightclub.fight_club_server.auth.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AE001", "비밀번호가 일치하지 않습니다."),
}