package com.fightclub.fight_club_server.common.constants

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "CE001", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "CE002", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "CE003", "접근이 거부되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "CE004", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CE005", "서버 내부 오류가 발생했습니다."),
    CONFLICT(HttpStatus.CONFLICT, "CE006", "데이터 충돌(중복) 요청입니다.")
}