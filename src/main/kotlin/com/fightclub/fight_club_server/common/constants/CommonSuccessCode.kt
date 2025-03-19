package com.fightclub.fight_club_server.common.constants

import org.springframework.http.HttpStatus

enum class CommonSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    OK(HttpStatus.OK, "CS001", "요청 성공"),
    CREATED(HttpStatus.CREATED, "CS002", "리소스 생성 완료"),
    UPDATED(HttpStatus.OK, "CS003", "업데이트 완료"),
    DELETED(HttpStatus.OK, "CS004", "삭제 완료")
}