package com.fightclub.fight_club_server.auth.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class AuthSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    LOGIN_SUCCESS(HttpStatus.OK, AuthConstants.LoginSuccess.CODE, AuthConstants.LoginSuccess.MESSAGE),
    LOGOUT_SUCCESS(HttpStatus.OK, AuthConstants.LogoutSuccess.CODE, AuthConstants.LogoutSuccess.MESSAGE),
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK, AuthConstants.TokenRefreshSuccess.CODE, AuthConstants.TokenRefreshSuccess.MESSAGE),
}