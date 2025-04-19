package com.fightclub.fight_club_server.match.constants

import com.fightclub.fight_club_server.common.constants.SocketResponseCode

enum class MatchSocketErrorCode(
    override val code: String,
    override val message: String,
): SocketResponseCode {
    MATCH_NOT_FOUND(MatchConstants.MatchNotFound.CODE, MatchConstants.MatchNotFound.MESSAGE),
    USER_IS_NOT_PARTICIPANT(MatchConstants.UserIsNotParticipant.CODE, MatchConstants.UserIsNotParticipant.MESSAGE),
    UNNECESSARY_READ_UPDATE(MatchConstants.UnnecessaryReadUpdate.CODE, MatchConstants.UnnecessaryReadUpdate.MESSAGE)
}