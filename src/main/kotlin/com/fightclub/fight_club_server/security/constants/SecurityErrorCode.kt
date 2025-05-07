package com.fightclub.fight_club_server.security.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class SecurityErrorCode (
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "SE001", "지원하지 않는 인증 제공자입니다."),
}