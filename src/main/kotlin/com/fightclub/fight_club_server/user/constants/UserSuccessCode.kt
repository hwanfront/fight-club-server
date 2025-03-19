package com.fightclub.fight_club_server.auth.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class UserSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    MY_INFO_SUCCESS(HttpStatus.OK, "US001", "내 정보 조회 성공"),
    USER_INFO_SUCCESS(HttpStatus.OK, "US001", "회원 정보 조회 성공")
}