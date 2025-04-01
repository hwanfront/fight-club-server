package com.fightclub.fight_club_server.matchingWait.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchingWaitErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    MATCHING_WAIT_NOT_FOUND(HttpStatus.NOT_FOUND,MatchingWaitConstants.MatchingWaitNotFound.CODE, MatchingWaitConstants.MatchingWaitNotFound.MESSAGE),
    MATCHING_WAIT_ALREADY_EXISTS(HttpStatus.CONFLICT, MatchingWaitConstants.MatchingWaitAlreadyExists.CODE, MatchingWaitConstants.MatchingWaitAlreadyExists.MESSAGE),
}
