package com.fightclub.fight_club_server.matchingWait.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchingWaitSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    GET_MY_WAIT_SUCCESS(HttpStatus.OK,"MWS001", "내 매칭 대기 정보를 불러왔습니다."),
    CREATE_WAIT_SUCCESS(HttpStatus.OK,"MWS002", "매칭 대기를 등록했습니다."),
    REMOVE_WAIT_SUCCESS(HttpStatus.OK,"MWS003", "매칭 대기를 삭제했습니다."),
    UPDATE_WAIT_SUCCESS(HttpStatus.OK,"MWS004", "매칭 대기를 수정했습니다."),
}
