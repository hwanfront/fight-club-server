package com.fightclub.fight_club_server.auth.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode

import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "UE001", "유저가 존재하지 않습니다."),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "UE002", "이미 가입된 사용자입니다.")
}