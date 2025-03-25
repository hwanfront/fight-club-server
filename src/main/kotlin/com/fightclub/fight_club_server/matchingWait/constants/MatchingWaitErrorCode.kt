package com.fightclub.fight_club_server.matchingWait.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchingWaitErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    MATCHING_WAIT_NOT_FOUND(HttpStatus.NOT_FOUND,"MWE001", "매칭 대기 정보가 존재하지 않습니다."),
    MATCHING_WAIT_ALREADY_EXISTS(HttpStatus.CONFLICT, "MWE002", "이미 매칭 대기 중입니다.")
}
