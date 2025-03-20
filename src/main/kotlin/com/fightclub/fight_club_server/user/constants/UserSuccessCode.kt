package com.fightclub.fight_club_server.user.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class UserSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    MY_INFO_SUCCESS(HttpStatus.OK, "US001", "내 정보 조회 성공"),
    USER_INFO_SUCCESS(HttpStatus.OK, "US002", "회원 정보 조회 성공"),
    SIGNUP_SUCCESS(HttpStatus.OK, "US003", "회원가입 성공"),
    DELETE_SUCCESS(HttpStatus.OK, "US004", "회웥탈퇴 성공")
}