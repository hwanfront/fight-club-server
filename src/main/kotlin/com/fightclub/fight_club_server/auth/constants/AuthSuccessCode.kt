package com.fightclub.fight_club_server.auth.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class AuthSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    LOGIN_SUCCESS(HttpStatus.OK, "AS001", "로그인 성공"),
    LOGOUT_SUCCESS(HttpStatus.OK, "AS002", "로그아웃 성공")
}