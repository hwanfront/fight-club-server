package com.fightclub.fight_club_server.user.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, UserConstants.UserNotFound.CODE, UserConstants.UserNotFound.MESSAGE),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, UserConstants.UserAlreadyExist.CODE, UserConstants.UserAlreadyExist.MESSAGE),
    USER_NOT_WAITING_STATUS(HttpStatus.FORBIDDEN, UserConstants.UserNotWaitingStatus.CODE, UserConstants.UserNotWaitingStatus.MESSAGE),
    USER_DELETED(HttpStatus.NOT_FOUND, UserConstants.UserDeleted.CODE, UserConstants.UserDeleted.MESSAGE),
}