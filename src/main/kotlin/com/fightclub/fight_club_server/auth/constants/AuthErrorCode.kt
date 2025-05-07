package com.fightclub.fight_club_server.auth.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, AuthConstants.InvalidPassword.CODE, AuthConstants.InvalidPassword.MESSAGE),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, AuthConstants.InvalidRefreshToken.CODE, AuthConstants.InvalidRefreshToken.MESSAGE),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, AuthConstants.RefreshTokenNotFound.CODE, AuthConstants.RefreshTokenNotFound.MESSAGE),
}