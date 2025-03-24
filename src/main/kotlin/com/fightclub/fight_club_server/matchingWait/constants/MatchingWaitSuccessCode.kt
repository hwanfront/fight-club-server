package com.fightclub.fight_club_server.matchingWait.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchingWaitSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    CREATE_WAIT_SUCCESS(HttpStatus.OK,"MWS002", "매칭 대기를 등록했습니다."),
}
