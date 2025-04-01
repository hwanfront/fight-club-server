package com.fightclub.fight_club_server.user.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class UserSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    MY_INFO_SUCCESS(HttpStatus.OK, UserConstants.MyInfoSuccess.CODE, UserConstants.MyInfoSuccess.MESSAGE),
    USER_INFO_SUCCESS(HttpStatus.OK, UserConstants.UserInfoSuccess.CODE, UserConstants.UserInfoSuccess.MESSAGE),
    SIGNUP_SUCCESS(HttpStatus.OK, UserConstants.SignupSuccess.CODE, UserConstants.SignupSuccess.MESSAGE),
    DELETE_USER_SUCCESS(HttpStatus.OK, UserConstants.DeleteUserSuccess.CODE, UserConstants.DeleteUserSuccess.MESSAGE),
}