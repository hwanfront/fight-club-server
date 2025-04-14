package com.fightclub.fight_club_server.match.exception

import com.fightclub.fight_club_server.common.constants.SocketResponseCode
import com.fightclub.fight_club_server.common.exception.BaseException
import com.fightclub.fight_club_server.common.exception.SocketCodeException
import com.fightclub.fight_club_server.match.constants.MatchErrorCode
import com.fightclub.fight_club_server.match.constants.MatchSocketErrorCode

class MatchNotFoundException(
    override val socketResponseCode: MatchSocketErrorCode = MatchSocketErrorCode.MATCH_NOT_FOUND
) : BaseException(MatchErrorCode.MATCH_NOT_FOUND), SocketCodeException
class UserIsNotParticipantException(
    override val socketResponseCode: SocketResponseCode = MatchSocketErrorCode.USER_IS_NOT_PARTICIPANT
): BaseException(MatchErrorCode.USER_IS_NOT_PARTICIPANT), SocketCodeException