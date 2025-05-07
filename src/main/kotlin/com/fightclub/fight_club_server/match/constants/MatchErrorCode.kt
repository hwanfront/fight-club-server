package com.fightclub.fight_club_server.match.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, MatchConstants.MatchNotFound.CODE, MatchConstants.MatchNotFound.MESSAGE),
    USER_IS_NOT_PARTICIPANT(HttpStatus.FORBIDDEN, MatchConstants.UserIsNotParticipant.CODE, MatchConstants.UserIsNotParticipant.MESSAGE),
}
